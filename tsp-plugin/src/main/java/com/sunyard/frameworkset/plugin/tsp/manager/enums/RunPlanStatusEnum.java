package com.sunyard.frameworkset.plugin.tsp.manager.enums;

/**
 * Created by mhy on 2017/1/17.
 */

import com.sunyard.frameworkset.util.enums.EnumAware;

public enum RunPlanStatusEnum implements EnumAware {
    STOP("0","停止"),
    RUN("1","运行"),
            ;

    RunPlanStatusEnum (String code, String name){
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
