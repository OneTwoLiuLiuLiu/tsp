package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;

import java.io.Serializable;

/**
* 计划监控
* Author: Created by code generator
* Date: Tue Jan 03 11:06:54 CST 2017
*/

public class PlanMonitorVo implements Serializable {

    /** serialVersionUID */
    private static final long         serialVersionUID = 6152390316262120897L;

    private String planInstId;


    private String pname;

    private String batchNo;

    private String id;

    private String endUser;

    private Integer jobaccount;

    private Integer surplus;

    private Integer runningaccount;

    private Integer doneaccount;

    private Integer failureaccount;

    private String status;

    private String endTime;

    private String startTime;

    private String costTime;

    public String getCostTime () {
        if(endTime==null){
            return null;
        }
        try {
            long st = DateUtil.strToDate (startTime, "yyyy-MM-dd HH:mm:ss")
                    .getTime ();
            long et = DateUtil.strToDate (endTime, "yyyy-MM-dd HH:mm:ss")
                    .getTime ();
            long seconds = (et - st) / 1000;
            costTime = "00:00:00";
            if (seconds > 0) {
                long hour = seconds / 3600;
                long minute = (seconds - hour * 3600) / 60;
                long second = (seconds - hour * 3600 - minute * 60);
                String h = hour / 10 > 0 ? "" + hour : "0" + hour;
                String m = minute / 10 > 0 ? "" + minute : "0" + minute;
                String ss = second / 10 > 0 ? "" + second : "0" + second;
                costTime = h + ":" + m + ":" + ss;
            }
        } catch (Exception e) {
            return null;
        }
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
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
        PlanMonitorEnum[] planMonitorEnms = PlanMonitorEnum.values ();
        for (PlanMonitorEnum planMonitorEnm : planMonitorEnms) {
            if (planMonitorEnm.getCode ().equals (status)) {
                return planMonitorEnm.getName ();
            }
        }
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
