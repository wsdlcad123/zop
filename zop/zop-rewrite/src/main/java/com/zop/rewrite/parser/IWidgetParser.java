package com.zop.rewrite.parser;

import java.util.Map;

/**
 * 挂件解析器接口
 * 
 * @author kingapex 2010-2-8下午03:50:22
 */
public interface IWidgetParser {

	/**
	 * 根据挂件参数 出挂件内容
	 * 
	 * @param params
	 * @return
	 */
	public String parse(Map<String, String> params);

}
