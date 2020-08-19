package com.xxx.demo.quickStart1;

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
    public void getMessage() throws IOException, InterruptedException {
        String queueName = "test001";
        Channel channel = channels.getChannelList().get(1);
        channel.queueDeclare(queueName, true, false, false, null);
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














