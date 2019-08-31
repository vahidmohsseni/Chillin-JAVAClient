package gameclinet;

import gameclinet.helper.logger.Logger;
import gameclinet.helper.messages.*;
import ks.KSObject;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public class Core {

    private boolean gameRunning;
    private Queue<KSObject> commandSendQueue;
    private Network network;
    private Protocol protocol;
    private AbstractAI ai;


    public class KSNull extends KSObject {

        public KSNull (){

        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public byte[] serialize() {
            return new byte[0];
        }

        @Override
        protected int deserialize(byte[] s, int offset) {
            return 0;
        }
    }



    public Core() {
        gameRunning = false;
        commandSendQueue = new LinkedBlockingQueue<>();
        network = new Network();
        protocol = new Protocol(network);
    }

    public void registerAI(AbstractAI ai) {
        ai.setCommandSendQueue(commandSendQueue);
        this.ai = ai;
    }

    public void quit() {
        gameRunning = false;
        commandSendQueue.add(new KSNull());
        network.close();
    }

    private void sendMessage(KSObject msg) {
        protocol.sendMessage(msg);
    }

    private KSObject recvMessage() {
        KSObject tmp = protocol.recvMessage();

        if (tmp != null)
            return tmp;

        // log
        quit();
        return null;
    }

    public void sendCommandThread() {
        while (true) {
            KSObject msg = commandSendQueue.poll();

            if (msg == null) {
                continue;
            }
            if (msg instanceof KSNull) {
                break;
            }
            if (gameRunning) {
                sendMessage(msg);
            }
        }
    }


    public boolean connect() {
        int max_tries = Config.getInstance().config.getJSONObject("net").getInt("max_tries");
        int retryWaitingTime =
                Config.getInstance().config.getJSONObject("net").getInt("retry_waiting_time");

        while (true) {
            // log connecting to host ...
            Logger.log("Connecting to host '" + network.getHostIp() + "' port " + network.getHostPort());
            try {
                network.connect();
                // log connected!
                Logger.log("Connected successfully");
                return true;
            }
            catch (Exception e) {
                // log
            	Logger.log("Failed to connect: " + e);
            }

            max_tries--;
            if (max_tries < 1) {
                break;
            }
            // log try reconnecting
            Logger.log("Reconnecting in " + retryWaitingTime + " seconds ...");

            try {
                Thread.sleep(retryWaitingTime * 1000);
            } catch (InterruptedException ignored) {

            }
        }

        return false;
    }


    public boolean join() {
        KSObject joinMsg;
        if (Config.getInstance().config.getJSONObject("general").getBoolean("offline_mode")) {
        	joinMsg = new JoinOfflineGame();
            ((JoinOfflineGame) joinMsg).setTeamNickname(Config.getInstance().config.getJSONObject("ai").getString("team_nickname"));
            ((JoinOfflineGame) joinMsg).setAgentName(Config.getInstance().config.getJSONObject("ai").getString("agent_name"));
        }
        else {
        	joinMsg = new JoinOnlineGame();
            ((JoinOnlineGame) joinMsg).setToken(Config.getInstance().config.getJSONObject("ai").getString("token"));
            ((JoinOnlineGame) joinMsg).setAgentName(Config.getInstance().config.getJSONObject("ai").getString("agent_name"));
        }

        sendMessage(joinMsg);
        ClientJoined msg = new ClientJoined();
        msg.setJoined(false);
        while (true){
            KSObject tmp = recvMessage();
            if (tmp.name().equals(ClientJoined.nameStatic))
            	msg = (ClientJoined) tmp;
                break;
        }

        if (msg.getJoined()) {
        	ai.setSides(msg.getSides(), msg.getSideName());
            Logger.log("joined the game successfully");
            Logger.log("Side: " + ai.mySide + "\n");
            return true;
        }

        Logger.log("Failed to join the game");
        return false;
    }

    public void loop() {
        while (true) {
            KSObject msg = recvMessage();

            if (msg instanceof BaseSnapshot) {
                handleSnapshot((BaseSnapshot) msg);
            }
            else if (msg.name().equals(StartGame.nameStatic)) {
                handleStartGame(msg);
            }
            else if (msg.name().equals(EndGame.nameStatic)) {
                handleEndGame((EndGame) msg);
                break;
            }
        }
    }

    private void handleSnapshot(BaseSnapshot snapshot) {
        ai.update(snapshot);

        // ai.update(snapshot);
        if (!gameRunning) {
            gameRunning = true;
            ai.initialize();
            if (!Config.getInstance().config.getJSONObject("ai").getBoolean("create_new_thread") ||
                    ai.allowedToDecide()) {
                // start new thread for ai.decide()
                Runnable t = () -> ai.decide();
                new Thread(t).start();
            }
        }
        else if(Config.getInstance().config.getJSONObject("ai").getBoolean("create_new_thread") &&
                ai.allowedToDecide()) {
            // start new thread for ai.decide()
            Runnable t = () -> ai.decide();
            new Thread(t).start();
        }
    }

    private void handleStartGame(KSObject gamestart) {
        // start game thread for sendCommandThread()
        Runnable t = () -> this.sendCommandThread();
        new Thread(t).start();
    }

    private void handleEndGame(EndGame endgame) {
        String winner = (endgame.getWinnerSidename() != null) ? endgame.getWinnerSidename() : "draw";
        Logger.log("Winner side: " + winner + "\n");
        if (!endgame.getDetails().isEmpty()) {
        	Logger.log("Details:");
            for(String name: endgame.getDetails().keySet()) {
            	Logger.log("  " + name + ":\n");
                for(String side: endgame.getDetails().get(name).keySet()) {
                	Logger.log("    " + side + " -> " + endgame.getDetails().get(name).get(side));
                }
            }
        }
        quit();
    }
}
