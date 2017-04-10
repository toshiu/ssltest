package util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * this class holds environment specific values. eg. DB Schema names
 * 
 *
 */
public class Config {

	private static Map<String, String> props = null;
	private static Map<String, String> forwards = null;

	public static String getProp(String name) {
		if (props != null) {
			return props.get(name);
		} else {
			props = new HashMap<String, String>();
			Properties pps = new Properties();
			try {
				pps.load(Config.class.getClassLoader().getResourceAsStream(SysConst.FILE_PROP_PATH));
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Enumeration<?> enum1 = pps.propertyNames();
			while (enum1.hasMoreElements()) {
				String strKey = (String) enum1.nextElement();
				String strValue = pps.getProperty(strKey);
				props.put(strKey, strValue);
			}
			return props.get(name);
		}
	}
//	
//	public static String getForward(String pageId) {
//		if (forwards != null) {
//			return forwards.get(pageId);
//		} else {
//			forwards = new HashMap<String, String>();
//			Properties pps = new Properties();
//			try {
//				pps.load(Config.class.getClassLoader().getResourceAsStream(SysConst.FILE_FORWARD_PATH));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//			Enumeration<?> enum1 = pps.propertyNames();
//			while (enum1.hasMoreElements()) {
//				String strKey = (String) enum1.nextElement();
//				String strValue = pps.getProperty(strKey);
//				forwards.put(strKey, strValue);
//			}
//			return forwards.get(pageId);
//		}
//	}
}
