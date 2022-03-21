//Nicholas Fang
//CSE143
//TA Ben Macmillian

//Program keeps tracks of players in a game of Assassin

import java.util.*;

public class AssassinManager {

    //List containing names of people still alive
    private AssassinNode killRing;
    //List containing names of people killed
    private AssassinNode graveYard;

    //Thows IllegalArgumentException if the list is empty
    //Constructs a new assassin manager to keep track of who is being stalked/killed
    //Takes in a list of names in order and placed into a kill ring
    public AssassinManager(List<String> names) {
        if(names.size() == 0) {
            throw new IllegalArgumentException();
        }
        
        for(int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            AssassinNode assassin = new AssassinNode(name);
            
            if(killRing == null) {
                killRing = assassin;
            } else {
                AssassinNode current = killRing;
                while(current.next != null) {
                    current = current.next;
                }
                current.next = assassin;
            }
        }
    }
    
    //Prints the names of the people who are still alive and playing
    //When there is one person left(winner), printed that he/she is stalking self
    public void printKillRing() {
        AssassinNode current = killRing;
        while(current.next != null) {
            System.out.println("    " + current.name + " is stalking " + current.next.name); 
            current = current.next;
        } 
        System.out.println("    " + current.name + " is stalking " + killRing.name);
        
    }
    
    //Prints the names of the people who have been killed in the game
    //Names of the people who were killed printed in reverse order of which they died
    //No output produced if the list is empty
    public void printGraveyard() {
        AssassinNode current = graveYard;
        while(current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }
    
    //Takes in a name(case insensitive) from the text file and
    //Returns whether or not that person is still in the kill ring
    public boolean killRingContains(String name) {
        AssassinNode current = killRing;
        return containsName(current, name);
    }
    
    //Takes in a name(case insensitive) from the text file and 
    //returns whether or not that person has been killed and in the grave
    public boolean graveyardContains(String name) {
        AssassinNode current = graveYard;
        return containsName(current, name);
    }
    
    //private method to elim. redundancy in killRingContains and graveYardContains
    //Takes a name(case insensitive) and checks if it is in the list given
    private boolean containsName(AssassinNode current, String name) {
        while(current != null) {
            if(current.name.equalsIgnoreCase(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    //Checks whether or not the game has finished
    public boolean gameOver() {
        return (killRing.next == null);
    }
    
    //Returns the name of the last man standing or null if game is not over
    public String winner() {
        if(!gameOver()) {
            return null;
        }
        return killRing.name;
    }
    
    //Throws an IllegalStateException if the game is over; otherwise if the name 
    //given is not part of the kill ring, throws an IllegalArgumentException. If both
    //conditions are met, the game over exception takes precedence
    //Records the assassination of person of the given name (case insensitive), 
    //transferring the person from the kill ring to the front of the grave yard.
    public void kill(String name) {
        if(gameOver()) {
            throw new IllegalStateException();
        }
        if(!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        
        AssassinNode current = killRing;
        AssassinNode graveCurr = graveYard;
        if(current.name.equalsIgnoreCase(name)) {
            graveCurr = current;
            while(current.next != null) {
                current = current.next;
            }
            killRing = killRing.next;
        } else {
            while(!current.next.name.equalsIgnoreCase(name)) {
                current = current.next;
            }
            graveCurr = current.next;
            current.next = current.next.next;
        }
        graveCurr.killer = current.name;
        graveCurr.next = graveYard;
        graveYard = graveCurr;
        
    }

}