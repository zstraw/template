package com.didi.game.controller;


import com.didi.game.common.Result;
import com.didi.game.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "zy/3/datas", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {

    @Autowired
    DataService dataService;

    @RequestMapping(method = RequestMethod.GET)
    public Result get(String table, Integer id){
        List<?> tableDTOS = dataService.getDatas(table, id);
        return new Result(tableDTOS);
    }

}
