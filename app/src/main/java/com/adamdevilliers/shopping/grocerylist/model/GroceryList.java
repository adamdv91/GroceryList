package com.adamdevilliers.shopping.grocerylist.model;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class GroceryList extends RealmObject implements Serializable {

    public GroceryList(){

    }

    @Required
    private String id;
    @Required
    private String name;
    @Required
    private Boolean valid;
    private RealmList<Items> items;

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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public RealmList<Items> getItems() {
        return items;
    }

    public void setItems(RealmList<Items> items) {
        this.items = items;
    }

//    public void addItems(Items items){
//        this.items.add(items);
//    }
}
