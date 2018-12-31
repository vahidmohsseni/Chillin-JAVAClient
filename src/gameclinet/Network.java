package gameclinet;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

class Network {

    private SocketAddress socketAddress;
    private SSLSocket socket;
    private int host_port;
    private String host_ip;


    Network() {
        host_ip = Config.getConfigIns().config.getJSONObject("net").getString("host");
        host_port = Config.getConfigIns().config.getJSONObject("net").getInt("port");
        socketAddress = new InetSocketAddress(host_ip, host_port);
        socket = create_socket();

    }

    private SSLSocket create_socket() {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = null;
        try {
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(host_ip, host_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sslSocket;

    }


    void connect() {
        try {

            socket.setSoTimeout(Config.getConfigIns().config.getJSONObject("net").getInt("timeout"));
            //socket.connect(socketAddress);


            socket.startHandshake();
            socket.setSoTimeout(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHost_ip(){
        return host_ip;
    }

    public int getHost_port(){
        return host_port;
    }


    byte[] recv_data(){
//        byte[] size;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            char[] size = new char[4];

            br.read(size, 0, 4);

            System.out.println(size);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    int send_data(byte[] data){
        int size = data.length;
        List<Byte> result = new ArrayList<>();
        result.addAll(b22B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array(),
                data));

        try {
            OutputStream os = socket.getOutputStream();
            byte[] res = B2b(result);
             os.write(res);
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    private static List<Byte> b2B(byte[] bytes)
    {
        List<Byte> result = new ArrayList<>();
        for (byte b : bytes)
            result.add(b);
        return result;
    }

    private static List<Byte> b22B(byte[] bytes1, byte[] bytes2)
    {
        List<Byte> result = new ArrayList<>();

        for (byte b : bytes1)
            result.add(b);

        for (byte b : bytes2)
            result.add(b);

        return result;
    }

    private static byte[] B2b(List<Byte> bytes)
    {
        byte[] result = new byte[bytes.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }

    void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
