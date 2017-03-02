package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class Car {

    // nickname used as the primary key for Cars (unique identifying value, like a fingerprint)
    // If User tries to enter an already existing nickname, don't allow it, display message.

    private String make;
    private String model;
    private int year;
    private String nickname;
    private double co2GramsPerMile_Highway;
    private double co2GramsPerKM_Highway;
    private double co2GramsPerMile_City;
    private double co2GramsPerKM_City;
    private boolean isHidden;
    private double cityMPG;
    private double highwayMPG;

    public Car(String make, String model, int year, String nickname, double highwayMPG, double cityMPG){
        this.make = make;
        this.model = model;
        this.year = year;
        this.nickname = nickname;
        this.highwayMPG = highwayMPG;
        this.cityMPG = cityMPG;
        this.co2GramsPerMile_Highway = calcCO2PerMile(highwayMPG);
        this.co2GramsPerMile_City = calcCO2PerMile(cityMPG);
        this.co2GramsPerKM_Highway = calcCO2PerKM(highwayMPG);
        this.co2GramsPerKM_City = calcCO2PerKM(cityMPG);
        this.isHidden = false;
    }

    // 8500g or 8.5kg/gallon averaged from multiple gov't sources
    private double calcCO2PerMile(double mpg){
        return (8500 / mpg);
    }

    private double calcCO2PerKM(double mpg){
        return (8500 / mpg * 0.621371);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getNickname() {
        return nickname;
    }

    public double getCo2GramsPerMile_Highway() {
        return co2GramsPerMile_Highway;
    }

    public double getCo2GramsPerKM_Highway() {
        return co2GramsPerKM_Highway;
    }

    public boolean isHidden(){
        return isHidden;
    }

    public void setHidden(boolean hidden){
        this.isHidden = hidden;
    }

    public double getCo2GramsPerMile_City() {
        return co2GramsPerMile_City;
    }

    public double getCo2GramsPerKM_City() {
        return co2GramsPerKM_City;
    }
}
