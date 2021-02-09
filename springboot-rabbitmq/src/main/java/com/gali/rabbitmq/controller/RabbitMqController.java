package com.gali.rabbitmq.controller;

import com.gali.rabbitmq.listener.event.PushOrderRecordEvent;
import com.gali.rabbitmq.response.BaseResponse;
import com.gali.rabbitmq.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;

/**
 * @author lijiali
 * @ClassName: RabbitMqController
 * @ProjectName rabbitmq
 * @date 2021/2/8 0008下午 9:55
 */
@RestController
@RequestMapping("/mq")
public class RabbitMqController {
    private static final Logger log= LoggerFactory.getLogger(RabbitMqController.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;
    /**
     * 下单
     * @return
     */
    @RequestMapping(value = "/pushMsg",method = RequestMethod.GET)
    public BaseResponse pushMsgr(String message){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        log.info("发送消息内容为:",message);
        try {

            rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));
            Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8)).build();
            rabbitTemplate.send(msg);
        log.info("消息发送成功内容为:",msg);
        }catch (Exception e){
            log.error("消息发生异常：",e.fillInStackTrace());
        }

        return response;
    }
}
