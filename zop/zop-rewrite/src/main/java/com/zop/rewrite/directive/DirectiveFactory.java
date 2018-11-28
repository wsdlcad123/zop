package com.zop.rewrite.directive;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.TemplateDirectiveModel;

/**
 * 指令工厂
 * @author kingapex
 *2012-3-24下午10:15:11
 */
public class DirectiveFactory {
	
	
	private DirectiveFactory(){}
	private static Map<String,TemplateDirectiveModel> directiveMap;
	
	public static Map<String,TemplateDirectiveModel> getCommonDirective(){
		
		if(directiveMap==null){
			directiveMap= new  HashMap<String, TemplateDirectiveModel>(9);
			/**
			 * 日期格式化指令
			 */
			TemplateDirectiveModel dateformate = new DateformateDirective();
			directiveMap.put("dateformat", dateformate);
			/**
			 * 脚本 声明指令
			 */
			TemplateDirectiveModel script = new ScriptDirectiveModel();
			directiveMap.put("script", script);
			/**
			 * 样式声明指令
			 */
			TemplateDirectiveModel css = new CssDirectiveModel();
			directiveMap.put("css", css);
			/**
			 * 图片声明指令
			 */
			TemplateDirectiveModel image = new ImageDirectiveModel();
			directiveMap.put("image", image);
			/**
			 * 图片url输出指令
			 */
			TemplateDirectiveModel imgurl= new ImageUrlDirectiveModel();
			directiveMap.put("imgurl", imgurl);
			/**
			 * 字串截取指令
			 */
			TemplateDirectiveModel substring = new  SubStringDirectiveModel();
			directiveMap.put("substring", substring);
		}
		return directiveMap;
	}
	
}
