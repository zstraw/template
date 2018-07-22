package com.didi.game.controller;

import com.didi.game.common.Result;
import com.didi.game.domain.ParkStruct;
import com.didi.game.service.ParkStructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author zy
 */
@RestController
@RequestMapping(value = "zy/3/parkStructs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkStructController {

    @Autowired
    ParkStructService parkStructService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getParkStructs(){
        List<ParkStruct> parkStructs = parkStructService.getParkStructs();
        return new Result(parkStructs);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result getParkStructs(@PathVariable Integer id){

        return new Result(parkStructService.getName(id));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result updateParkStruct(@RequestBody ParkStruct parkStruct){
        Boolean result = parkStructService.update(parkStruct);
        return new Result(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result insertParkStruct(@RequestBody ParkStruct parkStruct){

        return new Result(parkStructService.insert(parkStruct));
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Result delParkStruct(@PathVariable(value = "id") Integer id){
        Boolean result = parkStructService.delete(id);
        return new Result(result);
    }



}
