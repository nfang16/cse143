//Nicholas Fang
//CSE143
//Ben M.

//This program uses a dictionary to print all anagram phrases of a given word or phrase
//using recursive backtracing

import java.util.*;

public class AnagramSolver {

    private List<String> dictionary;
    private Map<String, LetterInventory> dictionaryCounts;
    
    //Initializes a new object that uses the given dictionary to create anagrams
    //of the words provided 
    public AnagramSolver(List<String> dictionary) {
        this.dictionary = dictionary;
        preprocessDictionary();
    }
    
    //Throws IllegalArgumentException if max is less than 0.
    //Finds combinations of words that have the same letters as the given string and
    //prints out all the different combinations of anagrams that include at most max words.
    //Builds up each answer one at a time and prints it out in dictionary order.
    public void print(String text, int max) {
        if(max < 0) {
            throw new IllegalArgumentException();
        }
        
        LetterInventory textCounts = new LetterInventory(text);
                                   
        List<String> smallerDict = prune(textCounts, dictionary);
        
        Stack<String> answerStack = new Stack<String>();
                                   
        print(textCounts, smallerDict, answerStack, max);
    }
    
    //Recursively prints out all of the anagrams
    //Keeps tracks of the different combinations of anagrams                       
    private void print(LetterInventory textCounts, List<String> smallerDict, 
                                         Stack<String> answerStack, int max) {
        //If conditions met prints out the anagrams; textcounts empty AND max equal to 0
        //or <= answerStack           
        if(textCounts.isEmpty() && (answerStack.size() <= max || max == 0)) {
            System.out.println(answerStack);
        } else {                 
            for(String word : smallerDict) {

                LetterInventory wordLetterCount = new LetterInventory(word);
                LetterInventory subtractedLetterCount = textCounts.subtract(wordLetterCount);
                
            //prune the dictionary again if possible and creates a new list of words to be used                                                   
                List<String> smaller = prune(subtractedLetterCount, smallerDict);
                answerStack.push(word);
                print(subtractedLetterCount, smaller, answerStack, max);
                answerStack.pop();
                             
            }
        }
    }
    
    //Prunes or reduces the size of the dictionary given a word or a phrase. Once words not
    //needed have been eliminated, a new smaller list containing the relevant words is returned
    private List<String> prune(LetterInventory textCounts, List<String> dictionary) {
                     
        List<String> smallerDict = new ArrayList<String>();
        
        for(String word : dictionary) {
        
            LetterInventory wordLetterCount = new LetterInventory(word);
            LetterInventory subtractedLetterCount = textCounts.subtract(wordLetterCount);
            
            if(subtractedLetterCount != null) {
                smallerDict.add(word);
            }
        }
        return smallerDict;
    }
    
    //Preprocess dictionary by storing words and letters into new dictionary
    private void preprocessDictionary() {
        dictionaryCounts = new HashMap<String, LetterInventory>();

        for(String word : dictionary) {
            LetterInventory wordCount = new LetterInventory(word);
            dictionaryCounts.put(word, wordCount);
        }    
    }

}

