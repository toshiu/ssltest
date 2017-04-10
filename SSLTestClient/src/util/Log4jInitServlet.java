package util;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		PropertyConfigurator.configure(prefix + file);
	}
}
