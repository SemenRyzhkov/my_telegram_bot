package com.ryzhkov.telegram.client;


import com.ryzhkov.telegram.model.Quotes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "quotes",
        url = "https://quotes15.p.rapidapi.com/quotes/random",
        configuration = ClientConfiguration.class)
public interface QuotesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    Quotes getRandomQuote(@RequestParam(value = "language_code") String key);
}
