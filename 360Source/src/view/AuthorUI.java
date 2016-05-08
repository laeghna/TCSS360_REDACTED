/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;

/** 
 * Class that provides the UI menus for an Author who has submitted a manuscript. 
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
 */
public class AuthorUI {
    
    /** The name of the conference. */
    private String myConference;
    
    /** The name of the user. */
    private String myName;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    /** Constructs an AuthorUI object. */
    public AuthorUI(final String theConference, final String theName,
                    final ArrayList<Manuscript> theManuscripts) {
        myConference = theConference;
        myName = theName;
        myManuscripts = theManuscripts;
        mySelection = 0;
    }
    
    /** Prints out the header information. */
    public void printHeader() {
        System.out.println();
        System.out.println(myConference);
        System.out.println("User: " + myName);
    }

    /**
     * Displays the main menu selections.
     */
    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        printHeader();
        System.out.println(" Author Options");
        System.out.println(" ---------------");
        System.out.println(" 1) Submit a Manuscript");
        System.out.println(" 2) View My Submitted Manuscripts");
        System.out.println(" 3) Make Changes to a Submitted Manuscript");
        System.out.println(" 4) Unsubmit a Manuscript");
        System.out.println(" 5) Logout");
        System.out.println();
        do {
            System.out.println("\nPlease enter a selection: ");
            try {
                mySelection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            }
            if (mySelection < 1 || mySelection > 5)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while (mySelection < 1 || mySelection > 5);
        scanner.close();
        switch(mySelection) {
            case 1:
                displaySubmitManuscriptMenu();
                break;
            case 2:
                displayViewMyManuscriptsMenu();
                break;
            case 3:
                displayMakeManuscriptChangesMenu();
                break;
            case 4:
                displayUnsubmitManuscriptMenu();
                break;
            case 5:
                //Code here?
                break;
        }
    }
    
    public void displaySubmitManuscriptMenu() {
        String filename = "";
        Scanner scanner = new Scanner(System.in);
        printHeader();
        System.out.println("\nPlease enter the complete pathname for the Manuscript to be submitted: ");
        filename = scanner.nextLine();
        scanner.close();
        try {
            //Code here
        } catch (Exception e) { //Can change the exception one known what type will be thrown
            System.out.println("Invalid pathname. File could not be found.");
            displayMainMenu();
        }
        System.out.println("Manuscript submitted successfully.");
        displayMainMenu();
    }
    
    public void displayViewMyManuscriptsMenu() {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        String input = "";
        printHeader();
        printManuscriptsNumberedList(counter);
        do {
            System.out.println("\nPlease enter a selection: ");
            input = scanner.nextLine(); 
            if (input != "a" && input != "b") {
                System.out.println("Invalid entry. Must enter a valid corresponding letter value.");
            }
        } while (input != "a" && input != "b");
        if (input == "a") {
            displayMainMenu();
        } else if (input == "b") {
            //Code here
        }
        scanner.close();
    }
    
    public void displayMakeManuscriptChangesMenu() {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        String input = "";
        printHeader();
        printManuscriptsNumberedList(counter);
        do {
            System.out.println("\nPlease enter a selection: ");
            input = scanner.nextLine(); 
            if (input != "a" && input != "b") {
                System.out.println("Invalid entry. Must enter a valid corresponding integer or letter value.");
            }
        } while (input != "a" && input != "b");
        if (input == "a") {
            displayMainMenu();
        } else if (input == "b") {
            //Code here
        }
        scanner.close();
    }
    
    public void displayUnsubmitManuscriptMenu() {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        String input = "";
        printHeader();
        printManuscriptsNumberedList(counter);
        do {
            System.out.println("\nPlease enter a selection: ");
            input = scanner.nextLine(); 
            if (input != "a" && input != "b") {
                System.out.println("Invalid entry. Must enter a valid corresponding integer or letter value.");
            }
        } while (input != "a" && input != "b");
        if (input == "a") {
            displayMainMenu();
        } else if (input == "b") {
            //Code here
        }
        scanner.close();
    }
    
    /** Helper method that prints the list of Manuscripts. */
    private int printManuscriptsNumberedList(final int counter) {

        System.out.println(" Submitted Manuscripts");
        System.out.println(" ----------------------");
        for( Manuscript paper : myManuscripts ) {
            System.out.println(" \"" + ++counter + ") " + paper.getTitle() + "\"");
            System.out.println();
        }
        System.out.println(" Options");
        System.out.println(" --------");
        System.out.println(" a) Back");
        System.out.println(" b) Logout");
        System.out.println();
        return counter;
    }
}
