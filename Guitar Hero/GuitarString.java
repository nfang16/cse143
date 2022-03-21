//Nicholas Fang
//CSE143
//TA Ben Macmillian
//Program models a vibrating guitar of a given freq.

import java.util.*;

public class GuitarString{

    //class constant for the decay favtor
    public static final double DECAY_FACTOR = 0.996;
    
    //queue interface implemented to keep track of the ring buffer
    private Queue<Double> ringBuffer;
    //desired capacity of the elements to be stored
    private int capacity;
    
    //freq. must be greater than 0 and ring buffer size must be greater than 2,
    //otherwise throws an IllegalArgumentException; takes a freq. value as a param
    //constructs a string of the given frequency, creates a ring buffer of the desired capacity
    //rounded to the nearest int., and initializes it to represent a guitar string at rest
    public GuitarString(double frequency) {
        capacity = Math.round((int)(StdAudio.SAMPLE_RATE / frequency));
        
        if((frequency <= 0) || (capacity < 2)) {
            throw new IllegalArgumentException();
        }
        
        ringBuffer = new LinkedList<Double>();
        for(int i = 0; i < capacity; i++) {
            ringBuffer.add(0.0);
        }
    }
    
    //size must be 2 or more elements, otherwise throws an IllegalArgumentException
    //takes an array list of values as a param
    //constructs a string and initializes contents of the ring buffer to the values 
    //in the list
    public GuitarString(double[] init) {
        capacity = init.length;
        
        if(capacity < 2) {
            throw new IllegalArgumentException();
        }
        
        ringBuffer = new LinkedList<Double>();
        for(int i = 0; i < capacity; i++) {
            ringBuffer.add(init[i]);
        }
    }
    
    //Replaces the N elements in the ring buffer with random values -0.5<=value<0.5
    public void pluck() {
        for(int i = 0; i < capacity; i++) {
            ringBuffer.remove();
            ringBuffer.add(Math.random() - 0.5);
        }
    }
    
    //applies the karpus strong update once. removes the first value, takes the avg of the 
    //first 2 values multiplied by the decay factor, and enqueues that to the end of the list
    public void tic() {
        double firstValue = ringBuffer.remove();
        double secondValue = ringBuffer.peek();
        double avg = ((firstValue + secondValue) / 2.0) * DECAY_FACTOR;
        ringBuffer.add(avg);
    }
    
    //returns the current value at the beginning of the list
    public double sample() {
        return ringBuffer.peek();
    }
}