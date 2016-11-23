import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;

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
	
	public static void main(String[] args) {
		WebBrowser wBrowser = new WebBrowser();
		wBrowser.initBrowser();
		wBrowser.setVisible();
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
		frame.add(browserView, BorderLayout.CENTER);
		frame.setSize(700, 500);
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
}
