package com.zop.platform.component.base.plugin.setting;

import java.util.*;

import com.zop.framework.plugin.AutoRegisterPluginsBundle;
import com.zop.framework.plugin.IPlugin;
import com.zop.framework.plugin.Tab;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 系统设置插件桩
 *
 */
public class SettingPluginBundle extends AutoRegisterPluginsBundle {


    protected static final Log logger = LogFactory.getLog(SettingPluginBundle.class);

	
	public String getName() {
		return "系统设置插件桩";
	}



	
	public void registerPlugin(IPlugin plugin) {
		super.registerPlugin(plugin);
	}

	public List<Tab> getTabs(){
        List<Tab> tabs = new ArrayList<Tab>();
		List<IPlugin> plugins = this.getPlugins();
		
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if(plugin instanceof ISettingOnInputEvent){
					ISettingOnInputEvent event = (ISettingOnInputEvent)plugin;
                    tabs.add(event.getTab());
				}
			}
		}
        Collections.sort(tabs);
		return tabs;
	}

	
	
	
	public Map<String,Object> getDatas(){
		Map<String,Object> datas = new HashMap<String, Object>();
		List<IPlugin> plugins = this.getPlugins();
		
		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if(plugin instanceof ISettingOnInputEvent){
					ISettingOnInputEvent event = (ISettingOnInputEvent)plugin;
                    Map<String,Object> eventData=  event.getData();
                    datas.putAll(eventData);
				}
			}
		}
		return datas;
	}
	
}
