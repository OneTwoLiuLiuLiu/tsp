package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
* 计划监控
* Author: Created by code generator
* Date: Tue Jan 03 11:06:54 CST 2017
*/
@Entity
@Table(name = "tsp_plan_monitor")
public class PlanMonitor implements Serializable {

    /** serialVersionUID */
    private static final long         serialVersionUID = 6152390316262120897L;

    @Column( name = "PLAN_INST_ID")
    private String planInstId;

    @Column( name = "PNAME")
    private String pname;

    @Column( name = "BATCH_NO")
    private String batchNo;

    @Column(name="PLAN_CONFIG_ID")
    private String planConfigId;

    @Id
    @Column( name = "ID")
    private String id;

    @Column( name = "END_USER")
    private String endUser;

    @Column( name = "JOBACCOUNT")
    private Integer jobaccount;

    @Column( name = "SURPLUS")
    private Integer surplus;

    @Column( name = "RUNNINGACCOUNT")
    private Integer runningaccount;

    @Column( name = "DONEACCOUNT")
    private Integer doneaccount;

    @Column( name = "FAILUREACCOUNT")
    private Integer failureaccount;

    @Column( name = "STATUS")
    private String status;

    @Column( name = "END_TIME")
    private String endTime;

    @Column( name = "START_TIME")
    private String startTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPlanConfigId() {
        return planConfigId;
    }

    public void setPlanConfigId(String planConfigId) {
        this.planConfigId = planConfigId;
    }

    public String getPlanInstId() {
        return planInstId;
    }

    public void setPlanInstId(String planInstId) {
        this.planInstId = planInstId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public Integer getJobaccount() {
        return jobaccount;
    }

    public void setJobaccount(Integer jobaccount) {
        this.jobaccount = jobaccount;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Integer getRunningaccount() {
        return runningaccount;
    }

    public void setRunningaccount(Integer runningaccount) {
        this.runningaccount = runningaccount;
    }

    public Integer getDoneaccount() {
        return doneaccount;
    }

    public void setDoneaccount(Integer doneaccount) {
        this.doneaccount = doneaccount;
    }

    public Integer getFailureaccount() {
        return failureaccount;
    }

    public void setFailureaccount(Integer failureaccount) {
        this.failureaccount = failureaccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
