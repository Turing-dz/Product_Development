package com.hckj.business.controller;

import com.hckj.business.req.DemoQueryReq;
import com.hckj.business.resp.CommonResp;
import com.hckj.business.resp.QueryResp;
import com.hckj.business.service.DemoService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Resource
    private DemoService demoService;
    @GetMapping("/hello")
    public CommonResp<String> hello(){

        return new CommonResp<>("hello word!!!!!!!!");
    }
    @GetMapping("/count")
    public CommonResp<Long> count(){
        return new CommonResp<>(demoService.count());
    }
    @GetMapping("/query")
    public CommonResp<List<QueryResp>> query(@Valid DemoQueryReq demoQueryReq){
        List<QueryResp> resp= demoService.query(demoQueryReq);
//        CommonResp<List<Demo>> listCommonResp = new CommonResp<>();
//        listCommonResp.setContent(resp);
//        return listCommonResp;
        return new CommonResp<>(resp);
    }

}
