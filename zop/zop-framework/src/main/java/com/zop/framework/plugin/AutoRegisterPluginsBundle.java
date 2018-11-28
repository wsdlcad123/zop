package com.zop.framework.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动注册插件桩
 * 
 * @author apexking
 * 
 */
public abstract class AutoRegisterPluginsBundle implements IPluginBundle {
	protected static final Log loger = LogFactory.getLog(AutoRegisterPluginsBundle.class);

	private List<IPlugin> plugins;

	public synchronized void registerPlugin(IPlugin plugin) {
		if (plugins == null) {
			plugins = new ArrayList<IPlugin>();
		}

		if (!plugins.contains(plugin)) {
			plugins.add(plugin);
		}

		if (loger.isDebugEnabled()) {
			loger.debug("为插件桩" + getName() + "注册插件：" + plugin.getClass());
		}
	}

	@Override
	public synchronized void unRegisterPlugin(IPlugin plugin) {
		if (plugins != null) {
			plugins.remove(plugin);
		}
	}

	/**
	 * 获取此插件列表
	 * 
	 * @return
	 */
	public synchronized List<IPlugin> getPlugins() {
			return plugins;
	}
	abstract public String getName();

}
