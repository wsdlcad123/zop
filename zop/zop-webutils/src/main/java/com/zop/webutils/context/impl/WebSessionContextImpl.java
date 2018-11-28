package com.zop.webutils.context.impl;

import java.io.*;
import java.util.Hashtable;
import java.util.Set;
import javax.servlet.http.HttpSession;
import com.zop.webutils.context.WebSessionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangbaochao
 */
public class WebSessionContextImpl implements WebSessionContext, Externalizable {

    private final Log logger=LogFactory.getLog(getClass());

	private HttpSession session;

	private Hashtable attributes;

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		
		this.session = session;
		this.attributes = (Hashtable) this.session.getAttribute(sessionAttributeKey);
		if (attributes == null) {
			attributes = new Hashtable();
			this.onSaveSessionAttribute();
		}
	}


	public void invalidateSession() {
		this.session.invalidate();
	}

	private void onSaveSessionAttribute() {
		this.session.setAttribute(sessionAttributeKey, attributes);
	}

	public void setAttribute(String name, Object value) {
		if(attributes!=null){
		    attributes.put(name, value);
		    onSaveSessionAttribute();
		}
	}

	public Object getAttribute(String name) {
		if(attributes!=null) {
            return attributes.get(name);
        }else{
            return null;
        }
	}

	public Set getAttributeNames() {
		return attributes.keySet();
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
		onSaveSessionAttribute();
	}

	public void readExternal(ObjectInput input) throws IOException,
			ClassNotFoundException {
		attributes = (Hashtable) input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(attributes);
	}

	public void destory() {
		this.attributes = null;
		this.session = null;
	}
}
