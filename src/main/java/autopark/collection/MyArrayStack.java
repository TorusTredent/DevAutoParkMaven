package autopark.collection;

public class MyArrayStack <T> {

    private Object[] array;
    private int size;
    private int capacity;

    private static final int DEFAULT_CAPACITY = 12;

    public MyArrayStack() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    public MyArrayStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(String.format("Illegal Capacity: %s", capacity));
        }
        this.array = new Object[capacity];
        this.capacity = capacity;
    }

    public void push(T element) {
        if (this.size >= this.capacity - 1) {
            changeArraySize();
        }
        this.array[size] = element;
        this.size++;
    }

    public T pop() {
        if (this.size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        T lastObject = (T) this.array[size - 1];;
        this.size--;
        return lastObject;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return (T) this.array[size];
    }

    public int size() {
        return size;
    }


    private void changeArraySize() {
        this.capacity *= 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(this.array, 0, newArray, 0, size);
        this.array = newArray;
    }
}
