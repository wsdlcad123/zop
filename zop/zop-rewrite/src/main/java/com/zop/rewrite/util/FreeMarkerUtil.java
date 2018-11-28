package com.zop.rewrite.util;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.zop.rewrite.directive.DirectiveFactory;
import com.zop.webutils.context.ThreadContextHolder;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * freemarker工具
 * 
 * @author kingapex 2010-2-8下午04:23:18
 */
public class FreeMarkerUtil {
	private FreeMarkerUtil() {
	}

	public static Configuration getCfg(){
	 
			Configuration  cfg = new Configuration();
			cfg.setTemplateUpdateDelay(6000);
			cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(java.util.Locale.CHINA);
			cfg.setEncoding(java.util.Locale.CHINA, "UTF-8");

            Map<String,TemplateDirectiveModel> directiveMap = DirectiveFactory.getCommonDirective();
            Iterator<String> keyIter= directiveMap.keySet().iterator();
            while(keyIter.hasNext()){
                String key = keyIter.next();
                cfg.setSharedVariable(key, directiveMap.get(key));
            }
		return cfg;
	}
	
	
	
	
	public static Configuration getCtxFolderCfg(String pageFolder)
			throws IOException {
		Configuration cfg =getCfg();
        cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), pageFolder);
		return cfg;

	}

    public static Configuration getFolderCfg(String pageFolder)
            throws IOException {
        Configuration cfg =getCfg();
        cfg.setDirectoryForTemplateLoading(new File(pageFolder));
        return cfg;

    }


	public static void main(String[] args) throws IOException,
			TemplateException {
		Configuration cfg = FreeMarkerUtil
				.getFolderCfg("E:/test");
		Template temp = cfg.getTemplate("GoodsCat.html");
		ByteOutputStream stream = new ByteOutputStream();

		Writer out = new OutputStreamWriter(stream, "UTF-8");
		temp.process(new HashMap(), out);

		out.flush();
		String html = stream.toString();
		System.out.println(html);
	}

}
