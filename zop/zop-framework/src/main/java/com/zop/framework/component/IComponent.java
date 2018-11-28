package com.zop.framework.component;

/**
 * 组件接口
 */
public interface IComponent {
	
	/**
	 * 组件被安装时调用此方法
	 */
	public void install();
	
	
	/**
	 * 组件卸载时调用此方法 
	 */
	public void unInstall();
	
}
