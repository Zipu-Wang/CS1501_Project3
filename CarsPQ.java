package cs1501_p3;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CarsPQ implements CarsPQ_Inter {
    
    private MyHashMap<Car> carsByVin;
    private PriceHeap priceHeap;
    private MileageHeap mileageHeap;
    // Make + Model is key
    private MyHashMap<PriceHeap> priceHeapByModel;
    private MyHashMap<MileageHeap> mileageHeapByModel;

    public CarsPQ() {
        carsByVin = new MyHashMap<Car>();
        priceHeap = new PriceHeap();
        mileageHeap = new MileageHeap();
        priceHeapByModel = new MyHashMap<PriceHeap>();
        mileageHeapByModel = new MyHashMap<MileageHeap>();
    }

    public CarsPQ(String filename) {
        this();
        // read from file
        try {

            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.length() == 0 || line.startsWith("#")) {
                    continue;
                }
                String[] info = line.split(":");
                Car car = new Car(info[0], info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]), info[5]);
                add(car);
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    // add to VIN MyHashMap, price Heap, mileage Heap and Make + Model MyHashMap
    public void add(Car c) throws IllegalStateException {
        if (carsByVin.contains(c.getVIN())) {
            throw new IllegalStateException("Car with the same VIN already exists");
        }
        carsByVin.put(c.getVIN(), c);
        priceHeap.insert(c);
        mileageHeap.insert(c);

        String key = makeModelKey(c.getMake(), c.getModel());
        if (!priceHeapByModel.contains(key)) {
            priceHeapByModel.put(key, new PriceHeap());
        }
        priceHeapByModel.get(key).insert(c);

        if (!mileageHeapByModel.contains(key)) {
            mileageHeapByModel.put(key, new MileageHeap());
        }
        mileageHeapByModel.get(key).insert(c);
    }

    public Car get(String vin) throws NoSuchElementException {
        if (carsByVin.contains(vin)) {
            return carsByVin.get(vin);
        } else {
            throw new NoSuchElementException("Can not found specified VIN in the datastructure.");
        }
    }

    // update the car's price, price Heap and Make + Model MyHashMap
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException {
        if (!carsByVin.contains(vin)) {
            throw new NoSuchElementException("Can not found specified VIN in the datastructure.");
        }

        priceHeap.update(vin, newPrice);
        Car c = carsByVin.get(vin);
        c.setPrice(newPrice);
        String key = makeModelKey(c.getMake(), c.getModel());
        priceHeapByModel.get(key).update(vin, newPrice);
    }

    // update the car's mileage, mileage Heap and Make + Model MyHashMap
    public void updateMileage(String vin, int newMileage) throws NoSuchElementException {
        if (!carsByVin.contains(vin)) {
            throw new NoSuchElementException("Can not found specified VIN in the datastructure.");
        }

        mileageHeap.update(vin, newMileage);
        Car c = carsByVin.get(vin);
        c.setMileage(newMileage);
        String key = makeModelKey(c.getMake(), c.getModel());
        mileageHeapByModel.get(key).update(vin, newMileage);
    }

    // update color
    public void updateColor(String vin, String newColor) throws NoSuchElementException {
        if (!carsByVin.contains(vin)) {
            throw new NoSuchElementException("Can not found specified VIN in the datastructure.");
        }

        Car c = carsByVin.get(vin);
        c.setColor(newColor);
    }

    // remove from VIN MyHashMap, price Heap, mileage Heap and Make + Model MyHashMap
    public void remove(String vin) throws NoSuchElementException {
        if (!carsByVin.contains(vin)) {
            throw new NoSuchElementException("Can not found specified VIN in the datastructure.");
        }
        Car thisCar = carsByVin.get(vin);
        carsByVin.remove(vin);
        priceHeap.removeCar(thisCar);
        mileageHeap.removeCar(thisCar);

        String key = makeModelKey(thisCar.getMake(), thisCar.getModel());
        priceHeapByModel.get(key).removeCar(thisCar);
        mileageHeapByModel.get(key).removeCar(thisCar);
    }

    public Car getLowPrice() {
        return priceHeap.peek();
    }

    // Method to get the car with lowest price (specific make and model)
    public Car getLowPrice(String make, String model) {
        PriceHeap heap = priceHeapByModel.get(makeModelKey(make, model));
        return heap != null ? heap.peek() : null;
    }

    public Car getLowMileage() {
        return mileageHeap.peek();
    }

    public Car getLowMileage(String make, String model) {
        MileageHeap heap = mileageHeapByModel.get(makeModelKey(make, model));
        return heap != null ? heap.peek() : null;
    }

    private String makeModelKey(String make, String model) {
        return make + "----" + model;
    }

}