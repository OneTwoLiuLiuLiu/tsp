package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;

/**
 * 作业连接池qct转化类
 * Created by pc on 2018/3/5.
 */
public class JobConnectPoolConfigQct extends QueryConditionTransfer<JobConnectPoolConfigQo> {
    @Override
    public void transNameQuery(JobConnectPoolConfigQo qo, QueryCondition condition) {
        if(qo!=null){
            if(qo.getData_source_name()!=null && !"".equals(qo.getData_source_name().trim())){
               condition.add(" And obj.data_source_name like :data_source_name", "data_source_name", qo.getBlurKeyword(qo.getData_source_name()));
            }
            if(qo.getDb_name()!=null && !"".equals(qo.getDb_name().trim())){
                condition.add(" And obj.db_name=:db_name","db_name",qo.getDb_name());
            }
            if(qo.getDb_type()!=null && !"".equals(qo.getDb_type().trim())){
                condition.add(" And obj.db_type=:db_type","db_type",qo.getDb_type());
            }
            if(qo.getEnable_flag()!=null &&!"".equals(qo.getEnable_flag())){
                condition.add(" And obj.enable_flag=:enable_flag","enable_flag",qo.getEnable_flag());
            }
        }
    }
}
