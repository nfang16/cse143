//Nicholas Fang
//CSE143
//TA Ben Macmillian
//Program compiles 37 guitar strings and implements a different implementation of the
//Class

// skeleton version of the class

public class Guitar37 implements Guitar {
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
        
    //array list that corresponds with keyboard and contains the frequencies of the keys 
    private GuitarString[] pianoString;
    //counts the number of times that tic has been called
    private int ticCount = 0;
    
    
    //models a guitar with 37 different strings
    //ith character of the guitar string corresponds with the frequency calculated using
    //the below stated equation and stored into a list
    public Guitar37() {
        pianoString = new GuitarString[KEYBOARD.length()];
        for(int i = 0; i < KEYBOARD.length(); i++) {
            pianoString[i] = new GuitarString(440 * Math.pow(2, (i-24)/12.0));
        }
    }
    
    //pitch value converted to index by adding 24 to the pitch value
    //method called to specify which note to play by passing it a pitch
    //if note cant be played, it is ignored
    public void playNote(int pitch) {
        if((pitch + 24 >= 0) && (pitch + 24 < KEYBOARD.length())) {
            int index = pitch + 24;
            pianoString[index].pluck();
        }
    }
    
    //verifies that a particular key exists for the corresponding string of the guitar
    public boolean hasString(char key) {
        return KEYBOARD.indexOf(key) != -1;
    }
    
    //precondition that the key is legal for guitar, otherwise throws IllegalArgumentException
    //plays the corresponding key from the guitar string
    public void pluck(char key) {
        if(!(hasString(key))) {
            throw new IllegalArgumentException();
        }
        pianoString[KEYBOARD.indexOf(key)].pluck();
    }
    
    //computes the sum of samples from the strings in the guitar
    //returns the value for the current sound sample
    public double sample() {
        double sumOfSamples = 0;
        for(int i = 0; i < pianoString.length; i++) {
            sumOfSamples += pianoString[i].sample();
        }
        return sumOfSamples;
    }
    
    //advances the time forward with one tic at a time with each corresponding index
    public void tic() {
        for(int i = 0; i < pianoString.length; i++) {
            pianoString[i].tic();
        }
        ticCount++;
    }
    
    //returns the number of times that the tic method has been called 
    public int time() {
        return ticCount;
    }

}