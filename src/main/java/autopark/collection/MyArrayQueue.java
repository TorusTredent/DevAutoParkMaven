package autopark.collection;

public class MyArrayQueue<T> {

    private Object[] array;
    private int size;
    private int capacity;
    private int indexOfFirst;
    private int indexOfLast;

    private static final int DEFAULT_CAPACITY = 12;

    public MyArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    public MyArrayQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(String.format("Illegal Capacity: %s", capacity));
        }
        this.array = new Object[capacity];
        this.capacity = capacity;
    }

    public void enqueue(T element) {
        if (indexOfLast >= capacity - 1) {
            changeArraySize();
        }
        this.array[indexOfLast] = element;
        this.indexOfLast++;
        this.size++;
    }

    public T dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        T firstObject = (T) this.array[indexOfFirst];;
        indexOfFirst++;
        size--;
        return firstObject;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return (T) this.array[indexOfFirst];
    }

    public int size() {
        return size;
    }

    private void changeArraySize() {
        this.capacity *= 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(this.array, this.indexOfFirst, newArray, 0, size);
        this.array = newArray;
        this.indexOfLast = size - 1;
    }
}
