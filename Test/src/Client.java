import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket client = new Socket("10.0.153.230", 8888);
		OutputStream os = client.getOutputStream();
		String msg = "대구과학대학교";
		os.write(msg.getBytes());
	}

}
