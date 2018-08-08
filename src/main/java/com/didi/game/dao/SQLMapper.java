package com.didi.game.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface SQLMapper {

    List<LinkedHashMap<String, Object>> executeSql(String sql);


}