package com.didi.game.dto;

import java.util.List;

public class TableDTO {

    private Integer id;
    private String name;
    private List<SchemaDTO> fields;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SchemaDTO> getFields() {
        return fields;
    }

    public void setFields(List<SchemaDTO> fields) {
        this.fields = fields;
    }
}
