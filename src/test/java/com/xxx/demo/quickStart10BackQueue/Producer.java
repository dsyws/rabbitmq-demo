package com.xxx.demo.quickStart10BackQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.xxx.demo.Channels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootTest
public class Producer {
    @Autowired
    private Channels channels;

    @Test
    public void doTest() throws IOException {
        Channel channel = channels.getChannelList().get(0);
        String exchangeName = "test_back_exchange";
        String routingKey = "test_back";
        String msg = "hello world back";
        IntStream.range(0, 5).forEach(i -> {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("key", i);
                AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
                channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
