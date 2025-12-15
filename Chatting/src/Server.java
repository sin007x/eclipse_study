
// 서버파트 구현 목록
//1)클라이언트 접속을 지속적으로 대기
//2)접속한 클라이언트 보내온 메시지 지속적으로 수신
//3)수신된 메시지 모든 클라이언트에게 전송

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//클라이언트 접속 지속적으로 대기하면서 클라이언트들이 보내오는 메시지 수신도 해야하기 때문에
//Thread를 만들었다.
class ServerThread extends Thread{
	Socket server;
	boolean flag = true;	//스레드 종료 여부를 결정하는 변수
	public ServerThread(Socket server) {
		this.server = server;
	}
	public void run() {
		//2)접속한 클라이언트가 보내오는 메시지 지속적으로 수신
		InputStream is;
		while(flag) {
			byte[] b = new byte[1024];
			try {
				is = server.getInputStream(); // 종이컵에서 읽기 위한 실 뽑아내기
				is.read(b);
			} catch (IOException e) {
				//클라이언트가 강제 종료되었으나 
				//여전히 서버에서는 해당 클라이언트로부터 메시지를 read() 하고 있음
				//이에따라 IOException 이 발생
				//*해결을 위해서는 이제 종료된 클라이언트를 담당하는 스레드는 더 이상 필요가 없으므로 
				//해당 스레드도 종료 해주면 됨.
				flag = false; 	// while문 종료  => 현재 스레드 종료
				
				System.out.println("클라이어트로부터 메시지 수신하다가 문제발생");
			}
			
			//3)수신된 메시지 모든 클라이언트에게 전송
			OutputStream os;
			
				for (int i = 0 ; i < Server.totalSocket.size(); i++) {
				// 종이컵 저장공간에서 종이컵 가져오기
				Socket temp = (Socket)Server.totalSocket.get(i); // 0 번째 종이컵 가져오기
				try {
				os = temp.getOutputStream();// 쓰기위한 실 뽑아내기
				os.write(b);
				}catch (IOException e) {
				// TODO Auto-generated catch block
				//이제 접속이 종료된 클라이언트와 연결되어 있던 종이컵이
					//여전히 저장공간에 남이 있고 그 종이컵을 통해서 
					//존재하지 않는 클라이언트에게 메세지를 전송하려다 보니 오류가 발생하는 상황
					//*해결책
					//-> 이제 더이상 사용하지 않는 종이컵 삭제
					Server.totalSocket.remove(i); //i번째 종이컵 삭제
					System.out.println("모든 클라이어트로부터 메시지 송신하다가 문제발생");
				}
			}
		}
	}
}

public class Server {
	
	static ArrayList totalSocket = new ArrayList();

	public static void main(String[] args) throws IOException {
		
		System.out.println("[서버 스타트]");
		
		
		//1)클라이언트 접속을 지속적으로 대기
		ServerSocket ss = new ServerSocket(8888);
		while (true) {
			//클라이언트 접속을 대기 + 접속된 클라이언트와의 통신을 위한 종이컵 생성
			Socket server = ss.accept(); //blocking method
			totalSocket.add(server); //종이컵 저장
			// 방금 접속한 클라이언트를 담당하게 될 스레드 객체화 후에 start()
			new ServerThread(server).start();
			
		}
			
	}

}
