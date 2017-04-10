package util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MySSLContext {
	private static final String KEY_STORE_TYPE_JKS = "PKCS12";

	private static final String KEY_STORE_PASSWORD = "123456";
	
	private static final String SRC_KEY_PASSWORD = "123456";

	public static SSLContext createSSLContext() {
		
		SSLContext sslContext = null;

		InputStream client_input = MySSLContext.class.getResourceAsStream("/mykey.p12");
		try {
			sslContext = SSLContext.getInstance("TLSv1.2");
			
			KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
			keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray());
//
//			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("PKIX");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("IbmX509");
			keyManagerFactory.init(keyStore, SRC_KEY_PASSWORD.toCharArray());
			
			TrustManager[] trustAllCerts = new TrustManager[] { 
				    new X509TrustManager() {     
				        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
				            return null;
				        } 
				        public void checkClientTrusted( 
				            java.security.cert.X509Certificate[] certs, String authType) {
				            } 
				        public void checkServerTrusted( 
				            java.security.cert.X509Certificate[] certs, String authType) {
				        }
				    } 
				}; 
			
			sslContext.init(null, trustAllCerts,
					new SecureRandom());
			return sslContext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				client_input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
