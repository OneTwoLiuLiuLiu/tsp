package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/13 0013 上午 9:11
 * version $Id: JobMonitorEnum.java, v 0.1 Exp $
 */
public enum  JobMonitorEnum implements EnumAware {
    RUNNING("0","正在运行"),
    COMPLE("1","完成"),
    OUTOFTIME("2","超时"),
    RUNERRER("3","运行失败"),
    PAUSE("4","暂停"),
    STOP("5","停止")
    ;

    JobMonitorEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;
    @Override
    public String getCode () {
        return this.code;
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public String getSimpleName () {
        return this.name;
    }
}
