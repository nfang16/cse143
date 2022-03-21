//Nicholas Fang
//CSE143
//Ben M.

//This program compresses and decompresses files based on frequency of letters for a selected
//file

import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanNode root;
    
    //Initializes new huffman code object given an array containing the frequency of letters in
    //the text file
    public HuffmanCode(int[] frequencies) {
        HuffmanNode combined;
        HuffmanNode leftZero;
        HuffmanNode rightOne;
        PriorityQueue<HuffmanNode> tree = new PriorityQueue<HuffmanNode>();
    
        for(int i = 0; i < frequencies.length; i++) {
            if(frequencies[i] > 0) {
                tree.add(new HuffmanNode(i, frequencies[i]));
            }
        }
        
        while(tree.size() > 1) {
            leftZero = tree.remove();
            rightOne = tree.remove();
            combined = new HuffmanNode(-1, leftZero.getFrequency() + rightOne.getFrequency(), 
                                                         leftZero, rightOne);
            tree.add(combined);
        }
        root = tree.remove();
    }
    
    //Initializes a new object by reading in a file 
    public HuffmanCode(Scanner input) {
        while(input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String pathway = input.nextLine();
            this.root = this.HuffmanHelper(this.root, asciiValue, pathway);
        }
    }
    
    //Helper method used to construct the huffmanCode object using the current huffmanNode,
    //the asciiValue attached the the object, and the path or sequence of values which
    //leads to the attachment
    private HuffmanNode HuffmanHelper(HuffmanNode currentRoot, int asciiValue, String path) {
        if(currentRoot == null && path.isEmpty()) {
            return new HuffmanNode(asciiValue, -1);
        } else if(currentRoot == null) {
            currentRoot = new HuffmanNode(0, -1);
        }
        
        char nextPath = path.charAt(0);
        
        if(nextPath == '0') {
            currentRoot.leftZero = this.HuffmanHelper(currentRoot.leftZero, asciiValue, 
                                                        path.substring(1));
        } else {
            currentRoot.rightOne = this.HuffmanHelper(currentRoot.rightOne, asciiValue, 
                                                        path.substring(1));
        }
        return currentRoot;
    }
    
    //stores the current huffman codes to a given output stream in standard format
    public void save(PrintStream output) {
        this.save(output, root, "");
    }
    
    //helper method which saves the current state of the huffman code at its current instance
    //and the path or sequence of values which leads to the that instance, to a new file
    private void save(PrintStream output, HuffmanNode currentRoot, String path) {
        if(currentRoot != null) {
            if(currentRoot.leftZero == null && currentRoot.rightOne == null) {
                output.println(currentRoot.asciiValue);
                output.println(path);
            } else {
                this.save(output, currentRoot.leftZero, path + "0");
                this.save(output, currentRoot.rightOne, path + "1");
            }
        }
    }
    
    //decrompresses the data and reads individual bits from the given input and writes the 
    //corresponding characters after translation to the output. stops reading when the
    //input is empty
    public void translate(BitInputStream input, PrintStream output) {
        while(input.hasNextBit()) {
            this.translate(input, output, root);
        }
    }
    
    //helper method which helps to translate the compressed object back to the original
    //decompressed file containing the original data given an input
    private void translate(BitInputStream input, PrintStream output, HuffmanNode currentRoot) {
        if(currentRoot != null) {
            if(currentRoot.leftZero == null && currentRoot.rightOne == null) {
                output.write(currentRoot.asciiValue);
            } else if(input.nextBit() == 0) {
                translate(input, output, currentRoot.leftZero);
            } else {
                translate(input, output, currentRoot.rightOne);
            }
        }
    }
    
    //private inner class which implements comparable
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        public HuffmanNode leftZero;
        public HuffmanNode rightOne;
        public int frequency;
        public int asciiValue;
        
        public HuffmanNode(int asciiValue, int frequency) {
            this(asciiValue, frequency, null, null);
        }
        
        public HuffmanNode(int asciiValue, int frequency, HuffmanNode left, HuffmanNode right) {
            this.asciiValue = asciiValue;
            this.frequency = frequency;
            this.leftZero = left;
            this.rightOne = right;
        }
        
        public int getFrequency() {
            return frequency;
        }
        
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }

}