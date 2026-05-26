package com.example.nobsv2;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean //gets inserted into spring container
    //gives us access to rest templates throughout the app
    public RestTemplate restTemplate() {
        //configure your rest template options
        return new RestTemplate();
    }

}
