package gameclinet;

import gameclinet.helper.messages.KSObject;
import gameclinet.helper.parser.Parser;


class Protocol {

//    private Network network;
    private Network network;
    private Parser parser;



    public Protocol(Network network){
        this.network = network;
        this.parser = new Parser(Config.getConfigIns().config.getJSONObject(
                "general").getJSONArray("command_files"));
    }

    public void send_message(KSObject msg) {
        byte[] data = parser.encode(msg);
        network.send_data(data);
    }

    public KSObject[] recv_message() {
        byte[] data = network.recv_data();
        if (data == null){
            return null;
        }
        KSObject[] msg = parser.decode(data);

        return msg;
    }
}
