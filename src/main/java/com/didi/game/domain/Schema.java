package com.didi.game.domain;

import java.io.Serializable;

public class Schema implements Serializable {
    private Integer id;

    private String name;

    private Integer table;

    private String type;

    private Integer valueStyle;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getValueStyle() {
        return valueStyle;
    }

    public void setValueStyle(Integer valueStyle) {
        this.valueStyle = valueStyle;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Schema other = (Schema) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTable() == null ? other.getTable() == null : this.getTable().equals(other.getTable()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getValueStyle() == null ? other.getValueStyle() == null : this.getValueStyle().equals(other.getValueStyle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTable() == null) ? 0 : getTable().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getValueStyle() == null) ? 0 : getValueStyle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", table=").append(table);
        sb.append(", type=").append(type);
        sb.append(", valueStyle=").append(valueStyle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}