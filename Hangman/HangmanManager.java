//Nicholas Fang
//CSE143
//Ben MacMillian
//This program manages the state of a game of hangman

import java.util.*;

public class HangmanManager {

    //set of words
    private SortedSet<String> words;
    //set of guessed letters 
    private SortedSet<Character> guessedLetters;
    //max number of guesses allowed 
    private int max;
    //current pattern of the word
    private String pattern;
    //counts the number of incorrect guesses
    private int incorrectGuesses;
    
    
    //If the word of given length is less than 1 or if the max number of guesses is less
    //than 1, throws an IllegalArgumentException
    //Passed a dictionary of words, a target word length, and a max number of wrong guesses
    //Creates a game of evil hangman
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        
        words = new TreeSet<String>();
        guessedLetters = new TreeSet<Character>();
        
        this.max = max;
        int incorrectGuesses = 0;
        
        if(length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        
        pattern = "-";
        for(int i = 1; i < length; i++) {
            pattern += " -";
        }
        
        for(String word : dictionary) {
            if(word.length() == length) {
                words.add(word);
            }
        }
    }
    
    //returns the current set of words to be considered
    public Set<String> words() {
        return words;
    }
    
    //returns number of guesses the player has left
    public int guessesLeft() {
        return max - incorrectGuesses;
    }
    
    //returns the set of words that have already been guessed
    public Set<Character> guesses() {
        return guessedLetters;
    }
    
    //if the word set is empty, throws an IllegalStateException
    //returns the current pattern of the word to be guesses,
    //dashes where word has not been guessed, character where a correct guess has been made
    public String pattern() {
        if(words.isEmpty()) {
            throw new IllegalStateException();
        }
        return pattern;
    }
    
    //If the number of guesses left is less than 1 or if the word set is empty, throws an 
    //IllegalStateException; otherwise if the character being guessed was guessed previously,
    //throws an IllegalArgumentException
    //Records the players guess. Once guess has been made, a set of words will be chosen 
    //according to which set is the largest. Returns the number of occurrences of the guess
    //and updates the number of guessess left
    public int record(char guess) {
        if(guessesLeft() < 1 || words.isEmpty()) {
            throw new IllegalStateException();
        }
        if(guessedLetters.contains(guess)) {
            throw new IllegalArgumentException();
        }
        guessedLetters.add(guess); //adds character to the TreeSet
        
        Map<String, SortedSet<String>> wordFamily = new TreeMap<String, SortedSet<String>>();
        
        makeFamily(guess, wordFamily);
        pickPattern(wordFamily);
        
        if(!pattern.contains(guess + "")) {
            incorrectGuesses++;
        }
        return countOccur(guess);
    }
    
    //creates the family of words to be considered as the game of hangman is being played,
    //matches and maps the pattern that to the corresponding word family (words in the set).
    private void makeFamily(char guess, Map<String, SortedSet<String>> wordFamily) {
        for(String word : words) {
            String patternFamily = pattern;
            
            for(int i = 0; i < word.length(); i++) { //goes through each word
                if(word.charAt(i) == guess) {
                    patternFamily = patternFamily.substring(0, i * 2) + guess +
                                    patternFamily.substring(i * 2 + 1);
                }
            }
            
            if(!wordFamily.containsKey(patternFamily)) {
                wordFamily.put(patternFamily, new TreeSet<String>());
            }
            wordFamily.get(patternFamily).add(word);
        }
    }
    
    //picks the largest family of words given all the different pattern sets
    //narrows down the list of words to be considered once the family is chosen
    private void pickPattern(Map<String, SortedSet<String>> wordFamily) {
        int largest = 0;
        for(String patternFam : wordFamily.keySet()) {
            if(wordFamily.get(patternFam).size() > largest) {
                largest = wordFamily.get(patternFam).size();
                words = wordFamily.get(patternFam);
                pattern = patternFam;
            }
        }
    }
    
    //retruns the number of occurences of the guessed letter in a new pattern
    private int countOccur(char guess) {
        int count = 0;
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) == guess) {
                count++;
            }
        }    
        return count;
    }
}