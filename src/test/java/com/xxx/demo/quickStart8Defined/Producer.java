package com.xxx.demo.quickStart8Defined;

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
        String exchangeName = "test_Defined_exchange";
        String routingKey = "test_Defined";
        String msg = "hello world Defined";
        IntStream.range(0,5).forEach(i->{
            try {
                channel.basicPublish(exchangeName, routingKey, true,null, msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
