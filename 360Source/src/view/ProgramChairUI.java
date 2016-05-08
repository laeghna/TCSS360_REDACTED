/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;

/** 
 * Class that provides the UI menus for a Program Chair. 
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
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
    
    /** 
     * Constructs a ProgramChair object. 
     * 
     * @param theConference the Conference object to be stored
     * @param theName the user's name
     * @param theManuscripts the list of Manuscripts
     */
    public ProgramChairUI (final String theConference, final String theName,
                         final ArrayList<Manuscript> theManuscripts) {
        myConference = theConference;
        myName = theName;
        myManuscripts = theManuscripts;
        mySelection = 0;
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
        Scanner scanner = new Scanner(System.in);
        printHeader();
        System.out.println(" /-------------------------------------------\\");
        System.out.println("| Program Chair Options                       |");
        System.out.println("| ----------------------                      |");
        System.out.println("| 1) View Submitted Manuscripts               |");
        System.out.println("| 2) Designate Subprogram Chair               |");
        System.out.println("| 3) Assign Manuscripts to Subprogram Chairs  |");
        System.out.println("| 4) Logout                                   |");
        System.out.println(" \\-------------------------------------------/)");
        do {
            System.out.println("\nPlease enter a selection: ");
            try {
                mySelection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            }
            if (mySelection < 1 || mySelection > 4)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
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
        Scanner scanner = new Scanner(System.in);
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
                mySelection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            }
            if (mySelection < 1 || mySelection > 2)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while (mySelection < 1 || mySelection > 2);
        scanner.close();
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
