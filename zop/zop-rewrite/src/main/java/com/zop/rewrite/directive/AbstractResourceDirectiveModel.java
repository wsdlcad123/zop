package com.zop.rewrite.directive;

import com.zop.rewrite.resource.Resource;

import java.util.Map;


/**
 * 抽象的资源指令基类
 * 
 * @author kingapex 2012-3-25上午8:05:50
 */
public abstract class AbstractResourceDirectiveModel {

	protected Resource createResource( Map params)  {
		String src = params.get("src").toString();
		String common= this.getValue(params, "common");
		Resource resource = new Resource();
		resource.setSrc(src);
		resource.setCommon("true".equals(common));
		return resource;
	}
	
	protected String getValue(Map params, String name) {

		Object value_obj = params.get(name);
		if (value_obj == null) {
			return null;
		}

		return value_obj.toString();
	}
}
