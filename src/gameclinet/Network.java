package gameclinet;


import gameclinet.helper.network.SSLUtil;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    }

    public void connect() throws Exception{
        SSLSocketFactory socket_factory = SSLUtil.getAllTrustedSocketFactory();
        SSLSocket sslSocket;
        sslSocket = (SSLSocket) socket_factory.createSocket(host_ip, host_port);
        sslSocket.startHandshake();
        socket = sslSocket;

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
            InputStream in = socket.getInputStream();
            byte[] size_by = new byte[4];
            in.read(size_by, 0, 4);
            int size = ByteBuffer.wrap(size_by).order(ByteOrder.LITTLE_ENDIAN).getChar();

//            byte[] raw_data = new byte[size];
            List<Byte> raw_data = new ArrayList<>();
            while (raw_data.size() < size){
                int a = (size - raw_data.size() > 1024) ? 1024 : size - raw_data.size();
                byte[] tmp = new byte[a];
                in.read(tmp, raw_data.size(), a);

                for(byte b : tmp){
                    raw_data.add(b);
                }
            }

            byte[] result = new byte[raw_data.size()];
            for (int i = 0; i < raw_data.size(); i++){
                result[i] = raw_data.get(i);
            }
            return result;
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()), 4);
//
//            // check for empty data!
//
//            char[] size_ch = new char[4];
//
//
//            br.read(size_ch, 0, 4);
//            int size;
//            size = ByteBuffer.wrap(new String(size_ch).getBytes()).order(ByteOrder.LITTLE_ENDIAN).getChar();
//            System.out.println(size);
//            List<Byte> raw_data = new ArrayList<>();
//            while (raw_data.size() < size){
//                System.out.println(size);
//                System.out.println(raw_data.size());
//                int a = (size - raw_data.size() > 1024) ? 1024 : size - raw_data.size();
//
//                br = new BufferedReader(new InputStreamReader(socket.getInputStream()), a);
//                // check for empty data!
//                char[] tmp = new char[a];
//                br.read(tmp, 0, a);
//                byte[] ttt = ByteBuffer.wrap(new String(tmp).getBytes()).order(ByteOrder.LITTLE_ENDIAN).array();
//                for(byte c : ttt){
//                    System.out.println("!!");
//                    raw_data.add(c);
//                }
//                System.out.println(raw_data.size());
//
//            }
//            System.out.println(raw_data.size());
//            byte[] result = new byte[raw_data.size()];
////            System.out.println(raw_data);
//            for (int i = 0; i < raw_data.size(); i++){
//                result[i] = raw_data.get(i);
//            }
////            System.out.println(result);
//            return new String(result, StandardCharsets.ISO_8859_1).getBytes();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
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
