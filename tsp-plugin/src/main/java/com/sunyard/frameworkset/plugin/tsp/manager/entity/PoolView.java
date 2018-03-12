package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
* 作业池
* Author: Created by code generator
* Date: Thu Jan 05 10:51:07 CST 2017
*/
@Entity
@Table(name = "TSP_POOL_VIEW")
public class PoolView implements Serializable {

    /** serialVersionUID */
    private static final long         serialVersionUID = 7551940961396813158L;

    @Column( name = "STATUS")
    private String status;
    @Id
    @Column( name = "ID")
    private String id;

    @Column( name = "PNAME")
    private String pname;

    @Column( name = "JNAME")
    private String jname;

    @Column( name = "CREATE_USER")
    private String createUser;

    @Column( name = "CREATE_TIME")
    private String createTime;

    @Column( name = "START_USER")
    private String startUser;

    @Column( name = "START_TIME")
    private String startTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

}
