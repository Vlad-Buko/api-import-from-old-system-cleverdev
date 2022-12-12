package com.cleverdev.clientService;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@SpringBootApplication
public class CleverDevNewSystem {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(CleverDevNewSystem.class, args);
    }

}
