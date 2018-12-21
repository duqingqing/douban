package com.douban.book.controller;

import com.douban.book.service.Information.InformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: duqingqing
 * @Date: 18-12-21 10:24
 * @Description:
 */
@RestController
@RequestMapping("/information")
public class BookInformationController {

    private InformationManager informationManager;

    @Autowired
    public void setInformationManager(InformationManager informationManager) {
        this.informationManager = informationManager;
    }

    @GetMapping("/get")
    public String doGetBookInformation(){
        informationManager.getInformation();
        return "finish";
    }

}
