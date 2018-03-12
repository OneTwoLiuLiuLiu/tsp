package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanMonitorEnum;
import com.sunyard.frameworkset.util.pages.PagingOrder;

/**
 * 计划监控 QO
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
   */
public class PlanMonitorQo extends PagingOrder {


    private String planInstId;

    private String pname;

    private String batchNo;

    private String startBatchNo;

    private String endBatchNo;

    public String getStartBatchNo() {
        return startBatchNo;
    }

    public void setStartBatchNo(String startBatchNo) {
        this.startBatchNo = startBatchNo;
    }

    public String getEndBatchNo() {
        return endBatchNo;
    }

    public void setEndBatchNo(String endBatchNo) {
        this.endBatchNo = endBatchNo;
    }

    private String id;

    private String endUser;

    private Integer jobaccount;

    private Integer surplus;

    private Integer runningaccount;

    private Integer doneaccount;

    private Integer failureaccount;

    private String status;

    private String endTime;
    private String endTimeBelow;
    private String endTimeTop;

    private String startTime;
    private String startTimeBelow;
    private String startTimeTop;

    private String statusChange;


    public String getStatusChange() {
        PlanMonitorEnum[] planMonitorEnms = PlanMonitorEnum.values ();
        for (PlanMonitorEnum planMonitorEnm : planMonitorEnms) {
            if (planMonitorEnm.getCode ().equals (status)) {
                return planMonitorEnm.getName ();
            }
        }
        return statusChange;
    }

    public void setStatusChange(String statusChange) {
        this.statusChange = statusChange;
    }

    public String getEndTimeBelow() {
        return endTimeBelow;
    }

    public void setEndTimeBelow(String endTimeBelow) {
        this.endTimeBelow = endTimeBelow;
    }

    public String getEndTimeTop() {
        return endTimeTop;
    }

    public void setEndTimeTop(String endTimeTop) {
        this.endTimeTop = endTimeTop;
    }

    public String getStartTimeBelow() {
        return startTimeBelow;
    }

    public void setStartTimeBelow(String startTimeBelow) {
        this.startTimeBelow = startTimeBelow;
    }

    public String getStartTimeTop() {
        return startTimeTop;
    }

    public void setStartTimeTop(String startTimeTop) {
        this.startTimeTop = startTimeTop;
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
