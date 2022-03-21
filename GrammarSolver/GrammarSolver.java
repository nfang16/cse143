//Nicholas Fang
//CSE143
//Ben M.

//This program generates random sentences given a set of rules 

import java.util.*;

public class GrammarSolver {

    //Created to store keys and values generated
    private SortedMap<String, String[]> grammarRules;
    
    //IllegalArgumentException thrown if list is empty or if there are 2 or more 
    //entries in the grammar for the same non-terminal.
    //Initializes a new grammar over the given BNF grammar rules where each rule 
    //corresponds to one line of text. 
    public GrammarSolver(List<String> rules) {
        if(rules.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammarRules = new TreeMap<String, String[]>();
        
        for(String s : rules) {
            String[] line = s.split("::=");                     //split into entry and rules
            String nonterminals = line[0];                      //non-terminals
            if(grammarRules.containsKey(nonterminals)) {
                throw new IllegalArgumentException();
            }
            String[] allRules = line[1].trim().split("[|]");    //seperate rules, terminals
            grammarRules.put(nonterminals, allRules);           //(non-terminal, terminal)
        }
    }
    
    //Returns true if given symbol is a non-terminal in the grammar and false otherwise
    public boolean grammarContains(String symbol) {
        return grammarRules.containsKey(symbol);                //non-terminal in key
    }
    
    //Returns a string representation of the various non-terminal symbols from the 
    //grammar as a comma seperated list in square brackets
    public String getSymbols() {
        return grammarRules.keySet().toString();
    }
    
    //Throws an IllegalArgumentException if times is negative or if the string argument
    //passed is not a non-terminal in grammar
    //Generates "times" random occurrences of the given symbol and returns it in an
    //array of strings. Equal probability when generating a nonterminal sybmol in grammar
    public String[] generate(String symbol, int times) {
        if(times < 0 || !grammarContains(symbol)) {
            throw new IllegalArgumentException();
        }
        
        String[] grammarString = new String[times];
        for(int i = 0; i < times; i++) {
            grammarString[i] = makeString(symbol);
        }
        return grammarString;
    }
    
    //Uses recursion to generate the finalGrammar string. Goes through each rule
    //to determine whether it is a nonterminal or not, and prints it out to the final
    //string if string is a terminal. Returns the final string 
    private String makeString(String symbol) {
        String finalGrammar = "";

        String[] rules = grammarRules.get(symbol);
        //int rand = (int) (Math.random() * rules.length);  //Create rand vars.
        Random rand = new Random();
        int rand1 = rand.nextInt(rules.length);
        String[] parts = rules[rand1].trim().split("\\s+");        //Splits and chooses rule
        
        for(String word : parts) {
            if(grammarRules.containsKey(word)) {            
                finalGrammar += makeString(word);           //Recursive case if nonterm
            } else {
                finalGrammar += word + " ";
            }
        }
        return finalGrammar;
        
    }
    
    
}