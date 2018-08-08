package com.didi.game.service;

import com.didi.game.common.AppBusinessException;
import com.didi.game.dao.SQLMapper;
import com.didi.game.dao.SchemaMapper;
import com.didi.game.dao.TableMapper;
import com.didi.game.domain.Schema;
import com.didi.game.domain.SchemaExample;
import com.didi.game.domain.Table;
import com.didi.game.domain.TableExample;
import com.didi.game.dto.SchemaDTO;
import com.didi.game.dto.TableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TableService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TableMapper tableMapper;

    @Autowired
    SchemaMapper schemaMapper;

    @Autowired
    SQLMapper sqlMapper;

    @Transactional(rollbackFor = Exception.class)
    public int createTable(TableDTO tableDTO) {

        if (tableDTO == null || StringUtils.isEmpty(tableDTO.getName())) {
            throw new AppBusinessException("表名不允许为空");
        }
        Table table = new Table();
        table.setName(tableDTO.getName());
        tableMapper.insert(table);

        int tableId = table.getId();
        createSchemas(tableDTO.getFields(), table);

        build(tableDTO);
        return tableId;
    }

    private void build(TableDTO tableDTO) {
        String sql = createSql(tableDTO);
        sqlMapper.executeSql(sql);
    }

    private String createSql(TableDTO tableDTO) {
        String sql = "create table `" + tableDTO.getName() + "` (`id` int(11) not null auto_increment, ";
        List<SchemaDTO> schemaDTOs = tableDTO.getFields();
        for (int i = 0; i < schemaDTOs.size(); i++) {
            sql += getTypeSQL(schemaDTOs.get(i)) + ",";
        }
        sql += "PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8";
        return sql;
    }

    private String getTypeSQL(SchemaDTO dto) {
        if (dto == null || StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getType())) {
            throw new AppBusinessException("表属性不能无名称或者没有类型");
        }
        return "`" + dto.getName() + "` " + getType(dto.getType());
    }

    private String getType(String type) {
        switch (type) {
            case "int":
                return "int(11)";
            case "varchar":
                return "varchar(255)";
            case "datetime":
                return "datetime";
            case "timestamp":
                return "timestamp";
            case "decimal":
                return "decimal(10,6)";
            default:
                return type;
        }
    }

    private void createSchemas(List<SchemaDTO> schemaDTOs, Table table) {
        if (CollectionUtils.isEmpty(schemaDTOs)) {
            throw new AppBusinessException("表不允许没有字段");
        }
        schemaDTOs.forEach(i -> createSchema(i, table));
    }

    private void createSchema(SchemaDTO dto, Table table) {
        if (dto != null) {
            Schema schema = new Schema();
            schema.setName(dto.getName());
            schema.setTableId(table.getId());
            schema.setType(dto.getType());
            schema.setValueStyle(dto.getValueStyle());
            schemaMapper.insert(schema);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean update(TableDTO tableDTO) {
        if (tableDTO == null || tableDTO.getId() == null) {
            return false;
        }
        Table table = tableMapper.selectByPrimaryKey(tableDTO.getId());
        if (!table.getName().equals(tableDTO.getName()) && tableDTO.getName() != null) {
            renameTable(table.getName(), tableDTO.getName());

            table.setName(tableDTO.getName());
            tableMapper.updateByPrimaryKey(table);
        }
        updateSchemas(tableDTO.getFields(), table);


        return true;
    }

    private void renameTable(String original, String newName) {
        sqlMapper.executeSql("ALTER TABLE `" + original + "` RENAME `" + newName + "`");
    }

    private boolean updateSchemas(List<SchemaDTO> schemas, Table table) {
        if (CollectionUtils.isEmpty(schemas)) {
            return false;
        }
        // TODO 去重名列
        SchemaExample example = new SchemaExample();
        example.createCriteria().andTableIdEqualTo(table.getId());
        List<Integer> originalSchemas = schemaMapper.selectByExample(example).stream().map(Schema::getId)
                .collect(Collectors.toList());

        for (SchemaDTO dto : schemas) {
            Integer id = dto.getId();
            if (originalSchemas.contains(id)) {
                originalSchemas.remove(id);
            }
            if (id == null) {
                createSchema(dto, table);
                insertSchema(dto, table);
            } else {
                updateSchema(dto, table);
            }
        }
        deleteUnnecessarySchema(originalSchemas, table.getName());
        return true;
    }

    private void insertSchema(SchemaDTO dto, Table table) {
        String sql = "ALTER TABLE `" + table.getName() + "` ADD `" + dto.getName() + "` " + getType(dto.getType());
        sqlMapper.executeSql(sql);
    }

    private void deleteUnnecessarySchema(List<Integer> ids, String table) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        SchemaExample example = new SchemaExample();
        example.createCriteria().andIdIn(ids);
        List<Schema> schemas = schemaMapper.selectByExample(example);

        schemas.stream().forEach(i -> {
            String sql = "ALTER TABLE `" + table + "` DROP COLUMN `" + i.getName() + "`";
            sqlMapper.executeSql(sql);
        });

        ids.parallelStream().forEach(id -> schemaMapper.deleteByPrimaryKey(id));
    }

    private void updateSchema(SchemaDTO dto, Table table) {
        Schema schema = schemaMapper.selectByPrimaryKey(dto.getId());
        String oldName = schema.getName();
        schema.setName(dto.getName());
        schema.setValueStyle(dto.getValueStyle());
        schema.setType(dto.getType());
        schema.setTableId(table.getId());
        schemaMapper.updateByPrimaryKeySelective(schema);

        String sql = "ALTER TABLE `" + table.getName() + "` CHANGE COLUMN `" + oldName + "` `" + schema.getName() +
                "` " +
                getType(schema.getType());
        sqlMapper.executeSql(sql);
    }

    public List<TableDTO> getTables(Integer id) {
        List<Table> tables = Collections.emptyList();
        if (id == null) {
            TableExample example = new TableExample();
            tables = tableMapper.selectByExample(example);
        } else {
            Table table = tableMapper.selectByPrimaryKey(id);
            tables.add(table);
        }
        return tables.stream().map(this::toTableDTO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        if (id == null) {
            return;
        }
        Table table = tableMapper.selectByPrimaryKey(id);
        deleteTable(table.getName());

        tableMapper.deleteByPrimaryKey(id);
        SchemaExample schemaExample = new SchemaExample();
        schemaExample.createCriteria().andTableIdEqualTo(id);
        schemaMapper.deleteByExample(schemaExample);
    }

    private void deleteTable(String table) {
        String sql = "DROP TABLE `" + table + "`";
        sqlMapper.executeSql(sql);
    }

    public TableDTO toTableDTO(Table table) {
        TableDTO dto = new TableDTO();
        if (table != null) {
            Integer id = table.getId();
            SchemaExample example = new SchemaExample();
            if (id == null) {
                logger.error("id is null");
                return new TableDTO();
            }
            example.createCriteria().andTableIdEqualTo(id);
            List<Schema> schemas = schemaMapper.selectByExample(example);
            List<SchemaDTO> schemaDTOS = schemas.stream().map(this::toSchemaDTO).collect(Collectors.toList());

            dto.setId(id);
            dto.setFields(schemaDTOS);
            dto.setName(table.getName());
        }
        return dto;
    }

    public SchemaDTO toSchemaDTO(Schema schema) {
        SchemaDTO dto = new SchemaDTO();
        if (schema != null) {
            dto.setId(schema.getId());
            dto.setName(schema.getName());
            dto.setType(schema.getType());
            dto.setValueStyle(schema.getValueStyle());
        }
        return dto;
    }
}
