package com.xxx.demo.quickStart3Topic;

import com.rabbitmq.client.Channel;
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

    /**
     * # 匹配一个词或多个词
     * * 匹配一个词
     */
    @Test
    public void doTest() throws IOException {
        Channel channel = channels.getChannelList().get(0);
        String exchangeName = "test_topic_exchange";
        String msg = "hello world topic";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        IntStream.range(0, 5).forEach(i -> {
            try {
                channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
                channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
                channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
