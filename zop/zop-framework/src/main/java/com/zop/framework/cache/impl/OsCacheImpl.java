package com.zop.framework.cache.impl;

import com.opensymphony.oscache.general.*;
import com.opensymphony.oscache.base.*;
import com.zop.framework.cache.ICache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;


public class OsCacheImpl implements ICache {

	private static final Log logger = LogFactory.getLog(OsCacheImpl.class);

	private GeneralCacheAdministrator admin;

	public OsCacheImpl() {
		admin = new GeneralCacheAdministrator();
	}

	public OsCacheImpl(String profile) {
		Properties properties = new Properties();
		ClassPathResource classPathResource = new ClassPathResource(profile);
		try {
			logger.info("Init Cache...");
			properties.load(classPathResource.getInputStream());
			admin = new GeneralCacheAdministrator(properties);
		} catch (Exception ex) {
			logger.error(ex);
			admin = new GeneralCacheAdministrator();
		}
	}

	/**
	 *
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 * @todo Implement this com.laoer.bbscs.service.Cache method
	 */
	public void add(Object key, Object value) {
		// logger.info("Add into cache [Key:" + key + " Value:" + value + "]");
		logger.debug("Add into cache [Key:" + key + "]");
		this.admin.putInCache(String.valueOf(key), value);
	}

	/**
	 *
	 * @param key
	 *            Object
	 * @return java.lang.Object
	 * @todo Implement this com.laoer.bbscs.service.Cache method
	 */
	public Object get(Object key) {
		try {
			// logger.info("Get from cache [Key:" + key + "]");
			logger.debug("Get from cache [Key:" + key + "]");
			return this.admin.getFromCache(String.valueOf(key));
		} catch (NeedsRefreshException ex) {
			// logger.info("Object not in cache, return null");
			logger.debug("Object not in cache, return null");
			this.admin.cancelUpdate(String.valueOf(key));
			return null;
		}
	}

	/**
	 *
	 * @param key
	 *            Object
	 * @todo Implement this com.laoer.bbscs.service.Cache method
	 */
	public void remove(Object key) {
		// logger.info("Remove from cache [Key:" + key + "]");
		logger.debug("Remove from cache [Key:" + key + "]");
		this.admin.flushEntry(key.toString());
	}

	/**
	 *
	 * @todo Implement this com.laoer.bbscs.service.Cache method
	 */
	public void removeAll() {
		// logger.info("Remove all");
		logger.debug("Remove all");
		this.admin.flushAll();
	}
}
