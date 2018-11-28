package com.zop.rewrite.context;

import com.zop.utils.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZopRewriteSetting {

    private static final Log logger = LogFactory.getLog(ZopRewriteSetting.class);
	//前台主题存储路径
	public static String THEMES_STORAGE_PATH = "/themes";

    //当前主题路径
    public static String CURRENT_THEMES_PATH = "/default";

    public static Map themeUri;

    public static List<String> exceptionUriList;

    /*
     * 从配置文件中读取相关配置<br/> 如果没有相关配置则使用默认
     */
    static {
        try {
            init();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public static void init() {
        initThemeUri();
        initExceptionUri();
    }

    /**
     * 初始化主题uri
     */
    private static void initThemeUri() {
        try {
            // 加载url xml 配置文档
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(FileUtil.getResourceAsStream("theme_uri.xml"));
            NodeList uriNodeList = document.getElementsByTagName("uris").item(0).getChildNodes();
            themeUri = new HashMap();
            for (int i = 0; i < uriNodeList.getLength(); i++) {
                Node node = uriNodeList.item(i);
                //去除不是节点的
                if(StringUtils.isNotBlank(StringUtils.trim(node.getTextContent()))){
                    Element uriEl = (Element) node;
                   String key = uriEl.getAttribute("regex");
                   String value = uriEl.getTextContent();
                   themeUri.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 初始化排除url
     */
    private static void initExceptionUri() {
        try {
            // 加载url xml 配置文档
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(FileUtil.getResourceAsStream("exception_uri.xml"));
            NodeList uriNodeList = document.getElementsByTagName("uris").item(0).getChildNodes();
            exceptionUriList = new ArrayList<String>();
            for (int i = 0; i < uriNodeList.getLength(); i++) {
                Node node = uriNodeList.item(i);
                //去除不是节点的
                if(StringUtils.isNotBlank(StringUtils.trim(node.getTextContent()))) {
                    exceptionUriList.add(node.getTextContent());
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}