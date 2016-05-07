package view;

import java.util.ArrayList;
import java.util.Scanner;

/** 
 * Class that provides the functionality for a Program Chair. 
 * 
 * @author Lisa Taylor
 * @version 6 May 2016
 */
public class ProgramChairUI {
    
    /** The name of the conference. */
    private String myConference;
    
    /** The name of the user. */
    private String myName;
    
    /** The list of manuscripts in a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    /** Scanner for reading input. */
    private Scanner myScanner;
    
    /** 
     * Constructs a ProgramChair object. 
     * 
     * @param theConference the Conference object to be stored
     * @param fname the user's first name
     * @param lname the user's last name
     */
    public ProgramChairUI (final String theConference, final String theName,
                         final ArrayList<Manuscript> theManuscripts) {
        myConference = theConference;
        myName = theName;
        myManuscripts = theManuscripts;
        mySelection = 0;
        myScanner = new Scanner(System.in);
    }
    
    /** Prints out the header information. */
    public void printHeader() {
        System.out.println(myConference);
        System.out.println("Program Chair: " + myName);
    }
    
    /**
     * Displays the main menu selections.
     */
    public void displayMainMenu() {
        printHeader();
        System.out.println(" /-------------------------------------------\\");
        System.out.println("| Home Screen Options                         |");
        System.out.println("| --------------------                        |");
        System.out.println("| 1) View Submitted Manuscripts               |");
        System.out.println("| 2) Designate Subprogram Chair               |");
        System.out.println("| 3) Assign Manuscripts to Subprogram Chairs  |");
        System.out.println("| 4) Logout                                   |");
        System.out.println(" \\-------------------------------------------/)");
        do {
            System.out.println("\nPlease enter a selection: ");
            try {
                mySelection = Integer.parseInt(myScanner.nextLine());
            } catch (InvalidEntryException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer. ");
            }
        } while (mySelection < 0 || mySelection > 4);
        switch(mySelection) {
            case 1:
                displaySubmittedManuscriptsMenu();
                break;
            case 2:
                displayDesignateSubprogramChairMenu();
                break;
            case 3:
                displayAssignManuscriptsMenu();
                break;
            case 4:
                break;
        }
    }
    
    /**
     * Displays submitted manuscripts and their respective authors.
     * Also provides menu options to go back or logout.
     */
    private void displaySubmittedManuscriptsMenu() {
        printHeader();
        System.out.println(" /-----------------------------------------------------------------\\");
        System.out.println("| Submitted Manuscripts                                             |");
        System.out.println("| ----------------------                                            |");
        for(int i = 0; i < myManuscripts.size(); i++) {
            System.out.format("| \"1) %61s |", myManuscripts[i].getTitle());
            System.out.format("|       \"Author: %50s |", myManuscripts[i].getAuthor());
            System.out.format("| \"%64s |");
        }
        System.out.println("| Options                                                           |");
        System.out.println("| --------                                                          |");
        System.out.println("| 1) Back                                                           |");
        System.out.println("| 2) Logout                                                         |");
        System.out.println(" \\-----------------------------------------------------------------/)");
        do {
            System.out.println("\nPlease enter a selection: ");
            try {
                mySelection = Integer.parseInt(myScanner.nextLine());
            } catch (InvalidEntryException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer. ");
            }
        } while (mySelection < 0 || mySelection > 4);
        switch(mySelection) {
            case 1:
                displayMainMenu();
                break;
            case 2:
                break;
        }
    }
    
    /**
     * Displays
     */
    private void displayDesignateSubprogramChairMenu() {
        
    }
    
    private void displayAssignManuscriptsMenu() {
        
    }
}
