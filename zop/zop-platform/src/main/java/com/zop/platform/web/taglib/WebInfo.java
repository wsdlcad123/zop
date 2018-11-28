package com.zop.platform.web.taglib;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.components.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.Writer;

public class WebInfo extends Component {

	private PageContext pageContext;

	private String type;

	public WebInfo(ValueStack stack, PageContext pageContext) {
		super(stack);
		this.pageContext = pageContext;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(this.pageContext
				.getServletContext());
		String contextPath = this.pageContext.getServletContext().getContextPath();
        String host = this.pageContext.getRequest().getServerName();
		StringBuffer sb = new StringBuffer();
		try {
            //ctx目录
            if (this.getType().equalsIgnoreCase("ctx")) {
                sb.append(contextPath + "/");
            }
            //host
            if (this.getType().equalsIgnoreCase("host")) {
                sb.append(host);
            }
            //bootstrap目录
            if (this.getType().equalsIgnoreCase("simpleboot")) {
                sb.append(contextPath + "/static/simpleboot/");
            }
			//js目录
			if (this.getType().equalsIgnoreCase("js")) {
				sb.append(contextPath + "/static/js/");
			}
			//css目录
			if (this.getType().equalsIgnoreCase("css")) {
				sb.append(contextPath + "/static/css/");
			}
			//images目录
			if (this.getType().equalsIgnoreCase("images")) {
				sb.append(contextPath + "/static/images/");
			}
			writer.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
