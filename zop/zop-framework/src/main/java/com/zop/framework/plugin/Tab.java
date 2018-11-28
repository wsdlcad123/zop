package com.zop.framework.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Tab implements Comparable<Tab>{
    private String id;
    private String name;
    private String url;
    private Integer ordres;
    private String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrdres() {
        return ordres;
    }

    public void setOrdres(Integer ordres) {
        this.ordres = ordres;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public int compareTo(Tab tab) {
        return this.getOrdres().compareTo(tab.getOrdres());
    }
}
