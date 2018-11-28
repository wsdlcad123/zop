package com.zop.framework.component;

import java.util.HashMap;
import java.util.Map;
/**
 * 挂件上下文
 * @author kingapex
 *2012-5-15上午7:33:06
 */
public class WidgetContext {

	 private static Map<String,Boolean> widgetState;

	static{
			widgetState=new HashMap<String, Boolean>();
	}
	
	
	
	
	public static void putWidgetState(String widgetId,Boolean state){
			widgetState.put(widgetId, state);
	}
	
	
	/**
	 * 返回挂件的状态
	 * @param widgetId
	 * @return 如挂件id未注册过返回假
	 */
	public static Boolean getWidgetState(String widgetId){
         Boolean state = widgetState.get(widgetId);
         if(state==null)return true;
         return state;
	}
}
