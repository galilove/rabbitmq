package com.gali.rabbitmq.controller;


import com.gali.rabbitmq.mapper.OrderRecordMapper;
import com.gali.rabbitmq.response.BaseResponse;
import com.gali.rabbitmq.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gali
 * @Description // helloworld Controller
 * @Date 19:55 2021/2/8
 * @Param 
 * @return 
 **/
@RestController
public class HelloWorldController {

    private static final Logger log= LoggerFactory.getLogger(HelloWorldController.class);

    private static final String Prefix="helloWorld";

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    /**
     * 测试SpringBoot整合是否有问题-hello world
     * @return
     */
    @RequestMapping(value = Prefix+"/rabbitmq",method = RequestMethod.GET)
    public BaseResponse rabbitmq(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        String str="rabbitmq的第二阶段-spring boot整合rabbitmq";
        response.setData(str);
        return response;
    }

    /**
     * 整合mybatis访问数据列表
     * @return
     */
    @RequestMapping(value = Prefix+"/data/list",method = RequestMethod.GET)
    public BaseResponse dataList(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        response.setData(orderRecordMapper.selectAll());
        return response;
    }


}
