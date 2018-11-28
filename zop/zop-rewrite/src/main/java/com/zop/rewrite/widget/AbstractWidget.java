package com.zop.rewrite.widget;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.rewrite.core.FreeMarkerPaser;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.ZopWebUtilsSetting;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 基于freemarker的挂件基类
 * 
 * @author kingapex 2010-1-29上午10:08:46
 */
abstract public class AbstractWidget implements IWidget {

    protected  final Log logger = LogFactory.getLog(getClass());

	// 是否要解析html并显示
	protected boolean showHtml = true;
	protected FreeMarkerPaser freeMarkerPaser;
	protected String action;
    protected String folder; // 自定义挂件页面所在文件夹
	protected String pageName;// 挂件页面，可通过setPageName方法设置

	/**
	 * 完成freemarker的模板处理<br/>
	 * 模板路径是子类挂件所在包<br/>
	 * 在解析模板之前会调用子类的 {@link #display(java.util.Map)}方法来设置挂件模板中的变量
	 */

	public String process(Map<String, String> params) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        action = request.getParameter("action");
		// 显示挂件html
		String html = show(params);
		return html;
	}

	/**
	 * 根据参数字串压入request的参数
	 * 
	 * @param reqparams
	 *            要获取reqeust中参数的参数名字，以,号隔开，如：name1,name2
	 */
	private void putRequestParam(String reqparams, Map<String, String> params) {
		if (!StringUtil.isEmpty(reqparams)) {
			HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
			String[] reqparamArray = StringUtils.split(reqparams, ",");
			for (String paramname : reqparamArray) {
				String value = httpRequest.getParameter(paramname);
				params.put(paramname, value);
			}
		}
	}

	private String show(Map<String, String> params) {
		freeMarkerPaser = FreeMarkerPaser.getInstance();
		/**
		 * ----------------------- 压入request中的参数值 -----------------------
		 */
		String reqparams = params.get("reqparams");
		putRequestParam(reqparams, params);

		freeMarkerPaser.putData(params);

		/**
		 * ------------------------------------------------------------------
		 * 如果在widgets.xml中指定了custom_page，则使用指定的页面
		 * 如果指定action页面，使用action页面
		 * -------------------------------------------------------------------
		 */
		String customPage = params.get("custom_page");
		this.folder = params.get("folder");

		// 定义此挂件是否显示html
		String showHtmlStr = params.get("showhtml");
		showHtml = true;

		/**
		 * -------------------------------------------------------------------
		 * 执行挂件实现的display
		 * --------------------------------------------------------------------
		 */
		display(params);

        // 如果指定自定义页面，使用自定义页面
        if (!StringUtil.isEmpty(customPage)) {
            pageName = customPage;
        }

        // 处理action页面，如果指定action页面，使用action页面，否则使用默认页面
        if (!StringUtil.isEmpty(action)) {
            String actionPage = params.get("action_" + action);
            if (!StringUtil.isEmpty(actionPage)) {
                pageName = actionPage;
            }
        }

        if (!StringUtil.isEmpty(pageName)) {
            this.freeMarkerPaser.setPageName(pageName);
        }
        if (!StringUtil.isEmpty(this.folder)) {
            folder = ZopRewriteSetting.THEMES_STORAGE_PATH + ZopRewriteSetting.CURRENT_THEMES_PATH + "/" + folder;
        }else{
            folder = ZopRewriteSetting.THEMES_STORAGE_PATH + ZopRewriteSetting.CURRENT_THEMES_PATH;
        }
        this.freeMarkerPaser.setPageFolder(folder);

		if (!StringUtil.isEmpty(showHtmlStr) && showHtmlStr.equals("false")) {
			showHtml = false;
		}

		if (showHtml || "yes".equals(params.get("ischild"))) {
			String html = freeMarkerPaser.proessPageContent();
			if ("yes".equals(params.get("ischild"))) {
				this.putData("widget_" + params.get("widgetid"), html);
			}
			return html;
		} else
			return "";
	}

	/**
	 * 子类需要实现在挂件处理方法<br/>
	 * 一般子类在此方法中处理挂件的业务逻辑，设置页面变量。
	 * 
	 * @param params
	 *            挂件参数
	 * @return
	 */
	abstract protected void display(Map<String, String> params);

	/**
	 * 设置挂件模板的变量
	 * 
	 * @param key
	 * @param value
	 */
	protected void putData(String key, Object value) {
		this.freeMarkerPaser.putData(key, value);
	}

	/**
	 * 设置挂件模板的变量
	 * 
	 * @param key
	 * @param value
	 */
	protected void putData(Map<String, Object> map) {
		this.freeMarkerPaser.putData(map);
	}

	protected Object getData(String key) {
		return this.freeMarkerPaser.getData(key);
	}

	/**
	 * 设置模板文件的名称 如果用户强制指定了挂件页面文件名，则使自定义页面
	 * 
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 设置操作后的提示信息
	 * 
	 * @param msg
	 *            要设置的信息
	 */
	protected void setMsg(String msg) {
		this.putData("msg", msg);
	}

	protected void showJson(String json) {
		this.freeMarkerPaser.setPageFolder("/commons/");
		this.freeMarkerPaser.setPageName("json");
		this.putData("json", json);
	}

}