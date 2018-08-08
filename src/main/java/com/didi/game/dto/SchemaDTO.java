package com.didi.game.dto;

public class SchemaDTO {
    private Integer id;
    private String name;
    private String type;
    private Integer valueStyle;

    public SchemaDTO(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValueStyle() {
        return valueStyle;
    }

    public void setValueStyle(Integer valueStyle) {
        this.valueStyle = valueStyle;
    }
}
