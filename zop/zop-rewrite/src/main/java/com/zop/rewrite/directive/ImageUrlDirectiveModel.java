package com.zop.rewrite.directive;

import java.io.IOException;
import java.util.Map;
import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ZopWebUtilsSetting;
import com.zop.webutils.util.UploadUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 图片实际URL输出指令
 * @author kingapex
 *2012-2-11上午9:51:22
 */
public class ImageUrlDirectiveModel extends AbstractResourceDirectiveModel implements TemplateDirectiveModel {

	
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String pic = params.get("pic").toString();
		String postfix =null;
		if (params.get("postfix") != null) {
			 postfix = params.get("postfix").toString();
		}	
		pic= this.getImageUrl(pic, postfix);
		env.getOut().write(pic);

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
}
