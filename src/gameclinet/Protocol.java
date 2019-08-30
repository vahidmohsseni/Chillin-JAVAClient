package gameclinet;

import ks.KSObject;
import gameclinet.helper.parser.Parser;


class Protocol {

    private Network network;
    private Parser parser;


    public Protocol(Network network) {
        this.network = network;
        this.parser = new Parser();
    }

    public void sendMessage(KSObject msg) {
        byte[] data = parser.encode(msg);
        network.sendData(data);
    }

    public KSObject recvMessage() {
        byte[] data = network.recvData();
        if (data == null)
            return null;

        KSObject msg = parser.decode(data);
        return msg;
    }
}
