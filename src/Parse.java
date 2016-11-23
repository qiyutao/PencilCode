import java.util.ArrayList;
import java.util.List;

public class Parse {
	private String toParse = null;
	private List<String> json = null;
	
	public Parse(String s) {
		toParse = s;
		json = new ArrayList<>();
	}
	
	public void toJSON() {
		String[] element = toParse.split("\\|");
		for(String tmp:element) {
			
			System.out.println(tmp);
			
			if(tmp.indexOf("fd")!=-1) {
				double dis = getValue(tmp);
				setJSON(dis, dis);
			} else if(tmp.indexOf("rt")!=-1) {
				double dis = getValue(tmp);
				setJSON(1, 1-dis/90);
			} else if(tmp.indexOf("lt")!=-1) {
				double dis = getValue(tmp);
				setJSON(1-dis/90, 1);
			} else if(tmp.indexOf("bk")!=-1) {
				double dis = getValue(tmp);
				setJSON(-dis, -dis);
			} 
		}
	}
	
	private double getValue(String cmd) {
		String[] tmp = cmd.split("\\+");
		return Double.parseDouble(tmp[tmp.length-1]);
	}
	
	private void setJSON(double a,double b) {
		String jsonStr = "{\"servos\":[{\"isTurn\":false,\"servoId\":9,\"servoSpeed\":"
				+a+"},{\"isTurn\":false,\"servoId\":10,\"servoSpeed\":"
				+b+"}]}\r\n";
		json.add(jsonStr);
	}
	
	public List<String> getJSON() {
		return json;
	}
}
