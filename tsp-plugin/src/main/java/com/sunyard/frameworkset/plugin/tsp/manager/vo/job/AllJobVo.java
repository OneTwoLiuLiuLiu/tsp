package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.JobConfigVo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/1/9 9:39
 * version $Id: AllJobVo.java, v 0.1  9:39 Exp $
 */
public class AllJobVo extends JobConfigVo {

    private String jobType;

    private String dsProjectName;

    private String dsJobName;

    private String initialMemoryValue;

    private String maxMemoryValue;

    private String clientName;

    private String params;

    private String select;

    private String file;

    private String rep;

    private String user;

    private String pass;

    private String trans;

    private String jobs;

    private String dir;

    private String filePath;

    private String returnValues;

    private String procedureName;

    private String dataBaseType;

    private String dataBaseUser;

    private String dataBasePwd;

    private String dataBaseIp;

    private String dataBasePort;

    private String dataBaseName;

    private String httpUrl;

    private String callPlanConfigId;

    private String CallPlanConfigName;

    public String getIcon(){
        return "ui-icon-bat";
    }

    @Override
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDsProjectName() {
        return dsProjectName;
    }

    public void setDsProjectName(String dsProjectName) {
        this.dsProjectName = dsProjectName;
    }

    public String getDsJobName() {
        return dsJobName;
    }

    public void setDsJobName(String dsJobName) {
        this.dsJobName = dsJobName;
    }

    public String getInitialMemoryValue() {
        return initialMemoryValue;
    }

    public void setInitialMemoryValue(String initialMemoryValue) {
        this.initialMemoryValue = initialMemoryValue;
    }

    public String getMaxMemoryValue() {
        return maxMemoryValue;
    }

    public void setMaxMemoryValue(String maxMemoryValue) {
        this.maxMemoryValue = maxMemoryValue;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBasePort() {
        return dataBasePort;
    }

    public void setDataBasePort(String dataBasePort) {
        this.dataBasePort = dataBasePort;
    }

    public String getDataBaseIp() {
        return dataBaseIp;
    }

    public void setDataBaseIp(String dataBaseIp) {
        this.dataBaseIp = dataBaseIp;
    }

    public String getDataBasePwd() {
        return dataBasePwd;
    }

    public void setDataBasePwd(String dataBasePwd) {
        this.dataBasePwd = dataBasePwd;
    }

    public String getDataBaseUser() {
        return dataBaseUser;
    }

    public void setDataBaseUser(String dataBaseUser) {
        this.dataBaseUser = dataBaseUser;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getReturnValues() {
        return returnValues;
    }

    public void setReturnValues(String returnValues) {
        this.returnValues = returnValues;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getCallPlanConfigId () {
        return callPlanConfigId;
    }

    public void setCallPlanConfigId (String callPlanConfigId) {
        this.callPlanConfigId = callPlanConfigId;
    }

    public String getCallPlanConfigName () {
        return CallPlanConfigName;
    }

    public void setCallPlanConfigName (String callPlanConfigName) {
        CallPlanConfigName = callPlanConfigName;
    }

    @Override
    public List<Map<String, Object>> getSubInfoResult() throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Map<String, Object> map = new HashMap<String, Object>();
            JobDesc job = field.getAnnotation(JobDesc.class);
            if (job != null) {
                field.setAccessible(true);
                map.put("text", job.desc());
                map.put("value", field.get(this));
                result.add(map);
            }
        }
        return result;
    }
}
