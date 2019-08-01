package mvc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class Router implements Filter {
	Logger logger=Logger.getLogger(this.getClass());
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	String user = "asd";
    	String contextPath = request.getRequestURL().toString();
    	String origin = request.getHeader("Origin");
    	response.setHeader("Access-Control-Allow-Origin", origin==null?"*":origin);
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	String method = request.getMethod();
//    	System.out.println("###"+method);
//    	if(method.toUpperCase().equals("OPTIONS")){
//    		response.setHeader("Access-Control-Allow-Origin", "*");
//	    	response.setHeader("Access-Control-Allow-Credentials", "true");
//    	}
    	if(null == user && !method.toUpperCase().equals("OPTIONS")){
    		if(contextPath.indexOf("/login/login") >= 0
					|| contextPath.indexOf("/login.html") >= 0
					|| contextPath.indexOf("/index.html") >= 0
					|| contextPath.indexOf("/imgs/") >= 0
					|| contextPath.indexOf("/app/") >= 0
					|| contextPath.indexOf("/resources/") >= 0
					|| contextPath.indexOf("/libs/") >= 0
					){
    			
			}else{
//				PrintWriter p = response.getWriter();
//	    		p.write("{\"code\":300}");
//	    		p.flush();
//	    		p.close();
				response.sendRedirect("/yc_amyxt/login.html");
				return;
			}
    		
    		
		}
		try {
			arg2.doFilter(arg0, arg1);
		} catch (Exception e) {
			String exceptionString = ExceptionUtils.getFullStackTrace(e);
			System.out.println(exceptionString);
			logger.error(exceptionString);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
