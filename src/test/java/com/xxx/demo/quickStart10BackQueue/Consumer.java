package com.xxx.demo.quickStart10BackQueue;

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

        String exchangeName = "test_back_exchange";
        String routingKey = "test_back";
        String queueName = "test_back_queue";
        String exchangeType="direct";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName, true, new MyConsumer(channel));

    }
}
