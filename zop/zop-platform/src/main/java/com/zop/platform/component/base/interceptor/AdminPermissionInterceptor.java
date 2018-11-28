package com.zop.platform.component.base.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.service.IMenuService;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.WebSessionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPermissionInterceptor extends AbstractInterceptor {
	private static final Log logger = LogFactory.getLog(AdminPermissionInterceptor.class);
    private boolean isLoad = false;
    private Map menu_map = new HashMap();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ac = invocation.getInvocationContext();
		String actionName = "/" + ac.getName();
		String saction = "";
		Map map = ac.getParameters();
		String[] _saction = (String[]) map.get("action");
		if (_saction != null) {
			saction = _saction[0];
		}
		logger.info("[actionName:" + actionName + ",saction:" + saction + "]");

		boolean havePermission = false;

        HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
        ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        String curl = request.getRequestURI();
        if(StringUtils.isNotBlank(saction)){
            curl = curl+"?action=" + saction;
        }
        if(!isLoad){
            logger.info("load Menu:" + isLoad);
            IMenuService menuService = (IMenuService) wc.getBean("menuService");
            List l = menuService.list("id", "asc");
            for(int i=0;i<l.size();i++){
                Menu m = (Menu) l.get(i);
                if(StringUtils.isNotEmpty(m.getUrl())) {
                    menu_map.put(servletContext.getContextPath() + "/" + m.getUrl(), String.valueOf(m.getId()));
                }
            }
            isLoad = true;
        }
        String cmenuId = (String) menu_map.get(curl);
        if(StringUtils.isNotBlank(cmenuId)){
            // session信息
            WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
            Map userMenu  = (Map) sessonContext.getAttribute("admin_menu_ids");
            String menuId = (String)userMenu.get(cmenuId);
            if(StringUtils.isBlank(menuId)){
                ac.getValueStack().set("error", "权限不够");
                return "error";
            }
            return invocation.invoke();
        }
        return invocation.invoke();
	}

}
