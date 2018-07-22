package com.didi.game.service;

import com.didi.game.dao.ParkStructMapper;
import com.didi.game.domain.ParkStruct;
import com.didi.game.domain.ParkStructExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ParkStructService {

    @Autowired
    ParkStructMapper parkStructMapper;

    public List<ParkStruct> getParkStructs() {
        ParkStructExample example = new ParkStructExample();
        example.createCriteria();
        List<ParkStruct> parkStructs = parkStructMapper.selectByExample(example);
        return parkStructs;
    }

    public String getName(Integer id) {
        ParkStruct parkStruct = parkStructMapper.selectByPrimaryKey(id);
        if(parkStruct==null){
            return null;
        }
        return parkStruct.getName();
    }

    public Boolean update(ParkStruct parkStruct) {
        if (parkStruct != null) {
            parkStructMapper.updateByPrimaryKey(parkStruct);
        }
        return true;
    }

    public Boolean insert(ParkStruct parkStruct) {
        if (parkStruct != null && !StringUtils.isEmpty(parkStruct.getName())) {
            parkStructMapper.insert(parkStruct);
        }
        return true;
    }

    public Boolean delete(Integer id) {
        if(id!=null){
            parkStructMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
