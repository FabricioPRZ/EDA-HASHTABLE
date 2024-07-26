package hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable {
    private LinkedList<String>[] table;
    private int capacity;
    private HashFunction1 hashFunction1;
    private HashFunction2 hashFunction2;

    public HashTable(int capacity) {
        this.capacity = capacity;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        hashFunction1 = new HashFunction1();
        hashFunction2 = new HashFunction2();
    }

    public int getCapacity() {
        return capacity;
    }

    public void put(String key, String value, int functionNumber) {
        int index = hashFunction(key, functionNumber);
        String entry = key + ":" + value;
        table[index].add(entry);
    }

    public List<String> getDataAtIndex(int index) {
        List<String> data = new ArrayList<>();
        if (index < 0 || index >= capacity) {
            return data;
        }
        for (String entry : table[index]) {
            int separatorIndex = entry.indexOf(':');
            if (separatorIndex != -1) {
                String key = entry.substring(0, separatorIndex);
                String value = entry.substring(separatorIndex + 1);
                data.add("Key: " + key + ", Value: " + value);
            }
        }
        return data;
    }

    public String searchValueByKey(String key, int functionNumber) {
        String searchKey = key + ":";
        for (int i = 0; i < capacity; i++) {
            for (String entry : table[i]) {
                if (entry.startsWith(searchKey)) {
                    return "Índice: " + i + ", Valor: " + entry.substring(searchKey.length());
                }
            }
        }
        return null;
    }

    private int hashFunction(String key, int functionNumber) {
        if (functionNumber == 1) {
            return hashFunction1.hash(key) % capacity;
        } else {
            return hashFunction2.hash(key, capacity);
        }
    }

    public long measurePutTime(String key, String value, int functionNumber) {
        long startTime = System.nanoTime();
        put(key, value, functionNumber);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
