package com.concertpharmaceutical.models;

/**
 * Created by Ayesha Ahmad on 3/7/2016.
 */
public class Container {

    public Container(){};

     private int number;
     private String uid;
     private int substance_id;
     private int location_id;
     private String description;
     private float size;
     private String uom;
     private String cas;
     private int supplier_id;
     private String catalog_no;
     private String expiration_date;
     private int id;
     private String lot_no;
     private String substance;
     private String location;
     private String supplier;
    private String disposed_at;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSubstance_id() {
        return substance_id;
    }

    public void setSubstance_id(int substance_id) {
        this.substance_id = substance_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getCatalog_no() {
        return catalog_no;
    }

    public void setCatalog_no(String catalog_no) {
        this.catalog_no = catalog_no;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDisposed_at() {
        return disposed_at;
    }

    public void setDisposed_at(String disposed_at) {
        this.disposed_at = disposed_at;
    }

}
