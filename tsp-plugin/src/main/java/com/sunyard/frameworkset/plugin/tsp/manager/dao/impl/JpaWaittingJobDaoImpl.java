package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.WaittingJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingViewVo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * 等待表 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Repository
public class JpaWaittingJobDaoImpl  extends JpaBaseDaoImpl<WaittingJob, String, WaittingViewQo> implements WaittingJobDao{

}
