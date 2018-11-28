package com.zop.framework.component;

import com.zop.utils.database.NotDbField;
import com.zop.utils.database.PrimaryKeyField;

import java.util.ArrayList;
import java.util.List;

public class ComponentView implements Cloneable {

	public ComponentView() {
		pluginList = new ArrayList<PluginView>();
		widgetList = new ArrayList<WidgetView>();

	}

    private String id;              //beanID
	private String name;            //名称
	private String version;         //版本
	private String author;          //作者
    private int install_status;     //0未安装 1已安装
    private int enable_status;      // 0未启用 1已启用

	private IComponent component;
	private List<PluginView> pluginList;
	private List<WidgetView> widgetList;

	public void addPlugin(PluginView plugin) {
		this.pluginList.add(plugin);
	}

	public void addWidget(WidgetView widget) {
		this.widgetList.add(widget);
	}

	public void setComponent(IComponent component) {
		this.component = component;
	}

	public IComponent getComponent() {
		return component;
	}

	public List<PluginView> getPluginList() {
		return pluginList;
	}

	public void setPluginList(List<PluginView> pluginList) {
		this.pluginList = pluginList;
	}

	public List<WidgetView> getWidgetList() {
		return widgetList;
	}

	public void setWidgetList(List<WidgetView> widgetList) {
		this.widgetList = widgetList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

    public int getInstall_status() {
        return install_status;
    }

    public void setInstall_status(int install_status) {
        this.install_status = install_status;
    }

    public int getEnable_status() {
        return enable_status;
    }

    public void setEnable_status(int enable_status) {
        this.enable_status = enable_status;
    }

    @Override
    public Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

    }

}
