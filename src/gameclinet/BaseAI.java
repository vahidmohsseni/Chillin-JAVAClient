package gameclinet;

import java.util.Queue;

public class BaseAI {

    private Queue<String> command_send_queue = null;

    public void set_command_send_queue(Queue q){
        command_send_queue = q;
    }
}
