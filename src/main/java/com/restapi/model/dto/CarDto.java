package com.restapi.model.dto;

public class CarDto {
    private Long id;
    private String brand;
    private String nModel;
    private String gearBox;
    private String WD;
    private int yyRelease;

    public CarDto(Long id, String brand, String nModel, String gearBox, String WD, int yyRelease) {
        this.id = id;
        this.brand = brand;
        this.nModel = nModel;
        this.gearBox = gearBox;
        this.WD = WD;
        this.yyRelease = yyRelease;
    }

    public CarDto(Long id) {
        this.id = id;
    }

    public CarDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getnModel() {
        return nModel;
    }

    public void setnModel(String nModel) {
        this.nModel = nModel;
    }

    public String getGearBox() {
        return gearBox;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    public String getWD() {
        return WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public int getYyRelease() {
        return yyRelease;
    }

    public void setYyRelease(int yyRelease) {
        this.yyRelease = yyRelease;
    }
}
