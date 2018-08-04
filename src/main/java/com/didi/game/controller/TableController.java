package com.didi.game.controller;

import com.didi.game.common.Result;
import com.didi.game.domain.Table;
import com.didi.game.dto.TableDTO;
import com.didi.game.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author zy
 */
@RestController
@RequestMapping(value = "zy/3/tables", produces = MediaType.APPLICATION_JSON_VALUE)
public class TableController {

    @Autowired
    TableService tableService;

    @RequestMapping(method = RequestMethod.GET)
    public Result get(){
        List<TableDTO> tableDTOS = tableService.getTables(null);
        return new Result(tableDTOS);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Integer id){

        return new Result(tableService.getTables(id));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody TableDTO tableDTO){
        Boolean result = tableService.update(tableDTO);
        return new Result(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestBody TableDTO tableDTO){

        return new Result(tableService.createTable(tableDTO));
    }

    /*@RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Result delParkStruct(@PathVariable(value = "id") Integer id){
//        Boolean result = parkStructService.delete(id);
        return new Result(result);
    }*/



}
