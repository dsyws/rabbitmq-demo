package com.xxx.demo.quickStart9Qos;

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

    @Test
    public void doTest() throws IOException {
        Channel channel = channels.getChannelList().get(0);
        String exchangeName = "test_qos_exchange";
        String routingKey = "test_qos";
        String msg = "hello world qos";
        IntStream.range(0,5).forEach(i->{
            try {
                channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
