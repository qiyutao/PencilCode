import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.teamdev.jxbrowser.chromium.BeforeSendHeadersParams;
import com.teamdev.jxbrowser.chromium.BeforeURLRequestParams;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.NetworkService;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

@SuppressWarnings("unused")
public class WebBrowser {
	private Browser browser = null;
	private JTextArea textArea = null;
	
	public static void main(String[] args) {
		WebBrowser wBrowser = new WebBrowser();
		wBrowser.initBrowser();
		wBrowser.setVisible();
		wBrowser.onRecv();
	}
	
	private void initBrowser() {
		BrowserContext browserContext = BrowserContext.defaultContext();
		 NetworkService service= browserContext.getNetworkService();
		 service.setNetworkDelegate(new DefaultNetworkDelegate() {
			    @Override
			    public void onBeforeURLRequest(BeforeURLRequestParams params) {
			        
			        getURL(params.getURL());
			    }
			});
		 browser = new Browser(browserContext);
	}
	
	private void setVisible() {
		BrowserView browserView = new BrowserView(browser);

		JFrame frame = new JFrame("JxBrowser");
		textArea = new JTextArea();
		
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		
		//panel.add(browserView);
		frame.add(browserView, BorderLayout.CENTER);
		frame.add(textArea, BorderLayout.EAST);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		browser.loadURL("http://pencilcode.net/edit/first");
	}
	
	private void getURL(String url) {
		//System.out.println(url);
		if(url.indexOf("first?run&")!=-1) {
			System.out.println(url);
			//TODO 
			Parse parse = new Parse(url);
			parse.toJSON();
			List<String> json = parse.getJSON();
			//StringBuilder builder = new StringBuilder();
			
			for(String str:json) {
				//builder.append(str);
				System.out.println(str);
			}
			
			
			sendJSON(json);
		}
	}
	
	private void sendJSON(List<String> json) {
		
		for(String str:json) {
			//builder.append(str);
			try {
				Thread.sleep(3000);
				Client client = new Client(json);
				client.write(str);
				client.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void onRecv() {
		Client client = new Client(this);
	}
	
	public void setText(String msg) {
		textArea.setText(msg);
		System.out.println(msg);
	}
}
