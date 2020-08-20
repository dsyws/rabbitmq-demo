package com.xxx.demo.quickStart5Propertie;

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
        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct";
        String msg = "hello world direct";
        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", 111);
        headers.put("my2", "222");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("utf-8")
                .expiration("10000")
                .headers(headers)
                .build();

        IntStream.range(0, 5).forEach(i -> {
            try {
                channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
