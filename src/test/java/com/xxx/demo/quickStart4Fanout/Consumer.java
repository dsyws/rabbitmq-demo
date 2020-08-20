package com.xxx.demo.quickStart4Fanout;

import com.rabbitmq.client.Channel;
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

        String exchangeName = "test_fanout_exchange";
        String routingKey = "test_fanout";
        String queueName = "test_fanout_queue";
        String exchangeType="fanout";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);


        String queueName2 = "test_fanout_queue2";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName2, false, false, false, null);
        channel.queueBind(queueName2,exchangeName,routingKey);

        QueueingConsumer queueingConsumer2 = new QueueingConsumer(channel);
        channel.basicConsume(queueName2, true, queueingConsumer2);


        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());

            System.out.println(msg+"11111111111111");

            QueueingConsumer.Delivery delivery2 = queueingConsumer.nextDelivery();
            String msg2 = new String(delivery2.getBody());

            System.out.println(msg2+"22222222222222");
        }
    }
}
