package com.zop.rewrite.resource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.zop.rewrite.context.HeaderConstants;
import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.utils.util.FileUtil;
import com.zop.webutils.context.ThreadContextHolder;
import com.zop.webutils.context.ZopWebUtilsSetting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 资源构建器
 * @author kingapex
 *2012-3-16上午12:00:56
 */
public class ResourceBuilder {
    protected  final Log logger = LogFactory.getLog(getClass());

    /**
     * 获取未合并的资源声名
     * @param type
     * @return
     */
    public static String getResource(){
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        List<Resource> scriptList  = (List<Resource>)request.getAttribute(HeaderConstants.scriptList);
        StringBuffer sb  = new StringBuffer();
        if(scriptList!=null){
            for(Resource script:scriptList){
                String src  = script.getSrc();
                if(!src.startsWith("/")){
                    src="/"+src;
                }
                String path = ZopWebUtilsSetting.CONTEXT_PATH + ZopRewriteSetting.THEMES_STORAGE_PATH + ZopRewriteSetting.CURRENT_THEMES_PATH + src;
                sb.append("<script src=\""+path+"\"  type=\"text/javascript\"></script>\n");
            }
        }

        scriptList  = (List<Resource>)request.getAttribute(HeaderConstants.cssList);
        if(scriptList!=null){
            for(Resource script:scriptList){
                String src  = script.getSrc();
                if(!src.startsWith("/")){
                    src="/"+src;
                }
                String path = ZopWebUtilsSetting.CONTEXT_PATH + ZopRewriteSetting.THEMES_STORAGE_PATH + ZopRewriteSetting.CURRENT_THEMES_PATH + src;
                sb.append("<link  href=\""+path+"\"  rel=\"stylesheet\" type=\"text/css\" />\n");
            }
        }

        return sb.toString();
    }

    /**
     * 获取某页是否有css
     * @return
     */
    public static boolean haveCss(){
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        List<Resource> cssList  = (List<Resource>)request.getAttribute(HeaderConstants.cssList);
        return !(cssList==null||cssList.isEmpty());
    }

	/**
	 * 获取某页是否有js
	 * @return
	 */
	public static boolean haveScript(){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		List<Resource> scriptList  = (List<Resource>)request.getAttribute(HeaderConstants.scriptList);
		return !(scriptList==null||scriptList.isEmpty());
	}
	
	/**
	 * 获取是否有公用脚本
	 * @return
	 */
	public static boolean haveCommonScript(){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		List<Resource> scriptList  = (List<Resource>)request.getAttribute(HeaderConstants.scriptCommonList);
		return !(scriptList==null||scriptList.isEmpty());
	}
	
	/**
	 * 获取是否有公用 css
	 * @return
	 */
	public static boolean haveCommonCss(){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		List<Resource> cssList  = (List<Resource>)request.getAttribute(HeaderConstants.cssCommonList);
		return !(cssList==null||cssList.isEmpty());
	}
	

	
	public static void putScript(Resource resource){
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		if(resource.isCommon()){
			List<Resource> scriptList = (List<Resource>) request.getAttribute(HeaderConstants.scriptCommonList);
			scriptList= scriptList==null? new ArrayList<Resource> ():scriptList;
			scriptList.add(resource);		
			request.setAttribute(HeaderConstants.scriptCommonList,scriptList);
		}else{
			List<Resource> scriptList =  (List<Resource>)request.getAttribute(HeaderConstants.scriptList);
			scriptList= scriptList==null? new ArrayList<Resource> ():scriptList;
			scriptList.add(resource);		
			request.setAttribute(HeaderConstants.scriptList,scriptList);				
		}
	}
	
	public  static void putCss(Resource resource){
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		if(resource.isCommon()){
			List<Resource> cssList =  (List<Resource>)request.getAttribute(HeaderConstants.cssCommonList);
			cssList= cssList==null? new ArrayList<Resource> ():cssList;
			cssList.add(resource);		
			request.setAttribute(HeaderConstants.cssCommonList,cssList);
		}else{
			List<Resource> cssList =  (List<Resource>)request.getAttribute(HeaderConstants.cssList);
			cssList= cssList==null? new ArrayList<Resource> ():cssList;
			cssList.add(resource);		
			request.setAttribute(HeaderConstants.cssList,cssList);
		}
	}
	public static void main(String[] args) throws IOException{
		
	}
}
