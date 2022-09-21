package com.example.littleProject.service;

import com.example.littleProject.controller.dto.request.MSTMBRequest;
import com.example.littleProject.controller.dto.request.dealPriceRequest;
import com.example.littleProject.controller.dto.response.MSTMBResponse;
import com.example.littleProject.model.MSTMBRepository;
import com.example.littleProject.model.entity.MSTMB;
import com.example.littleProject.model.entity.xml.Symbol;
import com.example.littleProject.model.entity.xml.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MSTMBService {
    @Autowired
    private MSTMBRepository mstmbRepository;

    @Autowired
    private Tool tool;

    public List<MSTMB> getAllMSTMB() {
        List<MSTMB> mstmbList = this.mstmbRepository.findAll();
        return mstmbList;
    }

//    public Symbol findByStockFromXML(String stockId){
//        String url = "http://systexdemo.ddns.net:443/Quote/Stock.jsp?stock="+ stockId;
//        RestTemplate restTemplate = new RestTemplate();
//        Symbols result = restTemplate.getForObject(url, Symbols.class);
//
//        return result.getSymbolList().get(0);
//    }

    @Cacheable(cacheNames = "stock", key = "#request.stock")
    public MSTMBResponse findByStock(MSTMBRequest request) {
        if (request.getStock().isBlank()) {
            return new MSTMBResponse().builder().message("參數為空，無法查詢").mstmb(null).build();
        }
        MSTMB mstmb = this.mstmbRepository.findByStock(request.getStock());
        if (null == mstmb) {
            return new MSTMBResponse().builder().message("查無結果").mstmb(null).build();
        }
        mstmb.setCurPrice(mstmb.getCurPrice().setScale(2, RoundingMode.HALF_UP));
        return new MSTMBResponse().builder().message("查詢成功").mstmb(mstmb).build();
    }

    @CacheEvict(cacheNames = "stock", key = "#request.stock", condition="#request.stock!=null")
    public MSTMBResponse updateCurPriceInMSTMBByStock(MSTMBRequest request) {
        if( request.getStock() == null || request.getCurPrice()==null){
            return new MSTMBResponse().builder().message("參數不得為空").mstmb(null).build();
        }
        MSTMB mstmb = this.mstmbRepository.findByStock(request.getStock());
        System.out.println(mstmb.getModTime());
        if (null == mstmb) {
            return new MSTMBResponse().builder().message("查無結果").mstmb(null).build();
        }

        BigDecimal curPrice = request.getCurPrice().setScale(2, RoundingMode.HALF_UP);

        String[] dateTimeNow = this.tool.dateTimeNow();
        this.mstmbRepository.updateCurPriceInMSTMBByStock(curPrice,dateTimeNow[0],dateTimeNow[1],"HuaiChiu", request.getStock());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }  //還是一樣
        MSTMB newMSTMB = this.mstmbRepository.findByStock(request.getStock());

        newMSTMB.setCurPrice(curPrice);
        newMSTMB.setModDate(dateTimeNow[0]);
        newMSTMB.setModTime(dateTimeNow[1]);
        return new MSTMBResponse().builder().message("修改成功").mstmb(newMSTMB).build();
    }
}
