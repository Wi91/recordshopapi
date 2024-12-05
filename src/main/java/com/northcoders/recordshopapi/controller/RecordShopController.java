package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.service.RecordShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/record-shop")
public class RecordShopController {

    @Autowired
    RecordShopService recordShopService;
}
