package hash;

public class HashFunction1 {
    public int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash *= 31;
        }

        if (hash < 0) {
            hash = -hash;
        }

        return hash;
    }
}
