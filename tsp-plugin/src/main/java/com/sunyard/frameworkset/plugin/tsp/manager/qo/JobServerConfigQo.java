package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;


public class JobServerConfigQo extends PagingOrder {

    private String hostname;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
