package com.zop.webutils.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.zop.webutils.context.ZopWebUtilsSetting;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ZopContextLoaderListener extends HttpServlet implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(ZopContextLoaderListener.class);

	/*
	 *
	 *
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/*
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		String rootpath = sce.getServletContext().getRealPath("/");
		if (rootpath != null) {
			rootpath = rootpath.replaceAll("\\\\", "/");
		} else {
			rootpath = "/";
		}
		if (!rootpath.endsWith("/")) {
			rootpath = rootpath + "/";
		}
        ZopWebUtilsSetting.ZOP_PATH = rootpath;
		logger.info("Application Run Path:" + rootpath);
		String contextpath = sce.getServletContext().getContextPath();
        ZopWebUtilsSetting.CONTEXT_PATH= contextpath;
		logger.info("Application Context Path:" + contextpath);
	}

}
