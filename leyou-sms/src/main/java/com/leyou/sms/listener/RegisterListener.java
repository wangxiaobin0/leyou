package com.leyou.sms.listener;

import com.leyou.sms.service.ISmsService;
import com.leyou.user.api.domain.MessageBody;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

/**
 * @author
 * @date 2020/3/27
 */
@Component
public class RegisterListener {

    @Autowired
    ISmsService smsService;
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "leyou.user"), exchange = @Exchange(value = "leyou.user"), key = "leyou.user.register")
    })
    public void register(MessageBody msg) {
        smsService.sendShortMsgForRegister((String) msg.getData());
    }
}
