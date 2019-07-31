package gameclinet;

import gameclinet.helper.messages.*;
import ks.KSObject;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


class AIDecideThread implements Runnable{
    private BaseAI ai;

    AIDecideThread(BaseAI ai){

        this.ai = ai;
    }
    public void run(){
        ai.decide();
    }

}

public class Core implements Runnable{

    private boolean game_running;
    private Queue<KSObject> command_send_queue;
    private Network network;
    private Protocol protocol;
    private BaseAI ai;

    public Core(){

        game_running = false;
        command_send_queue = new LinkedBlockingQueue<>();
        network = new Network();
        protocol = new Protocol(network);

    }

    public void registerAI(BaseAI ai) {
        ai.setCommandSendQueue(command_send_queue);
        this.ai = ai;
    }

    public void quit(){
        game_running = false;
        // adding null to java collections is not permitted!
        // command_send_queue.add(null);
        network.close();
    }

    private void sendMessage(KSObject msg){

        protocol.sendMessage(msg);
    }

    private KSObject[] recvMessage(){
        KSObject[] tmp = protocol.recvMessage();

        if (tmp[0] != null){
            return tmp;
        }
        // log
        quit();
        System.exit(0);

        return null;
    }

    public void sendCommandThread() {
        while (true) {
            KSObject msg = command_send_queue.poll();
            if (msg == null) {
                break;
            }
            if (game_running) {
                sendMessage(msg);
            }
        }

    }

    public void run(){
        sendCommandThread();
    }

    public boolean connect() {

        int max_tries = Config.getConfigIns().config.getJSONObject("net").getInt("max_tries");
        int retry_waiting_time =
                Config.getConfigIns().config.getJSONObject("net").getInt("retry_waiting_time");

        while (true) {
            // log connecting to host ...
            System.out.println("Connecting to host '" + network.getHostIp() + "' port " + network.getHostPort());
            try {
                network.connect();
                // log connected!
                System.out.println("Connected successfully");
                return true;
            }
            catch (Exception e) {
                // log
                System.out.println("Failed to connect: " + e);
            }

            max_tries--;
            if (max_tries < 0){
                break;
            }
            // log try reconnecting
            System.out.println("Reconnecting in " + retry_waiting_time + " seconds ...");

            try {
                Thread.sleep(retry_waiting_time * 1000);
            } catch (InterruptedException ignored) {

            }
        }

        return false;
    }


    public boolean join(){
        KSObject join_msg;
        if (Config.getConfigIns().config.getJSONObject("general").getBoolean("offline_mode")){
            join_msg = new JoinOfflineGame();
            ((JoinOfflineGame) join_msg).teamNickname =
                    Config.getConfigIns().config.getJSONObject("ai").getString("team_nickname");
            ((JoinOfflineGame) join_msg).agentName =
                    Config.getConfigIns().config.getJSONObject("ai").getString("agent_name");
        }
        else {
            join_msg = new JoinOnlineGame();
            ((JoinOnlineGame) join_msg).token =
                    Config.getConfigIns().config.getJSONObject("ai").getString("token");
            ((JoinOnlineGame) join_msg).agentName =
                    Config.getConfigIns().config.getJSONObject("ai").getString("agent_name");
        }

        sendMessage(join_msg);
        KSObject msg = new ClientJoined();
        ((ClientJoined)msg).joined = false;
        while (true){
            KSObject[] tmp = recvMessage();
            msg = tmp[0];

            if (msg.Name().equals(ClientJoined.NameStatic)){
                break;
            }
        }
        if (((ClientJoined)msg).joined){
            ai.mySide = ((ClientJoined) msg).sideName;
            ai.sides = ((ClientJoined) msg).sides;
            ai.otherSides = ai.sides.keySet();
            ai.otherSides.remove(ai.mySide);
            ai.otherSide = (ai.otherSides.size() == 1) ? ai.otherSides.iterator().next(): null;

            System.out.println("joined the game successfully");
            System.out.printf("Side: %s\n", ai.mySide);
            return true;
        }
        System.out.println("Failed to join the game");
        return false;
    }

    public void loop(){
        while (true) {
            KSObject[] tmp = recvMessage();
            KSObject msg = tmp[0];

            if (msg instanceof BaseSnapshot){
                handleSnapshot((BaseSnapshot) msg);
            }
            else if(msg.Name().equals(StartGame.NameStatic)){
                handStartGame(msg);
            }

            else if(msg.Name().equals(EndGame.NameStatic)){
                handleEndGame((EndGame) msg);
                break;
            }
        }
    }

    private void handleSnapshot(BaseSnapshot snapshot){

        if(snapshot instanceof TurnbasedSnapshot){
            ((TurnbasedAI)ai).updateTurnbased((TurnbasedSnapshot) snapshot);
        }
        else if( snapshot instanceof RealtimeSnapshot) {
            ((RealtimeAI) ai).updateRealtime((RealtimeSnapshot) snapshot);
        }
        else{
            ai.update(snapshot);
        }

        // ai.update(snapshot);
        if (!game_running){
            game_running = true;
            ai.initialize();
            if (!Config.getConfigIns().config.getJSONObject("ai").getBoolean("create_new_thread") ||
                    ai.allowedToDecide()){
                // start new thread for ai.decide()
                Thread t1 = new Thread(new AIDecideThread(ai));
                t1.start();
            }

        }
        else if(Config.getConfigIns().config.getJSONObject("ai").getBoolean("create_new_thread") &&
                ai.allowedToDecide()) {
            // start new thread for ai.decide()
            Thread t1 = new Thread(new AIDecideThread(ai));
            t1.start();
        }
    }

    private void handStartGame(KSObject gamestart){
        // start game thread for sendCommandThread()
        Thread t1 = new Thread(this);
        t1.start();
    }

    private void handleEndGame(EndGame endgame){
        String winner = (endgame.winnerSidename != null) ? endgame.winnerSidename : "draw";
        System.out.printf("Winner side: %s\n", winner);
        if (!endgame.details.isEmpty()){
            System.out.println("Details:");
            for(String name: endgame.details.keySet()){
                System.out.printf("  %s:\n", name);
                for(String side: endgame.details.get(name).keySet()){
                    System.out.printf("    %s -> %s",side, endgame.details.get(name).get(side));
                }
            }
        }
        quit();
    }

}
