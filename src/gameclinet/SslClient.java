package gameclinet;

import gameclinet.helper.network.SslContextProvider;
import gameclinet.helper.network.SslUtil;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static gameclinet.helper.network.SslUtil.*;

public class SslClient implements SslContextProvider {

    private SSLSocket socket;
    private int host_port;
    private String host_ip;

    SslClient() {
        host_ip = Config.getConfigIns().config.getJSONObject("net").getString("host");
        host_port = Config.getConfigIns().config.getJSONObject("net").getInt("port");
    }


    @Override
    public KeyManager[] getKeyManagers() throws GeneralSecurityException, IOException {
        return createKeyManagers("client.jks", "geheim".toCharArray());
    }

    @Override
    public String getProtocol() {
        return "TLSv1.2";
    }

    @Override
    public TrustManager[] getTrustManagers() throws GeneralSecurityException, IOException {
        return createTrustManagers("../cacert.jks", "geheim".toCharArray());
    }

    void connect()  {
        try {
            socket = createSSLSocket(host_ip, host_port);

            System.out.println("Pong obtained! Ending client...");
        }catch (Exception e){

        }

    }

    private SSLSocket createSSLSocket(String host, int port) throws Exception {
        return SslUtil.createSSLSocket(host, port, this);
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
        OutputStream os;

        try {
            os = socket.getOutputStream();

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