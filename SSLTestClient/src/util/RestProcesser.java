package util;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;

public class RestProcesser {
	private static final Logger logger = Logger.getLogger("IBMTestLogger");

	public String getAuthCode(String url, String rstate, String appName, String confirmation)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		Response resSecond = null;

		String code = null;

		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(url).append("&username=user&confirmation=").append(confirmation).append("&app-name=")
				.append(appName).append("&rstate=").append(rstate);

		String reqUrl = urlBuilder.toString();

		logger.info("認可コード発行のURL：" + reqUrl);

		Request req = RestClient.createUrlGetRequest(reqUrl, null);

		resSecond = RestClient.getClient().newCall(req).execute();

		String resBody = resSecond.body().string();

		if (resSecond.isSuccessful()) {
			logger.info("応答ステータス：" + resSecond.code());
			String resSecondUrl = null;

			resSecondUrl = URLDecoder.decode(resSecond.request().url().toString(), "utf-8");
			code = resSecondUrl.substring(resSecondUrl.indexOf("code=") + "code=".length());

			logger.info("code：" + code);
			resSecond.close();
		} else {
			logger.info("応答ステータス：" + resSecond.code());
			Set<String> headerNames = resSecond.headers().names();
			StringBuilder outHeaderStr = new StringBuilder();
			outHeaderStr.append("{");
			outHeaderStr.append("\n");
			for (String key : headerNames) {
				outHeaderStr.append("\"");
				outHeaderStr.append(key);
				outHeaderStr.append("\":");
				String value = resSecond.headers().get(key);
				outHeaderStr.append("\"");
				outHeaderStr.append(value);
				outHeaderStr.append("\"");
				outHeaderStr.append("\n");
			}
			outHeaderStr.append("}");
			logger.info("認可コード発行失敗原因のヘッダー。　" + outHeaderStr.toString());
			logger.info("認可コード発行失敗原因の主体。　" + resBody);
			logger.info("認可コード発行失敗原因のメッセージ。　" + resSecond.message());
			resSecond.close();
			throw new IOException("認可コード発行失敗");
		}

		return code;
	}

	public String getToken(String url, String code, String clientId, String clientPwd, String redirectUri)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		Map<String, String> formMap = new HashMap<String, String>();
		formMap.put("grant_type", "authorization_code");
		formMap.put("redirect_uri", redirectUri);
		formMap.put("code", code);

		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("Authorization", Credentials.basic(clientId, clientPwd));
		headMap.put("Content-Type", "application/x-www-form-urlencoded");

		Response res = null;

		Request req = RestClient.createFormPostRequest(url, headMap, formMap);

		res = RestClient.getClient().newCall(req).execute();

		logger.info("要求Method: " + req.method());

		String resBody = res.body().string();

		if (res.isSuccessful()) {
			logger.info("応答ステータス：" + res.code());
			res.close();
		} else {
			logger.info("応答ステータス：" + res.code());
			Set<String> headerNames = res.headers().names();
			StringBuilder outHeaderStr = new StringBuilder();
			outHeaderStr.append("{");
			outHeaderStr.append("\n");
			for (String key : headerNames) {
				outHeaderStr.append("\"");
				outHeaderStr.append(key);
				outHeaderStr.append("\":");
				String value = res.headers().get(key);
				outHeaderStr.append("\"");
				outHeaderStr.append(value);
				outHeaderStr.append("\"");
				outHeaderStr.append("\n");
			}
			outHeaderStr.append("}");
			logger.info("トークン発行失敗原因のURL。　" + url);
			logger.info("トークン発行失敗原因のヘッダー。　" + outHeaderStr.toString());
			logger.info("トークン発行失敗原因の主体。　" + resBody);
			logger.info("トークン発行失敗原因のメッセージ。　" + res.message());
			res.close();
			throw new IOException("トークン発行失敗");
		}

		return resBody;
	}

}
