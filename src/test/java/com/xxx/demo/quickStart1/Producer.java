package com.xxx.demo.quickStart1;

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
    public void sentMessage() {
        IntStream.range(0, 5).forEach(i -> {
                    try {
                        channels.getChannelList().get(0).basicPublish("", "test001", null, "hello rabbitmq".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
