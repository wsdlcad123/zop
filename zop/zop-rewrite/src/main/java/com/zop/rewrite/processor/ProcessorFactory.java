package com.zop.rewrite.processor;

import com.zop.rewrite.processor.impl.DefaultPageProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kingapex
 * @version 1.0
 * @created 13-十月-2009 11:36:29
 */
public abstract class ProcessorFactory {

	public static Processor newProcessorInstance(String uri, HttpServletRequest httpRequest) {
        //可以根据url判断加载哪个处理器
		Processor processor = new DefaultPageProcessor();
		return processor;
	}
}