package com.zop.rewrite.context;
import com.zop.rewrite.core.FreeMarkerPaser;
import com.zop.webutils.context.ZopWebUtilsSetting;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZhangBaochao on 14-7-27.
 */
public class FreeMakerContextIniter {
    public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        FreeMarkerPaser.set(new FreeMarkerPaser());
        FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();
        /**
         * 设置freemarker的相关常量
         */
        fmp.putData("ctx", httpRequest.getContextPath());
    }
}
