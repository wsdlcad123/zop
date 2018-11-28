package com.zop.rewrite.widget;

import java.util.Map;

/**
 * 挂件接口
 * @author kingapex
 * 2010-1-29上午10:09:22
 */
public interface IWidget {
	
	/**
	 * 解析挂件并返回解析后的html片段
	 * @param params 挂件参数Map
	 * @return 解析后的html片段
	 */
	public  String process(Map<String, String> params);
}
