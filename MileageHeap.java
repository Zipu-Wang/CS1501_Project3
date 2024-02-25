package cs1501_p3;


public class MileageHeap {

    private static final int DEFAULT_CAPACITY = 16;

    private int capacity;
    private int size;
    private Car[] heap;
    private MyHashMap<Integer> indexMap;


    public MileageHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MileageHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Car[capacity];
        this.indexMap = new MyHashMap<Integer>();
    }

    // insert in to heap(Array)
    public void insert(Car car) {
        if (size >= capacity) {
            resize();
        }

        heap[size] = car;
        indexMap.put(car.getVIN(), size);
        size++;
        percolateUp(size - 1);
    }

    // get the lowest mileage car in the heap
    public Car peek() {
        if (size == 0) {
            return null;
        }
        return heap[0];
    }

    // remove car
    public void removeCar(Car car) {
        if (size == 0) {
            return;
        }
        int index = indexMap.get(car.getVIN());
        exch(index, size - 1);
        
        size--;
        percolateUp(index);
        percolateDown(index);

        heap[size] = null;
        indexMap.remove(car.getVIN());
    }

    // Update mileage
    public int update(String VIN, int mileage) {
        int index = indexMap.get(VIN);

        heap[index].setMileage(mileage);
        index = percolateUp(index);
        index = percolateDown(index);
        return index;
    }

    // exchange two element in Array
    private void exch(int i, int j) {
        indexMap.put(heap[i].getVIN(), j); 
        indexMap.put(heap[j].getVIN(), i);

        Car tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    // resive the Array
    private void resize() {
        int newCapacity = capacity * 2;
        Car[] newHeap = new Car[newCapacity];
        for (int i = 0; i < capacity; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
        capacity = newCapacity;
    }

    // swim
    private int percolateUp(int index) {
        int currentIndex = index;
        int parentIndex = (index - 1) / 2;

        while (currentIndex > 0) {
            if (heap[currentIndex].getMileage() < heap[parentIndex].getMileage()) {
                exch(currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = (currentIndex - 1) / 2;
            } else {
                break;
            }
        }
        return currentIndex;
    }

    // sink
    private int percolateDown(int index) {
        int currentIndex = index;

        while (currentIndex < size) {
            int smallest = currentIndex;
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            if (leftChildIndex < size && heap[leftChildIndex].getMileage() < heap[smallest].getMileage()) {
                smallest = leftChildIndex;
            }
    
            if (rightChildIndex < size && heap[rightChildIndex].getMileage() < heap[smallest].getMileage()) {
                smallest = rightChildIndex;
            }

            if (smallest != currentIndex) {
                exch(currentIndex, smallest);
                currentIndex = smallest;
            } else {
                break;
            }
        }
        return currentIndex;
    }
}