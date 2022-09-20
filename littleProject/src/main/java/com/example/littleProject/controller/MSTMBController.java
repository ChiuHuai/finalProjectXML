package com.example.littleProject.controller;

import com.example.littleProject.controller.dto.request.MSTMBRequest;
import com.example.littleProject.controller.dto.request.dealPriceRequest;
import com.example.littleProject.controller.dto.response.MSTMBResponse;
import com.example.littleProject.model.entity.xml.Symbols;
import com.example.littleProject.service.MSTMBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MSTMBController {
    @Autowired
    private MSTMBService mstmbService;

    @PostMapping("/mstmb/updateCurPrice")
    public MSTMBResponse updateCurPriceInMSTMBByStock(@RequestBody MSTMBRequest request){
        MSTMBResponse response = this.mstmbService.updateCurPriceInMSTMBByStock(request);
        return response;
    }

    @PostMapping("/mstmb/findByStock")
    public MSTMBResponse findByStock(@RequestBody MSTMBRequest request){
        MSTMBResponse response = this.mstmbService.findByStock(request);
        return response;
    }

    @PostMapping("/mstmb/getDealPrice")
    public String getDealPrice(@RequestBody dealPriceRequest request){
        String url = "http://systexdemo.ddns.net:443/Quote/Stock.jsp?stock="+
                request.getStockId();
        RestTemplate restTemplate = new RestTemplate();
        Symbols result = restTemplate.getForObject(url, Symbols.class);

        return result.getSymbolList().stream().map(e->e.getShortname() + e.getDealprice()).collect(Collectors.joining(","));
    }

}
