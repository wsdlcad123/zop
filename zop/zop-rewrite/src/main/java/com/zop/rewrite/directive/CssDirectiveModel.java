package com.zop.rewrite.directive;

import java.io.IOException;
import java.util.Map;

import com.zop.rewrite.resource.Resource;
import com.zop.rewrite.resource.ResourceBuilder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CssDirectiveModel extends AbstractResourceDirectiveModel  implements TemplateDirectiveModel{
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		 
		Resource resource = this.createResource(params);
		resource.setType("css");
		ResourceBuilder.putCss(resource);
		 
	}
	
}
