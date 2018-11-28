package com.zop.webutils.context;

import java.util.Set;

import javax.servlet.http.HttpSession;

public interface WebSessionContext<T> {

	public static String sessionAttributeKey = "ZOPSessionKey";

	public  HttpSession getSession();

	public  void setSession(HttpSession session);

	public  void invalidateSession();

	public  void setAttribute(String name, T value);

	public  T getAttribute(String name);

	public  Set<T> getAttributeNames();

	public  void removeAttribute(String name);
	
	public  void destory();

}