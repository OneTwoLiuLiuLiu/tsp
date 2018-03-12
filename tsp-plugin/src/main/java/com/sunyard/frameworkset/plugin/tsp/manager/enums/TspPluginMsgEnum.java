package com.sunyard.frameworkset.plugin.tsp.manager.enums;

/**
 * Created by zhanghui on 2016/8/20.
 */
public enum TspPluginMsgEnum {

    GENERIC_PLUGIN_ERROR("GENERIC_PLUGIN_ERROR", "插件异常"),

    UPDATE_ERROR("TSP_UPDATE_ERR", "更新异常"),

    INSERT_ERROR("TSP_INSERT_ERR", "插入异常"),

    QUERY_ERROR("TSP_QUERY_ERR", "查询异常"),

    DEPLOY_ERROR("TSP_DEPLOY_ERR","任务启动异常"),

    QUARRZ_ERROR("TSP_QUARTZ_ERROR","QUARTZ异常"),

    DELETE_ERROR ("TSP_DELETE_ERR", "删除异常"),

    PLAN_NO_JOB_ERROR ("TSP_PLAN_NO_JOB_ERR", "计划中没有作业"),

    JOB_NO_JOB_ERROR ("TSP_JOB_NO_JOB_ERR", "作业中没有子作业"),

    FILE_ERROR ("TSP_FILE_ERROR", "文件解析错误")
    ;


    private String code;

    private String name;

    TspPluginMsgEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return this.name;
    }
}
