package com.zop.framework.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.zop.framework.component.ComponentView;
import com.zop.framework.component.IComponent;
import com.zop.framework.component.PluginView;
import com.zop.framework.component.WidgetView;
import com.zop.utils.util.FileUtil;
import com.zop.utils.util.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 组件上下文管理
 * 用于记录系统中所有的组件
 */
public class ComponentContext {

	private static List<ComponentView> componentList;
	static {
		componentList = new ArrayList<ComponentView>();
	}

	public static void registerComponent(ComponentView componentView) {
		try {
			loadComponent(componentView);
			componentList.add(componentView);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		}

	}

	public static List<ComponentView> getComponents() {
		return componentList;
	}

	/**
	 * 由component.xml中加载组件信息 此组件的名称及id 包含的挂件和插件
	 * 
	 * @param componentView
	 *            要加载的组件视图,会根据其component同包下的component.xml加载
	 * @throws org.xml.sax.SAXException
	 * @throws java.io.IOException
	 * @throws javax.xml.parsers.ParserConfigurationException
	 */
	private static void loadComponent(ComponentView componentView) throws SAXException, IOException, ParserConfigurationException {
		IComponent component = componentView.getComponent();

		String path = component.getClass().getPackage().getName();
		path = path.replace('.', '/') + "/component.xml";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(FileUtil.getResourceAsStream(path));
		Element componentEl = (Element) doc.getFirstChild(); // component节点

		componentView.setName(componentEl.getAttribute("name"));
		componentView.setAuthor(componentEl.getAttribute("author"));
		componentView.setVersion(componentEl.getAttribute("version"));

		Element pluginsEl = XMLUtil.getChildByTagName(componentEl, "plugins");
		Element widgetsEl = XMLUtil.getChildByTagName(componentEl, "widgets"); // 挂件节点		 	

		if (pluginsEl != null) {
			/**
			 * 查找所有插件，并压入组件视图的插件列表
			 */
			NodeList pluginNodeList = pluginsEl.getElementsByTagName("plugin");// 插件列表

			if (pluginNodeList != null) {
				int length = pluginNodeList.getLength();
				for (int i = 0; i < length; i++) {
					Element pluginEl = (Element) pluginNodeList.item(i);
					String name = pluginEl.getAttribute("name");
					String pluginBeanid = pluginEl.getAttribute("id");

					PluginView pluginView = new PluginView();
					pluginView.setId(pluginBeanid);
					pluginView.setName(name);

					/**
					 * 加载此插件的桩
					 */
					NodeList bundleList = pluginEl.getElementsByTagName("bundle");
					if (bundleList != null) {
						int bundleLength = bundleList.getLength();
						for (int j = 0; j < bundleLength; j++) {
							Element bundleEl = (Element) bundleList.item(j);
							String beanid = bundleEl.getAttribute("id");
							pluginView.addBundle(beanid);
						}
					}
					componentView.addPlugin(pluginView);
				}
			}
		}		 	
		 	
		if (widgetsEl != null) {
			/**
			 * 查找所有挂件，并压入组件视图的挂件列表
			 */
			NodeList widgetNodeList = widgetsEl.getElementsByTagName("widget");
			if (widgetNodeList != null) {
				int length = widgetNodeList.getLength();
				for (int i = 0; i < length; i++) {
					Element widgetEl = (Element) widgetNodeList.item(i);
					String name = widgetEl.getAttribute("name");
					String id = widgetEl.getAttribute("id");
					WidgetView widgetView = new WidgetView();
					widgetView.setName(name);
					widgetView.setId(id);
					componentView.addWidget(widgetView);

				}
			}
		}		 	    
	}
}