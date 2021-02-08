package com.gali.rabbitmq.controller;

import com.gali.rabbitmq.listener.event.PushOrderRecordEvent;
import com.gali.rabbitmq.response.BaseResponse;
import com.gali.rabbitmq.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gali
 * @Description //
 * @Date 19:58 2021/2/8
 * @Param 
 * @return 
 **/
@RestController
public class OrderRecordController {

    private static final Logger log= LoggerFactory.getLogger(OrderRecordController.class);

    private static final String Prefix="order";

    //TODO：类似于RabbitTemplate
    @Autowired
    private ApplicationEventPublisher publisher;


    /**
     * 下单
     * @return
     */
    @RequestMapping(value = Prefix+"/push",method = RequestMethod.GET)
    public BaseResponse pushOrder(){
        BaseResponse response=new BaseResponse(StatusCode.Success);

        try {
            PushOrderRecordEvent event=new PushOrderRecordEvent(this,"orderNo_20180821001","物流12");
            publisher.publishEvent(event);
        }catch (Exception e){
            log.error("下单发生异常：",e.fillInStackTrace());
        }

        return response;
    }

}










