/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

import enums.Recommendation;
import model.Conference;
import model.Manuscript;
import model.RegisteredUser;
import model.SubprogramChair;
import model.Reviewer;
import enums.PageStatus;

/** 
 * Class that provides the UI menus for a Subprogram Chair. 
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
 */
public class SubprogramChairUI {
    
    /** The name of the conference. */
    private Conference myConference;
    
    /** The name of the user. */
    private RegisteredUser myUser;
    
    private SubprogramChair myRole;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    private GeneralUI myParent;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    public SubprogramChairUI(final Conference theConference, final RegisteredUser theUser,
    						 GeneralUI theParent) {
    	myParent = theParent;
        myConference = theConference;
        myUser = theUser;
        myManuscripts = myConference.getSPCsManuscripts(theUser.getUsername());
        mySelection = 0;
    }
    
    /** Prints out the header information. */
    private void printHeader() {
        System.out.println("MSEE CONFERENCE MANAGEMENT SYSTEM");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Conference: " + myConference.toString());
        System.out.println("Subprogram Chair: " + myUser.getFullName());
    }

    /**
     * Displays the main menu selections.
     */
    public PageStatus displayMainMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
        
    	Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        boolean operationSuccess = false;
        String userInput = "";

        do {

            printHeader();
            stdout.println("\n Subprogram Chair Options");
            stdout.println(" -------------------------");
            stdout.println(" 1> Assign Manuscript to Reviewer");
            stdout.println(" 2> Submit Manuscript Recommendation");
            printSubMenuBackAndExit();

            do {
                stdout.print(" Please select an action: ");
                userInput = myScanner.nextLine();
                System.out.println("\n");
                
                if (userInput.length() == 0) {
                    
                    userInput = " ";
                }
                
                if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                    
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n"); 
                    operationSuccess = false;
                } else {
                    
                    switch (userInput.charAt(0)) {
                    
                    case '1':
                        operationSuccess = true;
                        backCallee = displayAssignManuscriptsMenu();
                        break;
                    case '2':
                        operationSuccess = true;
                        backCallee = displayManuscriptToSubmitRecommendationMenu();
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
                        System.out.println(" Invalid entry. Please enter a valid corresponding"
                                         + " integer or letter value.\n\n"); 
                        break;
                    }
                }
            }while(!operationSuccess);
        } while(backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);

        return backCaller;
    }

    /** 
     * Displays assign manuscript menu
     */
    private PageStatus displayAssignManuscriptsMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;

        do {
            
            printHeader();
            stdout.println("\n Manuscripts to Assign");
            stdout.println(" ----------------------");
            for (Manuscript paper : myManuscripts) {
                stdout.println(" \"" + ++counter + ") " + paper.getTitle() + "\"");
            }
            
            printSubMenuBackAndExit();

            do {
                stdout.print(" Please enter your selection: ");
                userInput = myScanner.nextLine();
                System.out.println("\n");
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    mySelection = option;
                    backCallee = displaySelectReviewerMenu();
                    if(backCallee == PageStatus.EXIT) {
                        backCaller = PageStatus.EXIT;
                    }
                    operationSuccess = true;
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
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n");
                }
            }while(!operationSuccess);
        } while(backCallee == PageStatus.BACK);

        return backCaller;
    }

    /** Displays select user to review menu
     * author Anh Tran
     * version May 30 2016
     */
    private PageStatus displaySelectReviewerMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;

        do {
            
            printHeader();
            stdout.println("\n Select Reviewer to review " + myManuscripts.get(mySelection).getTitle() + "\n");
            String[] users = (String[]) myParent.getMyUsers().keySet().toArray(); //Casting with abandon
            for (int i = 0; i < users.length; i++) {
                stdout.println(" \"" + ++counter + ") " + users[i] + "\"");
            }
            printSubMenuBackAndExit();

            do {
                stdout.print(" Please enter your selection: ");
                userInput = myScanner.nextLine();
                System.out.println("\n");
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    operationSuccess = true;
                    myManuscripts.get(mySelection).addReviewer(new Reviewer(users[option]));

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
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n");
                }
            }while(!operationSuccess);
        }while(backCallee == PageStatus.BACK);
        return backCaller;
    }

    /** Displays select manuscript to submit recommendation menu
     * author Anh Tran
     * version May 30 2016
     */
    private PageStatus displayManuscriptToSubmitRecommendationMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;

        do {

            printHeader();
            stdout.println("\n Select Manuscript to submit recommendation to: \n");
            for (Manuscript paper : myManuscripts) {
                stdout.println(" \"" + ++counter + ") " + paper.getTitle() + "\tRecommendation: " + paper.getRecommendation());
            }
            printSubMenuBackAndExit();
            stdout.println(" Please enter your selection: ");
            do{
                userInput = myScanner.nextLine();
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    mySelection = option;
                    backCallee = displaySubmitRecommendationMenu();
                    operationSuccess = true;
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
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n");
                }
            }while(!operationSuccess);
        }while(backCallee == PageStatus.BACK);

        return backCaller;
    }

    /** Displays submit recommendation menu
     * author Anh Tran
     * version May 30 2016
     */
    private PageStatus displaySubmitRecommendationMenu() {
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        boolean operationSuccess = false;
        String userInput = "";

        do {

            printHeader();
            stdout.println("\n Select Recommendation to give to " + myManuscripts.get(mySelection).getTitle() + "\n");
            stdout.println("---------------------------------------------");
            stdout.println(" 1> Strong Accect");
            stdout.println(" 2> Accept");
            stdout.println(" 3> Reject");
            stdout.println(" 4> Strong Reject");
            printSubMenuBackAndExit();
            stdout.print(" Please enter your selection: ");
            do {
                userInput = myScanner.nextLine();
                switch (userInput.charAt(0)) {
                    case '1':
                        operationSuccess = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.STRONG_ACCEPT);
                        break;
                    case '2':
                        operationSuccess = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.ACCEPT);
                        break;
                    case '3':
                        operationSuccess = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.REJECT);
                        break;
                    case '4':
                        operationSuccess = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.STRONG_REJECT);
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
                        System.out.println(" Invalid entry. Please enter a valid corresponding"
                                         + " integer or letter value.\n\n");
                        break;
                }
            } while(!operationSuccess);
        } while(backCallee == PageStatus.BACK);

        return backCaller;
    }
    
    private void printSubMenuBackAndExit() {
        
        System.out.print(" --"
                     + "\n b> Back"
                     + "\n e> Exit/Logout"
                     + "\n\n");
    }
}
