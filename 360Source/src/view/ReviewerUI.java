/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

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
 * @version 7 May 2016
 */
public class ReviewerUI {

    /** The name of the conference. */
    private Conference myConference;
    
    /** The name of the user. */
    private RegisteredUser mySelf;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    private GeneralUI myParent;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    public ReviewerUI(final Conference theConference, final RegisteredUser me,
    				  GeneralUI theParent) {
    	myParent = theParent;
        myConference = theConference;
        mySelf = me;
        myManuscripts = getMyManuscripts();
        mySelection = 0;
    }
    /* Gets all of the manuscripts associated with this
     * user for this conference.
     */
    private ArrayList<Manuscript> getMyManuscripts() {
    	
    	ArrayList<Manuscript> res = new ArrayList<Manuscript>();
    	
    	for(Manuscript man : myConference.getManuscripts()) {
    		
    		if(man.getReveiwersUsernames().contains(mySelf.getUsername())) {
    			
    			res.add(man);
    		}
    	}
    	return res;
    }
    
    /** Prints out the header information. */
    public void printHeader() {
        System.out.println();
        System.out.println(myConference);
        System.out.println("User: " + mySelf.toString());
    }
    
    /**
     * Displays the main menu selections.
     * author Anh Tran
     * version May 27 2016
     */
    public PageStatus displayMainMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";

        do {
            boolean opSucc = false;

            printHeader();
            stdout.println(" Reviewer Options");
            stdout.println(" ----------------------");
            stdout.println(" 1> Submit Reviews\n");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

            /*
            //Not needed. Validation will be done in switch statement.
            do {
                System.out.println("\nPlease enter a selection: ");
                try {
                    mySelection = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Entry. Must enter a valid corresponding integer. ");
                }
                if (mySelection < 1 || mySelection > 2)
                    System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            } while (mySelection < 0 || mySelection > 2);
            scanner.close();
            */

            do {
                stdout.print("Select an action: ");
                userInput = myScanner.nextLine();
                switch (userInput.charAt(0)) {
                    case '1':
                        opSucc = true;
                        backCallee = displayManuscriptsMenu();
                        break;
                    case 'b':
                        opSucc = true;
                        backCallee = PageStatus.EXIT; // Exit outer loop.
                        backCaller = PageStatus.BACK; // Tell calling method to hold.
                        break;
                    case 'e':
                        opSucc = true;
                        backCallee = PageStatus.EXIT; // Exit outer loop.
                        backCaller = PageStatus.EXIT; // Tell calling method to retire.
                        break;
                    default:
                        opSucc = false;
                        stdout.println("Invalid option, try again.");
                        break;
                }
            }while(!opSucc);
        }while(backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);

        return backCaller;
    }

    /**
     * Displays the list of manuscripts
     * author Anh Tran
     * version May 27 2016
     */
    public PageStatus displayManuscriptsMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";
        int counter = 0;

        do {
            boolean opSucc = false;

            stdout.println(" Assigned Manuscripts");
            stdout.println(" ---------------------");
            for (Manuscript paper : myManuscripts) {
                stdout.println(" \"" + ++counter + ") " + paper.getTitle() + "\"");
            }
            stdout.println(" ------------------------------\n");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

            /*
            //No need. Validation is done in next do-while loop.
            do {
                System.out.println("Enter the number of the Manuscript to be assigned: ");
                try {
                    mySelection = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
                }
                if (mySelection < 1 || mySelection > counter)
                    System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
            } while (mySelection < 1 || mySelection > counter);
            */

            do {
                stdout.print("Select manuscript to submit review for: ");
                userInput = myScanner.nextLine();
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    mySelection = option;
                    backCallee = displaySubmitReviewsMenu();
                    if(backCallee == PageStatus.EXIT) {
                        backCaller = PageStatus.EXIT;
                    }
                }
                else if(userInput.charAt(0) == 'b') {
                    opSucc = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                }
                else if(userInput.charAt(0) == 'e') {
                    opSucc = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else {
                    opSucc = false;
                    stdout.println("Invalid option, try again.");
                }
            }while(!opSucc);
        } while (backCallee == PageStatus.BACK);

        return backCaller;
    }

    /**
     * Displays the review submission menu
     * author Anh Tran
     * version May 27 2016
     */
    public PageStatus displaySubmitReviewsMenu() {
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";

        do {
            boolean opSucc = false;

            stdout.println(" Submit Review Menu");
            stdout.println(" Enter filename below\n");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

            do {
                stdout.print("Enter filename of review or action: ");
                userInput = myScanner.nextLine();

                if(userInput.length() > 0) {
                    opSucc = true;
                    Reviewer me = new Reviewer(mySelf.getUsername());
                    myManuscripts.get(mySelection).addNewReview(me, new Review(me, userInput));
                    stdout.println("Added " + userInput + " review.");
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else if(userInput.charAt(0) == 'b') {
                    opSucc = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                }
                else if(userInput.charAt(0) == 'e') {
                    opSucc = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                }
                else {
                    opSucc = false;
                    stdout.println("Invalid option, try again.");
                }
            }while(!opSucc);
        }while(backCallee == PageStatus.BACK);

        return backCaller;
    }
}
