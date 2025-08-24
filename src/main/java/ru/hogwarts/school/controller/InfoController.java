package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

import javax.sound.sampled.Port;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping ("/port")
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(infoService.getPort());
    }
}
