package com.zop.rewrite.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zop.rewrite.context.FreeMakerContextIniter;
import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.rewrite.core.FreeMarkerPaser;
import com.zop.rewrite.core.Response;
import com.zop.rewrite.processor.Processor;
import com.zop.rewrite.processor.ProcessorFactory;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.ZopContextIniter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 独立版的filter
 * @author kingapex
 * @version 1.0
 * @created 12-十月-2009 10:30:23
 */
public class RewriteFilter implements Filter {

    private  final Log logger = LogFactory.getLog(getClass());

	public void init(FilterConfig config) {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        //访问地址
		String uri = httpRequest.getServletPath();
        logger.debug("访问地址:" + uri);
		try {
            if (isExceptionUri(uri)) {
                chain.doFilter(httpRequest, httpResponse);
                return;
            }
            //Zop环境初始化
			ZopContextIniter.init(httpRequest, httpResponse);
            //模版环境初始化
            FreeMakerContextIniter.init(httpRequest, httpResponse);
			
			Processor processor = ProcessorFactory.newProcessorInstance(uri, httpRequest);
            //不需要处理器处理的
			if (processor == null) {
				chain.doFilter(request, response);
			}
            Response zopResponse = processor.process(httpResponse, httpRequest);
            InputStream in = zopResponse.getInputStream();
            if (in != null) {
                byte[] inbytes = IOUtils.toByteArray(in);
                httpResponse.setContentType(zopResponse.getContentType());
                httpResponse.setCharacterEncoding("UTF-8");
                OutputStream output = httpResponse.getOutputStream();
                IOUtils.write(inbytes, output);
            } else {
                chain.doFilter(request, response);
            }
		} catch (RuntimeException exception) {
            exception.printStackTrace();
		} finally {
			ThreadContextHolder.remove();
			FreeMarkerPaser.remove();
		}
	}

	public void destroy() {

	}

    private boolean isExceptionUri(String uri){
        List<String> exceptionUriList = ZopRewriteSetting.exceptionUriList;
        for(String exceptionUri:exceptionUriList){
            if (uri.startsWith(exceptionUri)) {
                return true;
            }
        }
        return false;
    }
	
}