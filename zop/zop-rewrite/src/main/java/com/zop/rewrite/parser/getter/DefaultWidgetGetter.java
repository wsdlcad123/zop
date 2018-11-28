package com.zop.rewrite.parser.getter;

import com.zop.rewrite.parser.IWidgetGetter;
import com.zop.rewrite.parser.IWidgetParser;
import com.zop.rewrite.parser.widget.LocalWidgetParser;

import java.util.Map;

/**
 * 挂件Html获取器</br> 通过 IWidgetPaser接口 解析挂件html
 *
 * @author kingapex 2010-2-8下午10:17:58
 */
public class DefaultWidgetGetter implements IWidgetGetter {

	public String process(Map<String, String> params, String page) {
		IWidgetParser widgetPaser = new LocalWidgetParser();
		String html = widgetPaser.parse(params);
		return html;
	}

}
