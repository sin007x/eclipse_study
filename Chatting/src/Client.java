
// 클라이언트 파트 구현 목록
//(1)서버에 접속
//(2)서버로 채팅메시지 보내기
//(3)서버에서 보내오는 채팅 메시지 수신
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//클라이언트 화면
class ClientUI extends JFrame{
	JTextField jtf; // 한줄 입력창
	JButton con; // 서버 접속 버튼
	JTextArea jta; //대화 내용을 보기위한 창
	
	String nick = ""; //닉네임 저장을 위한 변수 
	
	Socket client;	//서버와의 통신을 위한 클라이언트쪽 종이컵
	InputStream is; //쓰기 위한 실
	OutputStream os; //읽기 위한 실
	
	// 서버에서 보내오는 메시지 수신을 위한 thread
	class ClientThread extends Thread{
		public void run() {//callback method
			//(3)서버에서 보내오는 채팅 메시지 지속적으로 수신
			while(true) {
				try {
					byte[] b = new byte[1024];
					is.read(b);
					//수신된 메시지 화면(대화창)에 보여주기
					String msg = new String(b); // 바이트배열 -> 문자열 변환
//					jta.setText(msg.trim()); // 대화창에 보여주기
					jta.append(msg.trim()+'\n');
				} catch (IOException e) {
					System.out.println("서버에서 보내오는 메시지 수신중 오류발생");
				}
			}
			
		}
	}
	
	//Inner class(내부 클래스)
	//를 사용하는 가장 큰 이유는 외부 클래스 멤버변수에 접근 가능하다는 점 때문
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//callback method : 개발자인 저희가 호출못함, 시스템이 특정된 조건을 만족하면 호출해줌
			//버튼 클릭 or "enter"키 입력 되면 호출되는 콜백메소드
			
			String str = e.getActionCommand(); // 클릭된 버튼에 이름 가져오기
			if(str.equals("서버접속")) {
				//(1)서버에 접속
				try {
					client = new Socket("127.0.0.1", 8888);
//					client = new Socket("10.0.153.230",8888);
					//서버접속에 성공하면
					jtf.setEnabled(true); // 입력창 사용할 수 있도록 변경
					con.setText("서버접속완료");
					con.setEnabled(false);// 서버접속 버튼 비활성화
					is = client.getInputStream();	//읽기 전용 실 뽑아내기
					os = client.getOutputStream();	//쓰기 전용 실 뽑아내기
					new ClientThread().start(); //서버가 보내오는 메시지 수신할 수 있도록 thread start 

				} catch (IOException e1) {
					System.out.println("서버접속 실패");
				}
				System.out.println("버튼이 클릭됨");
			}else { //"Enter" 입력한경우
				//(2)서버로 채팅메시지 보내기
				String msg = "["+nick+"]"+jtf.getText();//입력창에 기입된 채팅 내용 가져오기
				try {
					os.write(msg.getBytes());
					//서버로 메시지 전송을 성공하고나면
					jtf.setText(""); // 입력창 초기화
				} catch (IOException e1) {
					System.out.println("서버로 메시지 전송중 오류 발생");
				}
				
			}
		}
		
	}
	
	public ClientUI() {
		nick = JOptionPane.showInputDialog("닉네임입력해주세요");
		jtf = new JTextField(20);
		con = new JButton("서버접속");
		jta = new JTextArea(20,20);
		
		jtf.setEnabled(false);// 서버 접속전까지는 입력창 사용불가로 셋팅
		
		MyListener m = new MyListener();	//감시자 객체화
		con.addActionListener(m); //접속버튼(con)에 다가 감시자 달아주기
		jtf.addActionListener(m); //입력창(jtf)에 다가 감시자 달아주기
		
		
		//화면 배치관리자 생성
		FlowLayout layout = new FlowLayout();
		this.setLayout(layout); //현재 화면(판때기)에 배치관리자 설정
		
		
		this.add(jtf);
		add(con);
		add(jta);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //강제 종료 기능 활성화
		
		setSize(250,480);
		setLocation(300,300);
		setVisible(true);
	}
	
}

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		new ClientUI();
		
		//(1)서버에 접속
//		Socket client = new Socket("127.0.0.1", 8888);

	}

}
