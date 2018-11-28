package com.zop.rewrite.wrapper;

import com.zop.rewrite.parser.IPageParser;
import com.zop.rewrite.resource.ResourceBuilder;

public class HeaderResourcePageWrapper extends PageWrapper {

	public HeaderResourcePageWrapper(IPageParser paser) {
		super(paser);
	}

	public String parse(String url) {
        String content = super.parse(url);
        StringBuffer headerhtml = new StringBuffer();
		headerhtml.append(ResourceBuilder.getResource());
		content = content.replaceAll("#headerscript#", headerhtml.toString());
		return content;
	}
}
