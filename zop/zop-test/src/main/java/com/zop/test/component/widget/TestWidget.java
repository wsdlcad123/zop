package com.zop.test.component.widget;

import com.zop.rewrite.widget.AbstractWidget;
import com.zop.test.component.service.IUserService;
import com.zop.utils.dbsolution.DBSolutionFactory;
import com.zop.webutils.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 礼品卡支付挂件
 * @author kingapex
 *2010-4-12下午02:42:37
 */
public class TestWidget extends AbstractWidget {
    private IUserService userService;

	protected void display(Map<String, String> params) {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        int id = this.userService.getId();
        //this.putData("param",params.get("param"));
        this.putData("param",id);
        //DBSolutionFactory.dbImport("file:com/zop/test/component/test_install.xml", "zop_");
        //String[] s = {"user"};
        //String sql = DBSolutionFactory.dbExport(s,false,"zop_");
        //System.out.println(sql);
	}

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
