package com.xxx.demo.quickStart7Return;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ReturnListener;
import com.xxx.demo.Channels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootTest
public class Producer {
    @Autowired
    private Channels channels;

    @Test
    public void doTest() throws IOException {
        Channel channel = channels.getChannelList().get(0);
        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct";
        String routingErrorKey = "test_direct_error";
        String msg = "hello world direct";

        channel.addReturnListener((replyCode, replyText, exchange, routingKey1, properties, body) ->
                System.out.println(replyCode + "->" + replyText + "->" + exchange + "->" + routingKey1 + "->" + new String(body)));
        IntStream.range(0, 5).forEach(i -> {
            try {
                //需要mandatory设置为true
                channel.basicPublish(exchangeName, routingErrorKey, true, null, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
