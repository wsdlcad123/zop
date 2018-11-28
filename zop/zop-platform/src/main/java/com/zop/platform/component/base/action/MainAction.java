package com.zop.platform.component.base.action;

import com.zop.framework.action.BaseAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainAction extends BaseAction {
	private static final Log logger = LogFactory.getLog(MainAction.class);

	/**
	 * 初始页
	 */
	public String index(){
		return SUCCESS;
	}

}
