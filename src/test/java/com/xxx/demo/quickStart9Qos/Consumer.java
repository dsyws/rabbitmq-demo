package com.xxx.demo.quickStart9Qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.xxx.demo.Channels;
import com.xxx.demo.MyConsumer;
import com.xxx.demo.QueueingConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class Consumer {
    @Autowired
    private Channels channels;

    @Test
    public void doTest() throws IOException, InterruptedException {
        Channel channel = channels.getChannelList().get(1);

        String exchangeName = "test_qos_exchange";
        String routingKey = "test_qos";
        String queueName = "test_qos_queue";
        String exchangeType = "direct";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicQos(0, 1, false);
        //关闭自动签收机制
        channel.basicConsume(queueName, false, new MyConsumer(channel));

    }
}
