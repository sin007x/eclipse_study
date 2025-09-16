import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOEx {

	public static void main(String[] args) throws IOException {
//		// 파일에 데이터 읽고쓰기 예제
//		//(1) 파일에 1byte 단위로 데이터 쓰기
//		FileOutputStream fos = new FileOutputStream("d://javatest/test.txt");
//		BufferedOutputStream bos = new BufferedOutputStream(fos);
//		// 문자열 -> 바이트배열로 변환
//		String str = "ABC김길동";
//		byte[]  b = str.getBytes("utf-8");
//		bos.write(b); // 이때 비로 파일에 write하는게 아님 ! 버퍼에 채우기만 함
//		bos.flush(); // 버퍼에 있는 데이터 밀어내기(파일에 쓰기)
//		
//		//(2) 파일에서 1byte 단위로 데이터 읽기
//		FileInputStream fis = new FileInputStream("d://javatest/test.txt");
//		byte[] r = new byte[1024];
//		fis.read(r);
//		//바이트배열 -> 문자열
//		String temp = new String(r, "utf-8");
//		System.out.print(temp.trim()); //trim() 빈공백 제거
//		System.out.print("END");
		
		
		
//		int result = fis.read();
//		char c = (char)result;	// 타입 캐스팅
//		System.out.println(c);
		
		
		//characterStream 계열 사용
		//파일에 문자 쓰기
		FileWriter fw = new FileWriter("d://javatest/test.txt");
		String msg = "ABCDEF가나다";
		fw.write(msg);
		fw.flush(); // 데이터 출력 flush()로 마무리
		
		//파일에서 문자 읽기
		FileReader fr = new FileReader("d://javatest/test.txt");
		BufferedReader br = new BufferedReader(fr);
		String result = br.readLine();
		System.out.println(result);
	}

}
