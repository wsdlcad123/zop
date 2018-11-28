package com.zop.framework.action;

import com.zop.utils.util.StringUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.*;

public class AjaxMessagesJson {

	private static final Log logger = LogFactory.getLog(AjaxMessagesJson.class);

	private JSONObject json = new JSONObject();

	public void setMessage(String info,int status,String referer) {
		try {
            this.json.put("referer", StringUtil.isEmpty(referer)?"":referer);
            this.json.put("state", status==0?"fail":"success");//提示类型，success fail
            this.json.put("info", info);
            this.json.put("status", status);
            this.json.put("data", "");
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	public void setMessage( String info, int status, String referer, String data) {
		try {
			this.json.put("referer", StringUtil.isEmpty(referer)?"":referer);
			this.json.put("state", status==0?"fail":"success");
			this.json.put("info", info);
            this.json.put("status", status);
            this.json.put("data", data);
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	public String getJsonString() {
		return this.json.toString();
	}
}
