package com.zop.rewrite.directive;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zop.rewrite.context.ZopRewriteSetting;
import com.zop.rewrite.resource.Resource;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ZopWebUtilsSetting;
import com.zop.webutils.util.UploadUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 图片指令
 * @author kingapex
 *2012-3-25上午8:45:37
 */
public class ImageDirectiveModel extends AbstractResourceDirectiveModel  implements TemplateDirectiveModel{
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		 

		Resource resource = this.createResource(params);
		resource.setType("image");
		String src = params.get("src").toString();
		String postfix= this.getValue(params, "postfix");
		String imageurl = getImageUrl(src,postfix);
		StringBuffer html = new StringBuffer();
		html.append("<img");
		html.append(" src=\""+imageurl+"\"");
		html.append(" />");
		env.getOut().write(html.toString());
	}
	
	
	private static String getImageUrl(String pic,String postfix){
		if (StringUtil.isEmpty(pic)) {
            pic = ZopWebUtilsSetting.DEFAULT_IMG_URL;
        }
		if (pic.startsWith("fs:")) {//静态资源式分离式存储
			pic = UploadUtil.replacePath(pic);
		}
        if(!pic.toUpperCase().startsWith("HTTP")){
			//保存pic前有 '/'
			if(!pic.startsWith("/")){
				pic = "/" + pic;
			}
			pic =ZopWebUtilsSetting.CONTEXT_PATH + pic;
		}
		if (!StringUtil.isEmpty(postfix)) {
			return UploadUtil.getThumbPath(pic, postfix);
		} else {
			return pic;
		}
	}
	
	public static void main(String args[]){
		System.out.println(getImageUrl("statics/attachment/goods/201202222000570832.jpg","_thumbnail"));
	}
}
