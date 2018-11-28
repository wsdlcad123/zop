package com.zop.test.component.widget;

import com.zop.rewrite.widget.AbstractWidget;
import com.zop.webutils.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 礼品卡支付挂件
 * @author kingapex
 *2010-4-12下午02:42:37
 */
public class HeaderWidget extends AbstractWidget {
	protected void display(Map<String, String> params) {

		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		
	}
}
