package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/13 0013 上午 9:09
 * version $Id: PlanMonitorEnum.java, v 0.1 Exp $
 */
public enum  PlanMonitorEnum implements EnumAware{
    PAUSE("0","暂停"),
    RUNNING("1","正在运行"),
    COMPLE("2","完成"),
    END("3","结束"),
    ERROR("4","运行失败"),
    WAITING("5","等待运行");
            ;

    PlanMonitorEnum(String code, String name){
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
