package com.zop.platform.component.base.plugin.setting;

import com.zop.framework.plugin.Tab;

import java.util.Map;

/**
 * 设置页面显示事件
 * @author kingapex
 *2010-3-3上午11:02:04
 */
public interface ISettingOnInputEvent {
	
	/**
	 * 得到插件需要Tab
	 */
	public Tab getTab();
	

	/**
	 * 得到插件需要的数据
	 * @return
	 */
	public Map<String,Object> getData();
}
