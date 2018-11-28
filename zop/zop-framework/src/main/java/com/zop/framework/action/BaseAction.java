package com.zop.framework.action;

import com.opensymphony.xwork2.ActionSupport;
import com.zop.utils.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class BaseAction extends ActionSupport implements SessionAware {
	private static final Log logger = LogFactory.getLog(BaseAction.class);

    public static final String RESULT_AJAXJSON = "ajaxjson";

    protected Map session = null;
    private String action = "index";
    private AjaxMessagesJson ajaxMessagesJson;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
    public AjaxMessagesJson getAjaxMessagesJson() {
        return ajaxMessagesJson;
    }
    public void setAjaxMessagesJson(AjaxMessagesJson ajaxMessagesJson) {
        this.ajaxMessagesJson = ajaxMessagesJson;
    }

    protected int page = 1;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	protected String executeMethod(String method) throws Exception {
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}

    public String execute() {
        try {
            return this.executeMethod(this.getAction());
        } catch (Exception e) {
            logger.error(e);
            return ERROR;
        }
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    protected ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

    protected String getContextPath() {
        return getServletContext().getContextPath() + "/";
    }

    protected String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }

    protected Object getSession(String key) {
        if (this.session.containsKey(key)) {
            return this.session.get(key);
        }
        return null;
    }

    protected void setSession(String key, Object object) {
        if (this.session.containsKey(key)) {
            this.session.remove(key);
            this.session.put(key, object);
        }
    }

    protected void setAjaxError(String message,String jumpUrl) {
        this.ajaxMessagesJson = new AjaxMessagesJson();
        this.ajaxMessagesJson.setMessage(message,0, StringUtil.isEmpty(jumpUrl)?"":getContextPath() + jumpUrl);
    }

    protected void setAjaxSuccess(String message,String jumpUrl) {
        this.ajaxMessagesJson = new AjaxMessagesJson();
        this.ajaxMessagesJson.setMessage(message,1,StringUtil.isEmpty(jumpUrl)?"":getContextPath() + jumpUrl);
    }

    protected String getActionUrl(String name,String namespace,String param) {
        ActionMapping mapping = new ActionMapping(name,namespace,null,null);
        ActionMapper mapper = new DefaultActionMapper();
        if(StringUtil.isEmpty(param)) {
            return getContextPath() + mapper.getUriFromActionMapping(mapping);
        }else{
            return getContextPath() + mapper.getUriFromActionMapping(mapping) + "?" + param;
        }
    }

    protected String getAjaxActionUrl(String name,String namespace,String param) {
        ActionMapping mapping = new ActionMapping(name,namespace,null,null);
        ActionMapper mapper = new DefaultActionMapper();
        if(StringUtil.isEmpty(param)) {
            return mapper.getUriFromActionMapping(mapping);
        }else{
            return mapper.getUriFromActionMapping(mapping) + "?" + param;
        }
    }
}
