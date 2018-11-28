package com.zop.rewrite.parser;

import java.util.Map;

/**
 * 挂件处理器接口
 * 
 * @author kingapex 2010-2-8下午03:50:22
 */
public interface IWidgetGetter {

	/**
	 * 根据挂件参数 出挂件内容 要进行不同的paser组合
	 * 
	 * @param params
	 * @return
	 */
	public String process(Map<String, String> params, String page);

}
