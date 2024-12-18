package com.hckj.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hckj.business.domain.Demo;
import com.hckj.business.domain.DemoExample;
import com.hckj.business.exception.BusinessException;
import com.hckj.business.exception.BusinessExceptionEnum;
import com.hckj.business.mapper.DemoMapper;
import com.hckj.business.req.DemoQueryReq;
import com.hckj.business.resp.QueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    @Resource
    private DemoMapper demoMapper;

    public long count(){

        return demoMapper.countByExample(null);
    }
//    public List<Demo> query(DemoQueryReq demoQueryReq) {
//        DemoExample demoExample = new DemoExample();
//        demoExample.setOrderByClause("id asc");
//        DemoExample.Criteria criteria = demoExample.createCriteria();
//        String mobile=demoQueryReq.getMobile();
//        if (mobile!=null){
//            criteria.andMobileEqualTo(mobile);
//        }
//        List<Demo> li = demoMapper.selectByExample(demoExample);
//        return li;
//    }
    public List<QueryResp> query(DemoQueryReq req) {
        String mobile = req.getMobile();
        DemoExample demoExample = new DemoExample();
        demoExample.setOrderByClause("id asc");
        DemoExample.Criteria criteria = demoExample.createCriteria();
//        if (mobile!= null) {
//            criteria.andMobileEqualTo(mobile);
//        }
        if(StrUtil.isBlank(mobile)){
            throw new BusinessException(BusinessExceptionEnum.DEMO_MOBILE_NOT_NULL.getDesc());
        }
        criteria.andMobileEqualTo(mobile);

        List<Demo> list = demoMapper.selectByExample(demoExample);
        List<QueryResp> respList = BeanUtil.copyToList(list, QueryResp.class);//将查询结果 list（Demo 类型）转换为 QueryResp 类型的列表
        return respList;
    }

}
