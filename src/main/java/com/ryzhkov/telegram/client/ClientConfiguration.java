package com.ryzhkov.telegram.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-rapidapi-host", "quotes15.p.rapidapi.com");
            requestTemplate.header("x-rapidapi-key", "c119804403mshf2b695c840d900ep11436fjsnde984c202174");
        };
    }
}
