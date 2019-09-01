package team.koala.chillin.client;

import team.koala.chillin.client.helper.network.SSLUtil;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;


public class Network {

	private SSLSocket socket;
	private int hostPort;
	private String hostIp;


	public Network() {
		hostIp = Config.getInstance().config.getJSONObject("net").getString("host");
		hostPort = Config.getInstance().config.getJSONObject("net").getInt("port");
	}

	public void connect() throws Exception {
		SSLSocketFactory socketFactory = SSLUtil.getAllTrustedSocketFactory();
		SSLSocket sslSocket;
		sslSocket = (SSLSocket) socketFactory.createSocket(hostIp, hostPort);
		sslSocket.startHandshake();
		socket = sslSocket;
	}

	public String getHostIp() {
		return hostIp;
	}

	public int getHostPort() {
		return hostPort;
	}


	public byte[] recvData() {
		try {
			InputStream in = socket.getInputStream();
			byte[] size_by = new byte[4];
			in.read(size_by, 0, 4);
			int size = ByteBuffer.wrap(size_by).order(ByteOrder.LITTLE_ENDIAN).getChar();

			List<Byte> raw_data = new ArrayList<>();
			while (raw_data.size() < size) {
				int a = (size - raw_data.size() > 1024) ? 1024 : size - raw_data.size();
				byte[] tmp = new byte[a];
				in.read(tmp, raw_data.size(), a);

				for (byte b : tmp) {
					raw_data.add(b);
				}
			}

			byte[] result = new byte[raw_data.size()];
			for (int i = 0; i < raw_data.size(); i++) {
				result[i] = raw_data.get(i);
			}
			return result;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}

	public int sendData(byte[] data) {
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

	private static List<Byte> b2B(byte[] bytes) {
		List<Byte> result = new ArrayList<>();
		for (byte b : bytes)
			result.add(b);
		return result;
	}

	private static List<Byte> b22B(byte[] bytes1, byte[] bytes2) {
		List<Byte> result = new ArrayList<>();

		for (byte b : bytes1)
			result.add(b);

		for (byte b : bytes2)
			result.add(b);

		return result;
	}

	private static byte[] B2b(List<Byte> bytes) {
		byte[] result = new byte[bytes.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = bytes.get(i);
		return result;
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
