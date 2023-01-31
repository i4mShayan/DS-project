public class LongList {
    private int size = 0;
    private static final int CAPACITY = 10;
    private long[] elements;

    public LongList() {
        elements = new long[CAPACITY];
    }

    public void add(long object) {
        if (size == elements.length) {
            doubleTheCapacity();
        }
        elements[size++] = object;
    }

    private void doubleTheCapacity() {
        long[] temp = new long[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            temp[i] = elements[i];
        }
        elements = temp;
    }

    public long get(int i) {
        if (i >= size || i < 0) {
            return 0;
        }
        return elements[i];
    }

    public long remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        long item = elements[i];
        for (int j = i; j < size - 1; j++) {
            elements[j] = elements[j + 1] ;
        }
        size--;
        return item;
    }

    public int size() {
        return size;
    }

    public long getLast(){
        return (size - 1) >= 0 ? elements[size - 1] : 0;
    }
}