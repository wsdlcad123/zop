package com.zop.webutils.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zop.webutils.context.impl.WebSessionContextImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *  用ThreadLocal来存储Session,以便实现Session any where 
 * @author zhangbaochao
 */
public class ThreadContextHolder  {
    private final Log logger= LogFactory.getLog(getClass());
	
	private static ThreadLocal<WebSessionContext> SessionContextThreadLocalHolder = new ThreadLocal<WebSessionContext>();
	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
	
	
	public static void setHttpRequest(HttpServletRequest request){
		HttpRequestThreadLocalHolder.set(request);
	}
	
	public static HttpServletRequest getHttpRequest(){
		return  HttpRequestThreadLocalHolder.get();
	}
	
	public static void remove(){
		SessionContextThreadLocalHolder.remove();
		HttpRequestThreadLocalHolder.remove();
		HttpResponseThreadLocalHolder.remove();
	}
	
	
	public static void setHttpResponse(HttpServletResponse response){
		HttpResponseThreadLocalHolder.set(response);	
	}
	
	public static HttpServletResponse getHttpResponse(){
		
		return HttpResponseThreadLocalHolder.get();
	}

	public static void setSessionContext(WebSessionContext context) {
		SessionContextThreadLocalHolder.set(context);
	}

	public static void destorySessionContext() {
		WebSessionContext context = SessionContextThreadLocalHolder.get();
		if (context != null) {
			context.destory();
		}
	}

	public static  WebSessionContext  getSessionContext() {
		if (SessionContextThreadLocalHolder.get() == null) {
			SessionContextThreadLocalHolder.set(new WebSessionContextImpl());
		}
		return SessionContextThreadLocalHolder.get();
	}
	
}
