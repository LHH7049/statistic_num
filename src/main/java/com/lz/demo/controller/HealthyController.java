package com.lz.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lz
 * @Date: 2021/4/22 11:20
 */
@RequestMapping("/healthy")
@RestController
public class HealthyController {

    @RequestMapping("/check.json")
    public String check(){
        return "SUCCESS";
    }
}
