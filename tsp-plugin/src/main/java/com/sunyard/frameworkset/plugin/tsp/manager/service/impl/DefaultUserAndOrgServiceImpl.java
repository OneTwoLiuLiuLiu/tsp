package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

import com.sunyard.frameworkset.plugin.tsp.spi.user.service.OrgVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import org.springframework.stereotype.Service;


@Service
public class DefaultUserAndOrgServiceImpl implements UserAndOrgService {

    public UserVo getCurrentUser() {
        UserVo uv = new UserVo();
        uv.setCode("whj");
        uv.setName("王海江");
        uv.setMail("haij.wang@sunyard.com");
        uv.setPhone("15158001673");
        return uv;
    }

    public UserVo findByCode(String userCode) {
        UserVo uv = new UserVo();
        if("whj".equals(userCode)){
            uv.setCode("whj");
            uv.setName("王海江");
            uv.setMail("haij.wang@sunyard.com");
            uv.setPhone("15158001673");
        }
        if("wxw".equals(userCode)){
            uv.setCode("wxw");
            uv.setName("王贤旺");
            uv.setMail("haij.wang@sunyard.com");
            uv.setPhone("15158001673");
        }
        if("xt".equals(userCode)){
            uv.setCode("xt");
            uv.setName("徐通");
            uv.setMail("haij.wang@sunyard.com");
            uv.setPhone("15158001673");
        }
        if("zh".equals(userCode)){
            uv.setCode("zh");
            uv.setName("章辉");
            uv.setMail("hui.zhang@sunyard.com");
            uv.setPhone("15868536376");
        }
        if("mhy".equals(userCode)){
            uv.setCode("mhy");
            uv.setName("莫含赟");
            uv.setMail("hany.mo@sunyard.com");
            uv.setPhone("15868536376");
        }
        if("dh".equals(userCode)){
            uv.setCode("dh");
            uv.setName("丁昊");
            uv.setMail("hao.ding@sunyard.com");
            uv.setPhone("15158001673");
        }
        if("lj".equals(userCode)){
            uv.setCode("lj");
            uv.setName("罗京");
            uv.setMail("luo.jing@sunyard.com");
            uv.setPhone("15158001673");
        }
        if("jl".equals(userCode)){
            uv.setCode("jl");
            uv.setName("纪龙");
            uv.setMail("long.ji@sunyard.com");
            uv.setPhone("15158001673");
        }
        return uv;
    }

    public List<OrgVo> getSubOrgs(String orgCode) {
        List<OrgVo> list = new ArrayList<OrgVo>();
        if(orgCode ==null){
            OrgVo orgvo = new OrgVo();
            orgvo.setCode("xyd");
            orgvo.setName("信雅达数码科技");
            list.add(orgvo);
        }else if("xyd".equals(orgCode)){
            OrgVo orgvo1 = new OrgVo();
            orgvo1.setCode("yfb");
            orgvo1.setName("研发部");
            list.add(orgvo1);
            OrgVo orgvo2 = new OrgVo();
            orgvo2.setCode("dsj");
            orgvo2.setName("大数据应用部");
            list.add(orgvo2);
        }
        return list;
    }

    public List<UserVo> getUserByOrg(String orgCode) {
        List<UserVo> list = new ArrayList<UserVo>();
        if("yfb".equals(orgCode)){
            UserVo u0 = new UserVo();
            u0.setCode("zh");
            u0.setName("章辉");
            u0.setMail("hui.zhang@sunyard.com");
            u0.setPhone("15868536376");
            list.add(u0);
            UserVo u1 = new UserVo();
            u1.setCode("mhy");
            u1.setName("莫含赟");
            u1.setMail("hany.mo@sunyard.com");
            u1.setPhone("15868536376");
            list.add(u1);
            UserVo u2 = new UserVo();
            u2.setCode("dh");
            u2.setName("丁昊");
            u2.setMail("hao.ding@sunyard.com");
            u2.setPhone("15158001673");
            list.add(u2);
            UserVo u3 = new UserVo();
            u3.setCode("lj");
            u3.setName("罗京");
            u3.setMail("luo.jing@sunyard.com");
            u3.setPhone("15158001673");
            list.add(u3);
            UserVo u4 = new UserVo();
            u4.setCode("jl");
            u4.setName("纪龙");
            u4.setMail("long.ji@sunyard.com");
            u4.setPhone("15158001673");
            list.add(u4);
        }else if("dsj".equals(orgCode)){
            UserVo u1 = new UserVo();
            u1.setCode("whj");
            u1.setName("王海江");
            u1.setMail("haij.wang@sunyard.com");
            u1.setPhone("15158001673");
            list.add(u1);
            UserVo u2 = new UserVo();
            u2.setCode("wxw");
            u2.setName("王贤旺");
            u2.setMail("haij.wang@sunyard.com");
            u2.setPhone("15158001673");
            list.add(u2);
            UserVo u3 = new UserVo();
            u3.setCode("xt");
            u3.setName("徐通");
            u3.setMail("haij.wang@sunyard.com");
            u3.setPhone("15158001673");
            list.add(u3);
        }
        return list;
    }

}
