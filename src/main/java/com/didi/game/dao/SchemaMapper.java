package com.didi.game.dao;

import com.didi.game.domain.Schema;
import com.didi.game.domain.SchemaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchemaMapper {
    int countByExample(SchemaExample example);

    int deleteByExample(SchemaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Schema record);

    int insertSelective(Schema record);

    List<Schema> selectByExample(SchemaExample example);

    Schema selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Schema record, @Param("example") SchemaExample example);

    int updateByExample(@Param("record") Schema record, @Param("example") SchemaExample example);

    int updateByPrimaryKeySelective(Schema record);

    int updateByPrimaryKey(Schema record);
}