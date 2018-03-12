package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/2/14 0014 下午 3:27
 * version $Id: StartWayEnum.java, v 0.1 Exp $
 */
public enum StartWayEnum implements EnumAware {
    NewTaskCancel ("1", "新任务取消"),
    NewTaskWait("2","新任务等待"),
    NewTaskExcute("3","新任务执行");
    private String code;
    private String name;
    StartWayEnum (String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSimpleName() {
        return this.name;
    }
}
