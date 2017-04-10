package apl;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import util.MySSLContext;
import util.RestClient;

/**
 * Servlet implementation class BackServlet
 */
public class TestServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger("InfoLogger");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OkHttpClient httpClient;
		
		OkHttpClient.Builder builder = new OkHttpClient.Builder();  
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).allEnabledCipherSuites().allEnabledTlsVersions()
                .build();
		
//		httpClient = builder.connectionSpecs(Collections.singletonList(spec)).connectTimeout(1, TimeUnit.MINUTES)
//				.readTimeout(1, TimeUnit.MINUTES).socketFactory(MySSLContext.createSSLContext().getSocketFactory()).build();
		httpClient = builder.connectionSpecs(Collections.singletonList(spec)).connectTimeout(1, TimeUnit.MINUTES)
				.readTimeout(1, TimeUnit.MINUTES).build();
		
		okhttp3.Request.Builder reqBuilder = new Request.Builder();
		Request req = reqBuilder.url("https://adminib-opi4vn1:10443/stc/").get().build();
//		Request req = reqBuilder.url("https://console.ng.mybluemix.net").get().build();
//		Request req = reqBuilder.url("https://kyfw.12306.cn/otn/").get().build();

//		Request req = reqBuilder.url("https://service.api.mizuhobank.co.jp/bk/apibanking/retail/st/oauth2/authorize?response_type=code&client_id=dfef4b97-4efe-4550-897b-72e8e6324859&scope=inquiry&redirect_uri=https://ftdemo.mybluemix.net/index.jsp").get().build();

		
		okhttp3.Response res = httpClient.newCall(req).execute();
		
		System.out.println(res.code());
		System.out.println(res.body().string());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
