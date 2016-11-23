import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
	private Socket socket = null;
	private OutputStream out = null;
	private List<String> json = null;
	
	public Client(/*List<String> j*/) {
		//json = j;
		try {
			socket = new Socket("192.168.2.238", 8124);
			out = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client(List<String> j) {
		json = j;
		try {
			socket = new Socket("192.168.2.238", 8124);
			out = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write() {
		//String str = "hello";
		try {
			//StringBuilder builder = new StringBuilder();
			for(String str:json) {
				//builder.append(str);
				System.out.println(str);
				out.write(str.getBytes());
			}
			//out.write(builder.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String str) {
		//String str = "hello";
		try {
			
				out.write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*public static void main(String[] args) {
		Client c = new Client();
		String string = "{\"servos\":[{\"isTurn\":false,\"servoId\":9,\"servoSpeed\":1},{\"isTurn\":false,\"servoId\":10,\"servoSpeed\":1}]}\r\n";
		c.write(string);
		c.close();
	}*/
}
