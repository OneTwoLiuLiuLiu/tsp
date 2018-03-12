package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

/**
 * 等待表 QO
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
   */
public class WaittingViewQo extends PagingOrder {


    private String id;

    private String pname;

    private String jname;

    private String startUser;

    private String startTime;

    private String createTime;

    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
