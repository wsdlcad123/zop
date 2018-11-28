package com.zop.framework.component;

import java.util.List;

import com.zop.framework.plugin.AutoRegisterPlugin;
import com.zop.framework.plugin.IPluginBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class ComponentLoader implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {

        if(bean instanceof AutoRegisterPlugin){
            AutoRegisterPlugin plugin =  (AutoRegisterPlugin)bean;
            if(plugin.getBundleList()==null){
                ///System.out.println( plugin.getName() +  " bundlelist is null " );
            }else{
                //plugin.register();
                List<IPluginBundle> pluginBundelList = plugin.getBundleList();
                for( IPluginBundle bundle :pluginBundelList){
                    bundle.registerPlugin(plugin);
                    //System.out.println(plugin.getName() + "注册在" + bundle.getClass().getName()+"桩中" );
                }
            }
        }

        //注册组件
		if(bean instanceof IComponent){
		 
			IComponent component = (IComponent)bean;
			ComponentView componentView = new ComponentView();
			componentView.setComponent(component);
			componentView.setId(beanName);
			ComponentContext.registerComponent(componentView);
		}
		
		return bean;
	}

}
