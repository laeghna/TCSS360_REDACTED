/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.Conference;
import model.Manuscript;
import model.RegisteredUser;
import model.Review;
import model.Reviewer;
import enums.PageStatus;

/** 
 * Class that provides the UI menus for a Reviewer. 
 * 
 * @author Lisa Taylor
 * @version 30 May 2016
 */
public class ReviewerUI {

    /** The name of the conference. */
    private Conference myConference;
    
    /** The name of the user. */
    private RegisteredUser myUser;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    /** Controls what the calling method does. */
    private PageStatus backCaller;
    
    /** Controls what to do based off the actions taken. */
    private PageStatus backCallee;
    
    private GeneralUI myParent;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    public ReviewerUI(final Conference theConference, final RegisteredUser me,
    				  final GeneralUI theParent) {
    	myParent = theParent;
        myConference = theConference;
        myUser = me;
        myManuscripts = theConference.getReviewersManuscripts(me.getUsername());
        mySelection = 0;
    }
    
    /** Prints out the header information. */
    private void printHeader() {
        
        System.out.println("MSEE CONFERENCE MANAGEMENT SYSTEM");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Conference: " + myConference.toString());
        System.out.println("Reviewer: " + myUser.getFullName());
    }
    
    /**
     * Displays the main menu selections.
     */
    public PageStatus displayMainMenu() {

        Scanner scanInput = new Scanner(System.in);
        boolean operationSuccess = false;
        String userInput = "";

        do {

            printHeader();
            System.out.println(" Reviewer Options"
                           + "\n -----------------"
                           + "\n 1> Submit Reviews");
            printSubMenuBackAndExit();

            do {
                System.out.print("Please enter a selection: ");
                userInput = scanInput.nextLine();
                if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                    
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    operationSuccess = false;
                } else {
                    
                    switch (userInput.charAt(0)) {
                    
                    case '1':
                        operationSuccess = true;
                        backCallee = displayManuscriptsMenu(scanInput);
                        break;
                    case 'b':
                        operationSuccess = true;
                        backCallee = PageStatus.EXIT; // Exit outer loop.
                        backCaller = PageStatus.BACK; // Tell calling method to hold.
                        break;
                    case 'e':
                        operationSuccess = true;
                        backCallee = PageStatus.EXIT; // Exit outer loop.
                        backCaller = PageStatus.EXIT; // Tell calling method to retire.
                        break;
                    default:
                        operationSuccess = false;
                        System.out.println("Invalid entry. Please enter a valid corresponding"
                                         + "integer or letter value."); 
                        break;
                    }
                }
            }while(!operationSuccess);
        }while(backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);

        return backCaller;
    }

    /**
     * Displays the list of manuscripts
     */
    private PageStatus displayManuscriptsMenu(Scanner stdin) {

        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;

        do {
            
            printHeader();
            printManuscriptsNumberedList(counter);
            printSubMenuBackAndExit();

            do {
                System.out.print("Please enter the corresponding number of the Manuscript to Review: ");
                userInput = stdin.nextLine();
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    mySelection = option;
                    backCallee = displaySubmitReviewsMenu(stdin);
                    if(backCallee == PageStatus.EXIT) {
                        backCaller = PageStatus.EXIT;
                    }
                }
                else if(userInput.charAt(0) == 'b') {
                    operationSuccess = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                }
                else if(userInput.charAt(0) == 'e') {
                    operationSuccess = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else {
                    operationSuccess = false;
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                }
            }while(!operationSuccess);
        } while (backCallee == PageStatus.BACK);

        return backCaller;
    }

    /**
     * Displays the review submission menu
     */
    private PageStatus displaySubmitReviewsMenu(Scanner stdin) {

        boolean operationSuccess = false;
        String userInput = "";

        do {

            System.out.println(" Submit Review for " + myManuscripts.get(mySelection).getTitle());
            System.out.println(" Enter filename below\n");
            printSubMenuBackAndExit();

            do {
                System.out.print("Enter filename of review or action: ");
                userInput = stdin.nextLine();

                if(userInput.length() > 0) {
                    operationSuccess = true;
                    Reviewer me = new Reviewer(myUser.getUsername());
                    myManuscripts.get(mySelection).addNewReview(me, new Review(me, userInput));
                    System.out.println(" Added " + userInput + " review.");
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else if(userInput.charAt(0) == 'b') {
                    operationSuccess = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                }
                else if(userInput.charAt(0) == 'e') {
                    operationSuccess = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else {
                    operationSuccess = false;
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                }
            }while(!operationSuccess);
        }while(backCallee == PageStatus.BACK);

        return backCaller;
    }
    
    /** Helper method that prints the list of Manuscripts. */
    private int printManuscriptsNumberedList(int counter) {

        counter = 0;
        System.out.println("\n Assigned Manuscripts"
                         + "\n ---------------------");
        
        for( Manuscript man : myManuscripts ) {
            System.out.println("\n " + ++counter + ") \"" + man.getTitle() + "\"");
        }

        return counter;
    }
    
    private void printSubMenuBackAndExit() {
        
        System.out.println("\n --"
                         + "\n b> Back"
                         + "\n e> Exit/Logout"
                         + "\n");
    }
}
