package com.zop.framework.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zop.webutils.context.ZopContextIniter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocalInterceptor extends AbstractInterceptor {
	private static final Log logger = LogFactory.getLog(ThreadLocalInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
        ZopContextIniter.init(request, response);
        return invocation.invoke();
	}

}
