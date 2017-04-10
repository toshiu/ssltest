package util;

public class StringUtil {

	public static boolean isNumber(Object val) {
		if (val == null) {
			return false;
		}
		try {
			Integer.parseInt(String.valueOf(val));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isEmpty(String target) {
		if (target == null || "".equals(target.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
}
