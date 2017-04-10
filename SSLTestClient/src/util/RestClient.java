package util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestClient {

	public static OkHttpClient httpClient;

	public static OkHttpClient getClient() throws NoSuchAlgorithmException, KeyManagementException {
		System.setProperty("jsse.enableSNIExtension", "false");
		if (httpClient == null) {
  
            OkHttpClient.Builder builder = new OkHttpClient.Builder();  
            
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).allEnabledCipherSuites().allEnabledTlsVersions().build();
			httpClient = builder.connectionSpecs(Collections.singletonList(spec)).connectTimeout(1, TimeUnit.MINUTES)
					.readTimeout(1, TimeUnit.MINUTES).build();
		}
		return httpClient;
	}

	public static Request createUrlGetRequest(String url, Map<String, String> headMap) {

		Request req = null;

		okhttp3.Request.Builder reqBuilder = new Request.Builder();

		if (headMap == null) {
			req = reqBuilder.url(url).get().build();
		} else {
			for (Map.Entry<String, String> header : headMap.entrySet()) {
				reqBuilder.addHeader(header.getKey(), header.getValue());
			}
			req = reqBuilder.url(url).get().build();
		}
		return req;
	}

	public static Request createFormPostRequest(String url, Map<String, String> headMap, Map<String, String> formMap) {

		RequestBody formBody = null;
		Request req = null;

		okhttp3.FormBody.Builder formBuilder = new FormBody.Builder();
		if (formMap != null) {
			for (Map.Entry<String, String> entry : formMap.entrySet()) {
				formBuilder.add(entry.getKey(), entry.getValue());
			}
		}
		formBody = formBuilder.build();
		
		okhttp3.Request.Builder reqBuilder = new Request.Builder();
		if (headMap != null) {
			for (Map.Entry<String, String> header : headMap.entrySet()) {
				reqBuilder.addHeader(header.getKey(), header.getValue());
			}
		}
		req = reqBuilder.url(url).post(formBody).build();
		return req;

	}

}