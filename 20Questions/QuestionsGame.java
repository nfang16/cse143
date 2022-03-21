//Nicholas Fang
//CSE143
//Ben M.
//This program...
//creates a guessing guessing based on binary trees. The game utilizes a system of 
//yes and no questions in order to discover what the player has guessed. If the 
//computer guesses wrongly initially, it will learn and add that guess into its
//possible list of answers to choose from.

import java.util.*;
import java.io.*;

public class QuestionsGame {
    // Your code here
    //root of the tree
    private QuestionNode overallRoot;
    //scanner used to read in files
    private Scanner console;
    
    //constructs a new game with one node
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }
    
    //replaces the current tree with data from an input file
    public void read(Scanner input) {
        overallRoot = readTreeHelper(input);
    }
    
    //private helper method that reads through each line from the input file
    //and constructs a new tree from given data file
    private QuestionNode readTreeHelper(Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        QuestionNode root = new QuestionNode(data);
        
        if(type.contains("Q:")) {
            root.leftYes = readTreeHelper(input);
            root.rightNo = readTreeHelper(input);
        } 
        
        return root;
    }
    
    //Stores the current question tree to an output file.
    public void write(PrintStream output) {
        write(overallRoot, output);
    }
    
    //private helper method used to store the contents of the tree into an
    //input file
    private void write(QuestionNode currentRoot, PrintStream output) {
        if(currentRoot.leftYes == null || currentRoot.rightNo == null) {
            output.println("A:");
            output.println(currentRoot.data);
        } else {
            output.println("Q:");
            output.println(currentRoot.data);
            write(currentRoot.leftYes, output);
            write(currentRoot.rightNo, output);
        }
    }
    
    //Asks the user a series of yes and no questions in order to create a guess
    //or answer that the user is thinking of. If computer guesses incorrectly, then
    //it will learn a new answer and incorporate that into the next game being played
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }
    
    //private helper method used to ask the user the series of yes and no questions
    //If the computer guesses incorrectly, it will ask the user to specify what the user
    //guessed and add that to its bank of possible answers. It will then ask the user
    //questions in order to reach the new node.
    private QuestionNode askQuestions(QuestionNode question) {
        if(question.leftYes == null || question.rightNo == null) {
            if(yesTo("Would your object happen to be " + question.data + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                question = newQuestion(question);
            } 
        } else {
            if(yesTo(question.data)) {
                question.leftYes = askQuestions(question.leftYes);
            } else {
                question.rightNo = askQuestions(question.rightNo);
            }
        }
        return question;
    }
    
    //another private helper method that asks the user the specify a new guess to add
    //to the tree. updates the tree with new questions and prompts to reach that answer
    private QuestionNode newQuestion(QuestionNode current) {
        System.out.print("What is the name of your object? ");
        QuestionNode answer = new QuestionNode(console.nextLine());
        System.out.println("Please give me a yes/no question that");
        System.out.println("distinguishes between your object");
        System.out.print("and mine--> ");
        String newQuestion = console.nextLine();
        
        if(yesTo("And what is the answer for your object?")) {
            return new QuestionNode(newQuestion, answer, current);
        }
        return new QuestionNode(newQuestion, answer, current);
    }
    
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }

    private static class QuestionNode {
        // Your code here
        public String data;
        public QuestionNode leftYes; //ref. to left subtree
        public QuestionNode rightNo; //ref. to right subtree
        
        ///constructs a leaf node with given data 
        private QuestionNode(String data) {
            this(data, null, null);
        }
        
        //Constructs a leaf or branch node with the given data and links 
        private QuestionNode(String data, QuestionNode left, QuestionNode right) {
            this.data = data;
            this.leftYes = left;
            this.rightNo = right;
        }
    }
}
