//Nicholas Fang

public class LetterInventory {
    public static final int DEFAULT_CAPACITY = 26;
    
    private int size;
    private int[] elementData;
    
    public LetterInventory(String data) {
        elementData = new int[DEFAULT_CAPACITY];
        data = data.toLowerCase();
        
        for(int i = 0; i < data.length(); i++) {
            if(Character.isLetter(data.charAt(i))) {
                size++;
                elementData[data.charAt(i) - 'a']++;
            }
        }
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int get(char letter) {
        letter = Character.toLowerCase(letter);
        if(!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
        return elementData[letter - 'a'];
    }
    
    public String toString() {
        String results = "[";
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            for(int j = 0; j < elementData[i]; j++) {
                results += (char) ('a' + i);
            }
        }
        return results + "]";
    }
    
    public void set(char letter, int value) {
        if(!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        size += value - elementData[letter - 'a'];
        elementData[letter - 'a'] = value;
    }
    
    public LetterInventory add(LetterInventory other) {
        LetterInventory sum = new LetterInventory("");
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            sum.elementData[i] = this.elementData[i] + other.elementData[i];
        }
        sum.size = this.size + other.size;
        return sum;
    }
}