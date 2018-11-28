package com.zop.rewrite.parser.param;


import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.rewrite.parser.IWidgetParamParser;
import com.zop.rewrite.util.WidgetXmlUtil;
import com.zop.webutils.context.ZopWebUtilsSetting;

import java.util.Map;

/**
 *  SAAS式的xml挂件参数解析器
 * @author kingapex
 * 2010-2-4下午04:05:18
 */
public class XmlWidgetParamParser implements IWidgetParamParser {
	
	public Map<String, Map<String, Map<String,String>>> parse() {
		String path =
                ZopWebUtilsSetting.ZOP_PATH
		+ ZopRewriteSetting.THEMES_STORAGE_PATH
        + ZopRewriteSetting.CURRENT_THEMES_PATH
		+ "/widgets.xml";
		
		return WidgetXmlUtil.parse(path);
	}
}
