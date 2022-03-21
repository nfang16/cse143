//Nicholas Fang
//CSE143
//TA Ben Macmillian
//This program creates an inventory that stores the number of 
//occurences of individual letters for a given string

public class LetterInventory {
    public static final int DEFAULT_CAPACITY = 26;
    
    //The number of elements counted and stored 
    private int size;
    //Array list tracking the occurences of each letter counted from a string
    private int[] elementData;
    
    //constructs elementaData inventory for the letters that appear in a string
    //and counts number of occurences for each letter.
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
    
    //post: returns the size of the elementData inventory
    public int size() {
        return size;
    }
    
    //post: returns true if elementData is empty, otherwise returns false
    public boolean isEmpty() {
        return size == 0;
    }
    
    //pre: values must be letters, throws an IllegalArgumentException otherwise
    //post: returns the number of elements stored in the inventory
    public int get(char letter) {
        letter = Character.toLowerCase(letter);
        if(!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
        return elementData[letter - 'a'];
    }
    
    //post: returns a string of the elementData inventory in order from a-z
    public String toString() {
        String results = "[";
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            for(int j = 0; j < elementData[i]; j++) {
                results += (char) ('a' + i);
            }
        }
        return results + "]";
    }
    
    //pre: values must be letters and must be positive, throws an 
    //                                      IllegalArgumentException otherwise
    //post: set the value for an occurence of a individual letter
    public void set(char letter, int value) {
        if(!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException();
        }
        letter = Character.toLowerCase(letter);
        this.size -= this.elementData[letter - 'a'];
        this.elementData[letter - 'a'] = value;
        this.size += value;

    }
    
    //pre: takes in a letterinventory other as a param
    //post: returns a new letterInventory with the combined values of the 2 lists,
    //  combining the values of each indiviual letter counted from the 2 list and storing
    //  that value into a new inventory list
    public LetterInventory add(LetterInventory other) {
        LetterInventory combined = new LetterInventory("");
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            combined.elementData[i] = this.elementData[i] + other.elementData[i];
        }
        combined.size = this.size() + other.size();
        return combined;
    }
    
    //pre: takes in a letterInventory other as a param
    //post: returns a new LetterInventory with the subtracted values of (list1-list2)
    //  stores the new values into a new inventory list.
    //  returns list as null if the new value is negative
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory subtracted = new LetterInventory("");
        for(int i = 0; i < DEFAULT_CAPACITY; i++) {
            subtracted.elementData[i] = this.elementData[i] - other.elementData[i];
            if(subtracted.elementData[i] < 0) {
                return subtracted = null;
            }
            subtracted.size = this.size() - other.size();
        }
        return subtracted;
    }
    
}