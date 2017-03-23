import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Parse {
	private String toParse = null;
	private List<String> json = null;
	private WebBrowser browser = null;
	private String jsonStr =null;
	
	public Parse(String s) {
		toParse = s;
		json = new ArrayList<>();
	}
	
	public Parse(WebBrowser b) {
		browser = b;
		json = new ArrayList<>();
	}
	
	//http://pencilcode.net/log/first?run&mode=b&lang=cs&code=fd+100|rt+90|
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
		jsonStr = "{\"servos\":[{\"isTurn\":false,\"servoId\":9,\"servoSpeed\":"
				+a+"},{\"isTurn\":false,\"servoId\":10,\"servoSpeed\":"
				+b+"}]}\r\n";
		json.add(jsonStr);
		
		//System.out.println(json);
	}
	
	public List<String> getJSON() {
		return json;
	}
	
	/*{
    "sensors": [
        {
            "name": "distance",
            "id": 0,
            "value": 0
        },
        {
            "name": "distance",
            "id": 1,
            "value": 0
        },
        {
            "name": "touch",
            "id": 1,
            "value": 0
        }
    ]
}*/
	/*
	 * code=fd+100|rt+90|
	 */
	
	public void setText(String json) {
		JSONObject jObj = new JSONObject(json);
		JSONArray jAry = jObj.getJSONArray("sensors");
		StringBuilder  builder = new StringBuilder();
		
		for(int i=0;i<jAry.length();i++) {
			JSONObject jsonObject = jAry.getJSONObject(i);
			builder.append("name:"+jsonObject.getString("name")+"\n");
			builder.append("id:"+jsonObject.getString("id")+"\n");
			builder.append("value:"+jsonObject.getString("value")+"\n");
			
			//bizhang
			if(jsonObject.getString("name").equals("distance")
					&&jsonObject.getString("id").equals("0")) {
				if(Double.parseDouble(jsonObject.getString("value"))<10
						&&Double.parseDouble(jsonObject.getString("value"))>0) {
					toParse = "http://pencilcode.net/log/first?run&mode=b&lang=cs&code=fd+0|";
					send();
				} else {
					toParse = "http://pencilcode.net/log/first?run&mode=b&lang=cs&code=fd+1|";
					send();
				}
			}
		}
		
		
		browser.setText(builder.toString());
	}
	
	private void send() {
		toJSON();
		try {
			Thread.sleep(1000);
			Client client = new Client(json);
			client.write(jsonStr);
			client.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
