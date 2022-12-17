package com.cleverdev.clientService;

import com.cleverdev.clientService.controller.NoteController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by Vladislav Domaniewski
 */

@SpringBootApplication
public class CleverDevNewSystem {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CleverDevNewSystem.class, args);
    }

}
