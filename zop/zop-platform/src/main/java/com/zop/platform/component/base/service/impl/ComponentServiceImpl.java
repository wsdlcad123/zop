package com.zop.platform.component.base.service.impl;


import com.zop.framework.component.*;
import com.zop.framework.plugin.IPlugin;
import com.zop.framework.plugin.IPluginBundle;
import com.zop.platform.component.base.bean.Component;
import com.zop.platform.component.base.dao.IComponentDAO;
import com.zop.platform.component.base.service.IComponentService;
import com.zop.utils.context.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;

public class ComponentServiceImpl implements IComponentService {

    private IComponentDAO componentDAO;

    public void setComponentDAO(IComponentDAO componentDAO) {
        this.componentDAO = componentDAO;
    }

    public void start(){
        List<Component> dbList = this.componentDAO.list();
        for (Component component : dbList) {
            if (component.getEnable_status() == 1) {
                this.enable(component.getComponent_view_id());
            }
        }
    }

    public List list(){
        List<ComponentView> viewList = new ArrayList<ComponentView>();
        List<ComponentView> componentViews = ComponentContext.getComponents();// 加载所有声明的组件
        List<Component> dbList = this.componentDAO.list();
        if (componentViews != null) {
            for (ComponentView view : componentViews) {
                ComponentView componentView = (ComponentView) view.clone();
                componentView.setInstall_status(0);     //默认为未安装
                componentView.setEnable_status(0);      // 默认为未启用
                this.loadComponentState(componentView, dbList); // 由数据库加载组件信息

                viewList.add(componentView);
            }
        }
        return viewList;

    }

    /**
     * 用数据库的组件列表加载组件上下文中的组件状态
     * @param componentView
     * @param dbList
     */
    private void loadComponentState(ComponentView componentView, List<Component> dbList) {
        for (Component component : dbList) {
            if (component.getComponent_view_id().equals(componentView.getId())) {
                componentView.setInstall_status(component.getInstall_status());
                componentView.setEnable_status(component.getEnable_status());
            }
        }
    }

    public void install(String component_view_id){
        ComponentView componentView= this.getComponentView(component_view_id);
        if (componentView != null) {
            componentView.getComponent().install();
            Component c = this.componentDAO.getByViewId(component_view_id);
            if(c == null){
                // 数据库中还不存在，需要先插入一条
                c = viewToComponent(componentView);
                c.setInstall_status(1);
                this.componentDAO.save(c);
            }else{
                c.setInstall_status(1);
                this.componentDAO.update(c);
            }
        }
    }

    public void unInstall(String component_view_id){
        ComponentView componentView= this.getComponentView(component_view_id);
        if (componentView != null) {
            componentView.getComponent().unInstall();
            Component c = this.componentDAO.getByViewId(component_view_id);
            if(c == null){
                // 数据库中还不存在，需要先插入一条
                c = viewToComponent(componentView);
                c.setInstall_status(0);
                this.componentDAO.save(c);
            }else{
                c.setInstall_status(0);
                this.componentDAO.update(c);
            }
        }
    }

    public void enable(String  component_view_id){
        ComponentView componentView= this.getComponentView(component_view_id);
        /**
         * 启用相应插件
         */
        List<PluginView> pluginList = componentView.getPluginList();
        for (PluginView pluginView : pluginList) {
            String pluginid = pluginView.getId();
            IPlugin plugin = SpringContextHolder.getBean(pluginid);

            List<String> bundleList = pluginView.getBundleList();
            if (bundleList != null) {
                for (String bundleId : bundleList) {
                    IPluginBundle pluginBundle = SpringContextHolder.getBean(bundleId);
                    pluginBundle.registerPlugin(plugin);
                }
            }
        }

        /**
         * 启用相应挂件
         */
        List<WidgetView> widgetList = componentView.getWidgetList();
        for (WidgetView widgetView : widgetList) {
            String widgetid = widgetView.getId();
            WidgetContext.putWidgetState(widgetid, true);
        }
        /**
         * 更新数据库的状态
         */
        Component c = this.componentDAO.getByViewId(component_view_id);
        c.setEnable_status(1);
        this.componentDAO.update(c);

    }

    public void disable(String  component_view_id){
        ComponentView componentView= this.getComponentView(component_view_id);
        /**
         * 启用相应插件
         */
        List<PluginView> pluginList = componentView.getPluginList();
        for (PluginView pluginView : pluginList) {
            String pluginid = pluginView.getId();
            IPlugin plugin = SpringContextHolder.getBean(pluginid);

            List<String> bundleList = pluginView.getBundleList();
            if (bundleList != null) {
                for (String bundleId : bundleList) {
                    IPluginBundle pluginBundle = SpringContextHolder.getBean(bundleId);
                    pluginBundle.unRegisterPlugin(plugin);
                }
            }
        }

        /**
         * 启用相应挂件
         */
        List<WidgetView> widgetList = componentView.getWidgetList();
        for (WidgetView widgetView : widgetList) {
            String widgetid = widgetView.getId();
            WidgetContext.putWidgetState(widgetid, false);
        }
        /**
         * 更新数据库的状态
         */
        Component c = this.componentDAO.getByViewId(component_view_id);
        c.setEnable_status(0);
        this.componentDAO.update(c);
    }

    private ComponentView getComponentView(String component_view_id) {
        List<ComponentView> componentList = ComponentContext.getComponents();
        for (ComponentView componentView : componentList) {
            if (componentView.getId().equals(component_view_id)) {
                return componentView;
            }
        }
        return null;
    }

    private Component viewToComponent(ComponentView componentView){
        Component c = new Component();
        c.setComponent_view_id(componentView.getId());
        c.setName(componentView.getName());
        c.setVersion(componentView.getVersion());
        c.setAuthor(componentView.getAuthor());
        c.setInstall_status(0);
        c.setEnable_status(0);
        return c;
    }
}