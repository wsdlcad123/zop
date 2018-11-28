package com.zop.webutils.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Zop上下文初始化
 * @author kingapex
 *
 */
public class ZopContextIniter {
	
	public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		/**
		 * 将requst response及静态资源域名加入到上下文
		 */
		HttpSession session = httpRequest.getSession();
		ThreadContextHolder.getSessionContext().setSession(session);
		ThreadContextHolder.setHttpRequest(httpRequest);
		ThreadContextHolder.setHttpResponse(httpResponse);
		String servletPath = httpRequest.getServletPath();

		// System.out.println("uri : "+ RequestUtil.getRequestUrl(httpRequest));
	}
}
