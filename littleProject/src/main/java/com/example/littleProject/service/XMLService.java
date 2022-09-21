package com.example.littleProject.service;

import com.example.littleProject.controller.dto.request.dealPricesRequest;
import com.example.littleProject.model.entity.xml.Symbols;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class XMLService {
    public String getDealPrices(dealPricesRequest request) {
        String url = "http://systexdemo.ddns.net:443/Quote/Stock.jsp?stock=" +
                request.getStocksId().stream().collect(Collectors.joining(","));
        RestTemplate restTemplate = new RestTemplate();
        Symbols result = restTemplate.getForObject(url, Symbols.class);

        return result.toString();
    }
}
