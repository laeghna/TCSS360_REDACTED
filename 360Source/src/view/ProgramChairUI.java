/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import model.ProgramChair;
import model.RegisteredUser;

/** 
 * Class that provides the UI menus for a Program Chair. 
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
 */
public class ProgramChairUI {
    
    /** The Conference object. */
    private Conference myConference;
    
    /** The name of the user. */
    private String myName;
    
    /** Maps each registered user's unique username to its corresponding RegisteredUser object. */
    private HashMap<String, RegisteredUser> registeredUsers;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    /** 
     * Constructs a ProgramChair object. 
     * 
     * @param theConference the Conference object to be stored
     * @param theName the user's name
     * @param theManuscripts the list of Manuscripts
     */
    public ProgramChairUI (final Conference theConference, final String theName, 
                           final HashMap<String, RegisteredUser> regUsers) {
        myConference = theConference;
        myName = theName;
        registeredUsers = regUsers;
        mySelection = 0;
    }
    
    /** Prints out the header information. */
    public void printHeader() {
        System.out.println(myConference.toString());
        System.out.println("Program Chair: " + myName);
    }
    
    /**
     * Displays the main menu selections.
     */
    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        printHeader();
        System.out.println(" Program Chair Options");
        System.out.println(" ----------------------");
        System.out.println(" 1) View Submitted Manuscripts");
        System.out.println(" 2) Designate Subprogram Chair");
        System.out.println(" 3) Assign Manuscripts to Subprogram Chairs");
        System.out.println(" 4) Logout");
        System.out.println();
        do {
            System.out.println("Please enter a selection: \n");
            try {
                mySelection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            }
            if (mySelection < 1 || mySelection > 4)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while (mySelection < 0 || mySelection > 4);
        scanner.close();
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
                //Code here?
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
        System.out.println(" Submitted Manuscripts");
        System.out.println(" ----------------------");
        for( Manuscript paper : myManuscripts ) {
            System.out.println("\"" + paper.getTitle() + "\"");
            System.out.println("        Author: " + paper.getAuthor());
            System.out.println();
        }
        System.out.println(" Options");
        System.out.println(" --------");
        System.out.println(" 1) Back");
        System.out.println(" 2) Logout");
        System.out.println();
        do {
            System.out.println("Please enter a selection: \n");
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
                //Code here?
                break;
        }
    }
    
    /**
     * Displays
     */
    private void displayDesignateSubprogramChairMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        ProgramChair myRole = myConference.getProgramChair();
        Iterator<String> it = registeredUsers.keySet().iterator();
        int counter = 0;
        if (myRole.getSPCS().size() > 0) {
            ArrayList<String> subPCs = myRole.getSPCS();
            System.out.println(" Subprogram Chairs");
            System.out.println(" ------------------");
            for (String uname : subPCs) {
                System.out.println(" Username: " + uname);
                System.out.println("     Name: " + registeredUsers.get(uname).getName());
                System.out.println();
            }
        }
        System.out.println("Registered Users");
        System.out.println("-----------------");
        while (it.hasNext()) {
            String uname = it.next();
            counter = 0;
            System.out.println(" Username: " + uname);
            System.out.println("     Name: " + registeredUsers.get(uname).getName());
            System.out.println();
        }
        do {
            System.out.println("Please enter the username of the Registered User to assign the role");
            System.out.println("of Subprogram Chair: \n");
            input = scanner.nextLine();
            if (!registeredUsers.containsKey(input))
                System.out.println("Invalid username entered.");
        } while (!registeredUsers.containsKey(input));
        
        myRole.addSPC(input);
        myConference.assignSubprogramChair(registeredUsers.get(input));
        
        System.out.println(registeredUsers.get(input) + " successfully assigned as Subprogram Chair.");
        System.out.println("Assign another Subprogram Chair? (y/n: \n");
        input = scanner.nextLine();
        scanner.close();
        if (input == "y") {
            System.out.println();
            displayDesignateSubprogramChairMenu();
        } else if (input == "n") {
            System.out.println("Returning to Main Menu.");
            System.out.println();
            displayMainMenu();
        }
    }
    
    private void displayAssignManuscriptsMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        ProgramChair myRole = myConference.getProgramChair();
        if (myRole.getSPCS().isEmpty()) {
            System.out.println("No Subprogram Chairs have been assigned. Returning to Main Menu.");
            System.out.println();
            break;
        }
        ArrayList<String> subPCs = myRole.getSPCS();
        System.out.println(" Subprogram Chairs");
        System.out.println(" ------------------");
        for (String uname : subPCs) {
            System.out.println(" Username: " + uname);
            System.out.println("     Name: " + registeredUsers.get(uname).getName());
            System.out.println();
        }
        do {
            System.out.println("Please enter the username of the Subprogram Chair to assign");
            System.out.println("a Manuscript to: \n");
            input = scanner.nextLine();
            if (!myRole.getSPCS().contains(input)) {
                System.out.println("Invalid username entered.");
                System.out.println();
            }
        } while (!myRole.getSPCS().contains(input));
        System.out.println("Manuscript will be assigned to " + registeredUsers.get(input).getName());
        int counter = 0;
        System.out.println(" Submitted Manuscripts");
        System.out.println(" ----------------------");
        ArrayList<Manuscript> papers = myConference.getManuscripts();
        for( Manuscript paper : papers ) {
            System.out.println(" \"" + ++counter + ") " + paper.getTitle() + "\"");
            System.out.println();
        }
        do {
            System.out.println("Enter the number of the Manuscript to be assigned: \n");
            try {
                mySelection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            }
            if (mySelection < 1 || mySelection > counter)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while (mySelection < 1 || mySelection > counter);
        myConference.getSubProgramChair().addManuscript(papers.get(mySelection - 1));
        System.out.println("Paper successfully assigned.");
        System.out.println("Returning to Main Menu");
        scanner.close();
        displayMainMenu();
    }
}
