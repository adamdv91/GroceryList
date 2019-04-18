package com.adamdevilliers.shopping.grocerylist.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Items extends RealmObject implements Serializable {

    public Items(){
    }

    @PrimaryKey
    private String id;
    @Required
    private String name;
    @Required
    private Integer quantity;
    @Required
    private Boolean paid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
