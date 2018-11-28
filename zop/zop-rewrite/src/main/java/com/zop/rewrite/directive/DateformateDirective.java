package com.zop.rewrite.directive;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.zop.utils.util.DateUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DateformateDirective extends AbstractResourceDirectiveModel implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String timeStr = params.get("time").toString();
		String pattern = params.get("pattern").toString();
		long time = Long.valueOf(timeStr);
		if(time>0){
			Date date = new Date(time);
			String str  = DateUtil.toString(date, pattern);
			env.getOut().write(str);
		}else{
			env.getOut().write("");
		}
 

	}

}
