package com.zop.rewrite.parser.widget;

import com.zop.rewrite.core.RewriteException;
import com.zop.rewrite.core.UrlNotFoundException;
import com.zop.rewrite.parser.IWidgetParser;
import com.zop.rewrite.widget.IWidget;
import com.zop.utils.context.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 本地挂件解析器
 * 
 * @author kingapex 2010-2-8下午03:56:17
 */
public class LocalWidgetParser implements IWidgetParser {

    protected  final Log logger = LogFactory.getLog(getClass());

	public String parse(Map<String, String> params) {
		if (params == null) {
            throw new RewriteException("挂件参数不能为空");
        }

		String widgetType = params.get("type");
		if (widgetType == null)
			throw new RewriteException("挂件类型不能为空");
		// System.out.println("processor "+
		// widgetType+"["+params.get("widgetid")+"]");
		try {
			IWidget widget = SpringContextHolder.getBean(widgetType);

			String content;
			if (widget == null)
				content = ("widget[" + widgetType + "]not found");
			else {
				content = widget.process(params); // 解析挂件内容
			}
			return content;
		} catch (UrlNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return "widget[" + widgetType + "]pase error ";
		}
	}

}
