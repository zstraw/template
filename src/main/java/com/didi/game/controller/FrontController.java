package com.didi.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author zy
 */
@Controller
@RequestMapping("zy/3")
public class FrontController {

    @RequestMapping("toParkStruct")
    public String toParkStruct(){
        return "parkStruct";
    }
}
