package com.zop.rewrite.parser.page;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.zop.rewrite.context.HttpHeaderConstants;
import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.rewrite.core.FreeMarkerPaser;
import com.zop.rewrite.core.UrlNotFoundException;
import com.zop.rewrite.parser.IPageParser;
import com.zop.rewrite.parser.IWidgetGetter;
import com.zop.rewrite.parser.IWidgetParamParser;
import com.zop.rewrite.parser.getter.DefaultWidgetGetter;
import com.zop.rewrite.util.FreeMarkerUtil;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ThreadContextHolder;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 前台页面解析器
 * 
 * @author kingapex 2010-2-8下午10:45:20
 */
public class DefaultPageParser implements IPageParser {
	private IWidgetParamParser widgetParamParser;
	
	public synchronized String parse(String uri) {
		try {
			return doParse(uri);
		} catch (UrlNotFoundException e) {
			HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();
			httpResponse.setStatus(HttpHeaderConstants.status_404);
			return get404Html();
		}
	}
	
	public String get404Html() {
		// 要设置到页面中的变量值
		Map<String, Object> widgetData = new HashMap<String, Object>();
		String originalUri = "/404.html"; 

		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		request.setAttribute("pageid", "404"); // 设置模板的名称为pageid至上下文中，供其它包装器调用
		request.setAttribute("tplFileName", "404");// 设置模板名称至上下文		

		// 此站点挂件参数集合
		Map<String, Map<String, Map<String, String>>> pages = widgetParamParser.parse();
		IWidgetGetter htmlGetter = new DefaultWidgetGetter();
		// 处理公用挂件
		Map<String, Map<String, String>> commonWidgets = pages.get("common");
		if (commonWidgets != null) {
			Set<String> idSet = commonWidgets.keySet();
			for (String id : idSet) {
				Map<String, String> params = commonWidgets.get(id);
				String content = htmlGetter.process(params, originalUri);
				widgetData.put("widget_" + id, content);
			}
		}
		return parse(originalUri, widgetData);
	}
	
	public String doParse(String uri) {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        //原始URI
		String originalUri = uri;
		
		// 要设置到页面中的变量值
		Map<String, Object> widgetData = new HashMap<String, Object>();

		// 去掉uri问号以后的东西
		if (uri.indexOf('?') > 0) {
			uri = uri.substring(0, uri.indexOf('?'));
		}

	    // 得到模板文件名
        /**
         * 需要再次修改
         */
		String tplFileName = getPath(uri);
		String pageid = tplFileName.substring(1, tplFileName.indexOf("."));
		request.setAttribute("pageid", pageid); // 设置模板的名称为pageid至上下文中，供其它包装器调用
		request.setAttribute("tplFileName", pageid);// 设置模板名称至上下文

        FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();
		
		// 此站点挂件参数集合IWidgetParamParser
		Map<String, Map<String, Map<String, String>>> pages = this.widgetParamParser.parse();
		
		// 此页面的挂件参数集合
		Map<String, Map<String, String>> widgets = pages.get(tplFileName);

		IWidgetGetter widgetGetter = new DefaultWidgetGetter();

		String ajax = request.getParameter("ajax");
 		
		if (widgets != null) {   
			//如果指定执行某挂件，则直接返回此挂件内容
			String widgetid = request.getParameter("widgetid");
			if ("yes".equals(ajax) && !StringUtil.isEmpty(widgetid)) {
				Map<String, String> wgtParams = widgets.get(widgetid);
				String content = widgetGetter.process(wgtParams, originalUri);
				return content;
			}

			Set<String> idSet = widgets.keySet();
			
			for (String id : idSet) {
				/**
				 * 解析挂件的html
				 */
               //是否为通用html
                boolean isCurrUrl = matchUrl(uri, id);
				Map<String, String> params = widgets.get(id);
				String content = widgetGetter.process(params, originalUri);
                if (id.startsWith("/") && isCurrUrl) {
                    widgetData.put("widget_main", content);
                }else {
                    widgetData.put("widget_" + id, content);
                }
			}
		}
		
		if(!"yes".equals(ajax)){
			//处理公用挂件
			Map<String, Map<String, String>> commonWidgets = pages.get("common");
			if (commonWidgets != null) {
				Set<String> idSet = commonWidgets.keySet();
				for (String id : idSet) {
					Map<String, String> params = commonWidgets.get(id);
					String content = widgetGetter.process(params,originalUri);
					widgetData.put("widget_" + id, content);
				}
			}
		}
		return parse(tplFileName, widgetData);
	}

	public String parse(String tplFileName, Map<String, Object> widgetData) {
		try {
			// 站点使用模板
			String pageFolder = ZopRewriteSetting.THEMES_STORAGE_PATH +  ZopRewriteSetting.CURRENT_THEMES_PATH;
			Configuration cfg = FreeMarkerUtil.getCtxFolderCfg(pageFolder);
			Template temp = cfg.getTemplate(tplFileName);
			ByteOutputStream stream = new ByteOutputStream();

			Writer out = new OutputStreamWriter(stream);
			temp.process(widgetData, out);

			out.flush();
			String html = stream.toString();

			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return "page pase error";
		}
 
	}

	private boolean matchUrl(String uri, String targetUri) {
		Pattern p = Pattern.compile(targetUri, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(uri);
		return m.find();
	}

    private String getPath(String uri){
        Iterator<String> themeUriItor =  ZopRewriteSetting.themeUri.keySet().iterator();
        while(themeUriItor.hasNext()){
            String targetUri = themeUriItor.next();
            if(matchUrl(uri,targetUri)){
                return (String) ZopRewriteSetting.themeUri.get(targetUri);
            }
        }
        throw new UrlNotFoundException();
    }

	public void setWidgetParamParser(IWidgetParamParser widgetParamParser) {
		this.widgetParamParser = widgetParamParser;
	}
}