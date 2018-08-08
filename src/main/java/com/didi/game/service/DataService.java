package com.didi.game.service;

import com.didi.game.dao.SQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    @Autowired
    SQLMapper sqlMapper;

    public List<?> getDatas(String table, Integer id) {
        if (StringUtils.isEmpty(table)) {
            return Collections.EMPTY_LIST;
        }
        List<?> datas = sqlMapper.executeSql(getSelectSql(table, id));
        return datas;
    }

    private String getSelectSql(String table, Integer id) {
        String sql = "SELECT * FROM `" + table + "`";
        if (id != null) {
            sql += " WHERE `id`=" + id;
        }
        return sql;
    }

    /*public String getName(Integer id) {
        ParkStruct parkStruct = parkStructMapper.selectByPrimaryKey(id);
        if (parkStruct == null) {
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
        if (id != null) {
            parkStructMapper.deleteByPrimaryKey(id);
        }
        return true;
    }*/
}
