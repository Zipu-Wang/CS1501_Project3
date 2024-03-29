package cs1501_p3;

public class Car implements Car_Inter {
    private String VIN;
    private String make;
    private String model;
    private int price;
    private int mileage;
    private String color;

    public Car(String VIN, String make, String model, int price, int mileage, String color) {
        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.color = color;
    }

    public String getVIN() {
        return VIN;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }

    public String getColor() {
        return color;
    }


    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    public void setMileage(int newMileage) {
        this.mileage = newMileage;
    }

    public void setColor(String newColor) {
        this.color = newColor;
    }

}