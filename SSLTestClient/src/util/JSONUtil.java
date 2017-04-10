package util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 *
 */
public class JSONUtil {

	public static <T> T TransJsonToObject(String resJson, Class<T> claz) {
		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			t = mapper.readValue(resJson, claz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
}
