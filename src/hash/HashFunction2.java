package hash;

public class HashFunction2 {
    public int hash(String key, int tableSize) {
        int hash = 0;

        for (int i = 0; i < key.length(); i++) {
            hash = 31 * hash + key.charAt(i);
        }

        hash %= tableSize;
        if (hash < 0) {
            hash += tableSize;
        }

        return hash;
    }
}
