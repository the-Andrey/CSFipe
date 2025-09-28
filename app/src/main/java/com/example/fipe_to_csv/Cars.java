package com.example.fipe_to_csv;


import java.io.Serializable;

public class Cars implements Serializable {

    private String code;
    private String name;
    private String brand;
    private String codeFipe;
    private String fuel;
    private String fuelAcronym;
    private String model;
    private Integer modelYear;
    private String price;
    private PriceHistory priceHistory;
    private String referenceMonth;
    private Integer vehicleType;

    public Cars(String code, String name, String brand, String codeFipe, String fuel, String fuelAcronym, String model, Integer modelYear, String price, PriceHistory priceHistory, String referenceMonth, Integer vehicleType) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.codeFipe = codeFipe;
        this.fuel = fuel;
        this.fuelAcronym = fuelAcronym;
        this.model = model;
        this.modelYear = modelYear;
        this.price = price;
        this.priceHistory = priceHistory;
        this.referenceMonth = referenceMonth;
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public String getCodeFipe() {
        return codeFipe;
    }

    public String getFuel() {
        return fuel;
    }

    public String getFuelAcronym() {
        return fuelAcronym;
    }

    public String getModel() {
        return model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public String getPrice(){
        return price;
    }

    public PriceHistory getPriceHistory() {
        return priceHistory;
    }

    public String getReferenceMonth(){
        return referenceMonth;
    }

    public Integer getVehicleType(){
        return vehicleType;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
