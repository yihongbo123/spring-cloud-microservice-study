package com.tgw360.controller;

import com.tgw360.entity.Bar;
import com.tgw360.mapper.BarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by 易弘博 on 2018/1/31 15:08
 */
@RestController
@RequestMapping("/bar")
public class BarController {
    @Autowired
    private BarMapper barMapper;
    private final Logger logger = LoggerFactory.getLogger(BarController.class);
    @GetMapping("/{code}/{begin}/{end}")
    public List<Bar> findByCode(@PathVariable String code,@PathVariable String begin,@PathVariable String end){
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
            LocalDate localDate = LocalDate.parse(begin, timeFormatter);
            LocalDate localDate1 = LocalDate.parse(end, timeFormatter);
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
            ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(zoneId);
            Date bg = Date.from(zonedDateTime.toInstant());
            Date ed = Date.from(zonedDateTime1.toInstant());
            List<Bar> barList = barMapper.findByCodeAndDate(code, bg, ed);
            return barList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("test")
    public String test(){
        logger.info("info--------------------------------------------------");
        logger.debug("debug--------------------------------------------------");
        return "success";
    }
}
