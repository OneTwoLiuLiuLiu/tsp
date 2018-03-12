package com.sunyard.frameworkset.plugin.tsp.manager.biz.dtf;

import com.sunyard.frameworkset.core.dto.GenericObjectTranslator;
import com.sunyard.frameworkset.core.dto.ObjectTranslator;
import com.sunyard.frameworkset.core.manager.AbstractObjectTransferFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanInstanceVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanVo;
import com.sunyard.frameworkset.util.converter.BeanConverter;
import com.sunyard.frameworkset.util.pages.PageList;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by mhy on 2017/1/12.
 */
public class PlanInstanceDtf extends AbstractObjectTransferFactory {

    @Override
    public ObjectTranslator<PlanInstance, PlanInstanceVo> createPo2voer() {
        return new GenericObjectTranslator<PlanInstance,PlanInstanceVo>(){
            @Override
            public PlanInstanceVo transfer ( PlanInstanceVo planInstanceVo, PlanInstance planInstance ) {
                planInstanceVo = super.transfer ( planInstanceVo, planInstance );
                PlanVo planVo = new PlanVo();
                planVo = BeanConverter.convert(planVo,planInstance.getPlan());
                planInstanceVo.setPlanVo(planVo);
                return planInstanceVo;
            }

            @Override
            public PageList< PlanInstanceVo > transfer ( Class< PlanInstanceVo > clazz, List< PlanInstance > planInstances ) {
                PageList<PlanInstanceVo> planInstanceVoPageList = new PageList<> ();

                if (planInstances instanceof PageList) {
                    planInstanceVoPageList.setPageTools ( (( PageList<PlanInstance> ) planInstances).getPageTools () );
                }

                for (PlanInstance planInstance : planInstances) {
                    PlanInstanceVo planInstanceVo = new PlanInstanceVo ();
                    transfer ( planInstanceVo, planInstance );
                    planInstanceVoPageList.add ( planInstanceVo );
                }
                return planInstanceVoPageList;
            }
        };
    }

    @Override
    public  ObjectTranslator<PlanInstance, PlanInstanceVo> createVo2Poer() {
        return null;
    }
}
