package com.zop.rewrite.processor.impl;

import com.zop.rewrite.core.Response;
import com.zop.rewrite.core.StringResponse;
import com.zop.rewrite.parser.IPageParser;
import com.zop.rewrite.processor.Processor;
import com.zop.rewrite.wrapper.HeaderResourcePageWrapper;
import com.zop.rewrite.wrapper.SafeHttpRequestWrapper;
import com.zop.utils.context.SpringContextHolder;
import com.zop.webutils.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面处理器
 * 
 * @author kingapex
 * @version 1.0
 * @created 22-十月-2009 18:12:29
 */
public class DefaultPageProcessor implements Processor {
	/**
	 * 
	 * 响应页面的三种操作,通过参数_method来识别，并分别通过三个接口来完成操作：
	 * <li>GET:解析页面： {@link com.zop.rewrite.parser.IPageParser}</li>
	 * </br>
	 * 页面的url会被读取并做为解析实际页面文件地址的依据
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response process( HttpServletResponse httpResponse,	HttpServletRequest httpRequest) {
		httpRequest = new SafeHttpRequestWrapper(httpRequest);
		String method = RequestUtil.getRequestMethod(httpRequest);
		String uri = RequestUtil.getRequestUrl(httpRequest);
		Response response = new StringResponse();

		// 解析页面
		if (method.equals("GET")) {
            //new DefaultPageParser();
			IPageParser parser = SpringContextHolder.getBean("defaultPageParser");
            parser = new HeaderResourcePageWrapper(parser);// 头部资源包装器
			String content = parser.parse(uri);
			response.setContent(content);
		}

        // 获取参数json
        if (method.equals("PARAMJSON")) {
        }
		return response;
	}

}