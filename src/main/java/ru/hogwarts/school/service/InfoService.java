package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Service
public class InfoService {

    @Value("${server.port}")
    private String port;

    private final static Logger LOGGER = LoggerFactory.getLogger(InfoService.class);

    public String getPort() {
        return port;
    }

    public Long getNumber() {
        long startTime2 = System.currentTimeMillis();
        Long sum = LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .limit(1_000_000)
                .sum();
        long finishTime2 = System.currentTimeMillis()-startTime2;
        LOGGER.info ("время работы метода 2 - {}", finishTime2);
        return sum;
    }
}
