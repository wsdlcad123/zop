package com.zop.rewrite.resource;

public class Resource {
	
	private String src;
	private String type;//css\javascript\image
	private boolean common; //是否是公用的

    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isCommon() {
        return common;
    }
    public void setCommon(boolean common) {
        this.common = common;
    }
}
