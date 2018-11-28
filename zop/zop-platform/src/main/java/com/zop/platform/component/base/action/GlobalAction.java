package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GlobalAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(GlobalAction.class);

	/**
	 * 初始页
	 */
	public String gv(){
		return "gv";
	}

}
