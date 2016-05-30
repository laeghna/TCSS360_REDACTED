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
    private RegisteredUser mySelf;
    
    private SubprogramChair myRole;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    private GeneralUI myParent;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    public SubprogramChairUI(final Conference theConference, final RegisteredUser me,
    						 GeneralUI theParent) {
    	myParent = theParent;
        myConference = theConference;
        mySelf = me;
        myManuscripts = myConference.getSPCsManuscripts(me.getUsername());
        mySelection = 0;
    }
    
    /** Prints out the header information. */
    public void printHeader() {
        System.out.println(myConference.toString());
        System.out.println("Program Chair: " + mySelf.toString());
    }

    /**
     * author: Anh Tran
     * version: May 30 2016
     * Displays the main menu selections.
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
            stdout.println(" Subprogram Chair Options");
            stdout.println(" -------------------------");
            stdout.println(" 1> Assign Manuscript to Reviewer");
            stdout.println(" 2> Submit Manuscript Recommendation\n");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

            /*
            //Don't know if we need this entry validation. Switch statement will handle it.
            do {
                System.out.println("\nPlease enter a selection: ");
                try {
                    userInput = myScanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid Entry. Must enter a valid corresponding entry. ");
                }
                if (!userInput.equals("1") || !userInput.equals("2") || !userInput.equals("b") || !userInput.equals("e"))
                    System.out.println("Invalid Entry. Must enter a valid corresponding entry.");
            } while (!userInput.equals("1") || !userInput.equals("2") || !userInput.equals("b") || !userInput.equals("e"));
            */
            do {
                stdout.print("Select an action: ");
                userInput = myScanner.nextLine();
                switch (userInput.charAt(0)) {
                    case '1':
                        opSucc = true;
                        backCallee = displayAssignManuscriptsMenu();
                        break;
                    case '2':
                        opSucc = true;
                        backCallee = displayManuscriptToSubmitRecommendationMenu();
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
        } while(backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);

        return backCaller;
    }

    /** Displays assign manuscript menu
     * author Anh Tran
     * version May 30 2016
     */
    public PageStatus displayAssignManuscriptsMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";
        int counter = 0;

        do {
            boolean opSucc = false;
            stdout.println(" Assign Manuscript to Reviewer");
            stdout.println(" -----------------------------\n");
            stdout.println(" List of Manuscripts");
            for (Manuscript paper : myManuscripts) {
                stdout.println(" \"" + ++counter + ") " + paper.getTitle() + "\"");
            }
            stdout.println("\n ---------------------------------");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

        /*
        //Not needed. Validation handled in next do-while loop
        do {
            System.out.println("\nPlease enter a selection: ");
            try {
                mySelection = Integer.parseInt(myScanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid Entry. Must enter a valid corresponding integer. ");
            }
            if (mySelection < 0 || mySelection > counter)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while(mySelection < 0 || mySelection > counter);
        */
            do {
                stdout.print("Select manuscript number or action: ");
                userInput = myScanner.nextLine();
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
                    opSucc = true;
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
        } while(backCallee == PageStatus.BACK);

        return backCaller;
    }

    /** Displays select user to review menu
     * author Anh Tran
     * version May 30 2016
     */
    public PageStatus displaySelectReviewerMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";
        int counter = 0;

        do {
            boolean opSucc = false;
            stdout.println("\n Select Reviewer to review " + myManuscripts.get(mySelection).getTitle() + "\n");
            String[] users = (String[]) myParent.getMyUsers().keySet().toArray(); //Casting with abandon
            for (int i = 0; i < users.length; i++) {
                stdout.println(" \"" + ++counter + ") " + users[i] + "\"");
            }
            stdout.println("\n ---------------------------------");
            stdout.println(" b> Back");
            stdout.println(" e> Exit\n");

            do {
                stdout.print("Select reviewer to assign or action: ");
                userInput = myScanner.nextLine();
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException e) {
                    option = 0;
                }
                if(option > 0 && option <= counter) {
                    opSucc = true;
                    myManuscripts.get(mySelection).addReviewer(new Reviewer(users[option]));

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
        }while(backCallee == PageStatus.BACK);
        return backCaller;
    }

    /** Displays select manuscript to submit recommendation menu
     * author Anh Tran
     * version May 30 2016
     */
    public PageStatus displayManuscriptToSubmitRecommendationMenu() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";
        int counter = 0;

        do {
            boolean opSucc = false;
            stdout.println("Select Manuscript to submit recommendation to: \n");
            for (Manuscript paper : myManuscripts) {
                stdout.println(" \"" + ++counter + ") " + paper.getTitle() + "\tRecommendation: " + paper.getRecommendation());
            }
            stdout.println("b> Back");
            stdout.println("e> Exit\n");
            stdout.print("Select Manuscript or action: ");
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
                    opSucc = true;
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
        }while(backCallee == PageStatus.BACK);

        return backCaller;
    }

    /** Displays submit recommendation menu
     * author Anh Tran
     * version May 30 2016
     */
    public PageStatus displaySubmitRecommendationMenu() {
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.

        Scanner myScanner = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        String userInput = "";
        int counter = 0;

        do {
            boolean opSucc = false;
            stdout.println("Select Recommendation to give to " + myManuscripts.get(mySelection).getTitle() + "\n");
            stdout.println(" 1> Strong Accect");
            stdout.println(" 2> Accept");
            stdout.println(" 3> Reject");
            stdout.println(" 4> Strong Reject\n");
            stdout.println("b> Back");
            stdout.println("e> Exit\n");
            stdout.print("Select Recommendation or action: ");
            do {
                userInput = myScanner.nextLine();
                switch (userInput.charAt(0)) {
                    case '1':
                        opSucc = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.STRONG_ACCEPT);
                        break;
                    case '2':
                        opSucc = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.ACCEPT);
                        break;
                    case '3':
                        opSucc = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.REJECT);
                        break;
                    case '4':
                        opSucc = true;
                        myManuscripts.get(mySelection).setRecommendation(Recommendation.STRONG_REJECT);
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
            } while(!opSucc);
        } while(backCallee == PageStatus.BACK);

        return backCaller;
    }
}
