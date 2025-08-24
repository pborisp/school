package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!Main")
public class InfoServiceImplTest implements InfoService {
    private String port = "8081";

    @Override
    public String getPort() {
        return port;
    }
}