package gameclinet;

import gameclinet.helper.messages.ClientJoined;
import gameclinet.helper.messages.JoinOfflineGame;
import gameclinet.helper.messages.JoinOnlineGame;
import gameclinet.helper.messages.KSObject;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Core {

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

    private void send_message(KSObject msg){

        protocol.send_message(msg);
    }

    private KSObject[] recv_message(){
        KSObject[] tmp = protocol.recv_message();

        if (tmp[0] != null){
            return tmp;
        }
        // log
        quit();
        System.exit(0);

        return null;
    }

    public void send_command_thread() {
        while (true) {
            KSObject msg = command_send_queue.poll();
            if (msg == null) {
                break;
            }
            if (game_running) {
                send_message(msg);
            }
        }

    }

    public boolean connect() {

        int max_tries = Config.getConfigIns().config.getJSONObject("net").getInt("max_tries");
        int retry_waiting_time =
                Config.getConfigIns().config.getJSONObject("net").getInt("retry_waiting_time");

        while (true) {
            // log connecting to host ...
            System.out.println("Connecting to host '" + network.getHost_ip() + "' port " + network.getHost_port());
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

        send_message(join_msg);
        KSObject msg = new ClientJoined();
        ((ClientJoined)msg).joined = false;
        while (true){
            KSObject[] tmp = recv_message();
            msg = tmp[0];

            if (msg.Name().equals(ClientJoined.NameStatic)){
                break;
            }
        }
        if (((ClientJoined)msg).joined){
            msg = (ClientJoined)msg;
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
//            msg_type, msg = self._recv_msg()
//
//            if isinstance(msg, BaseSnapshot):
//            self._handle_snapshot(msg)
//
//            elif msg_type ==StartGame.name():
//            self._handle_start_game(msg)
//
//            elif msg_type ==EndGame.name():
//            self._handle_end_game(msg)
//            break
        }
    }


}
