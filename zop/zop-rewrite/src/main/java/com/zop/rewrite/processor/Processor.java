package com.zop.rewrite.processor;

import com.zop.rewrite.core.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kingapex
 * @version 1.0
 * @created 13-十月-2009 11:25:48
 */
public interface Processor {

	/**
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response process(HttpServletResponse httpResponse, HttpServletRequest httpRequest);

}