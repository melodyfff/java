package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @Description:
 * @author xinchen 2016年9月16日 下午4:51:34
 */
public class GreetingClient {

	private final static Logger LOGGER = LoggerFactory.getLogger(GreetingClient.class);

	public static void main(String[] args) {
//		String serverName = args[0];
//		int port = Integer.parseInt(args[1]);
		String serverName = "127.0.0.1";
		int port = 8083;
		try {
			LOGGER.info("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			LOGGER.info("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF("Hello from " + client.getLocalSocketAddress());
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			LOGGER.info("Server says " + in.readUTF());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
