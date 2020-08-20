package com.xxx.demo.quickStart2Direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.xxx.demo.Channels;
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

        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct";
        String queueName = "test_direct_queue";
        String exchangeType="direct";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());

            Envelope envelope = delivery.getEnvelope();
            System.out.println(msg);
        }
    }
}
