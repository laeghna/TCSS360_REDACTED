/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.util.ArrayList;
import java.util.Scanner;

import enums.PageStatus;
import model.Author;
import model.Conference;
import model.Manuscript;
import model.RegisteredUser;

/** 
 * Class that provides the UI menus for an Author who has submitted a manuscript. 
 * 
 * @author Lisa Taylor
 * @version 30 May 2016
 */
public class AuthorUI {
    
    /** The current Conference. */
    private Conference myConference;
    
    /** The current User. */
    private RegisteredUser myUser;
    
    private Author myRole;
    
    private GeneralUI myParent;
    
    /** The list of manuscripts the Author has submitted the current Conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    /** Controls what the calling method does. */
    private PageStatus backCaller;
    
    /** Controls what to do based off the actions taken. */
    private PageStatus backCallee;
    
    /** Constructs an AuthorUI object. */
    public AuthorUI(final Conference theConference, final RegisteredUser theUser, GeneralUI theParent) {
        
        myConference = theConference;
        myUser = theUser;
        myParent = theParent;
        myRole = new Author(theUser.getUsername());
        myManuscripts = theConference.getMyManuscripts(theUser.getUsername());

        backCaller = PageStatus.GOTO_MAIN_MENU;
        backCallee = PageStatus.GOTO_MAIN_MENU;
    }
    
    /** Helper method that prints out the header information for a menu. */
    public void printHeader() {
        
        System.out.println("MSEE CONFERENCE MANAGEMENT SYSTEM");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Conference: " + myConference.toString());
        System.out.println("Author: " + myUser.getFullName());
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
            System.out.println(" Author Options"
                           + "\n ---------------"
                           + "\n 1> Submit a Manuscript"
                           + "\n 2> View My Submitted Manuscripts"
                           + "\n 3> Make Changes to a Submitted Manuscript"
                           + "\n 4> Unsubmit a Manuscript");
            printSubMenuBackAndExit();
            do {
                
                System.out.print("Please enter a selection: ");
                userInput = scanInput.nextLine();
                if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                    
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    operationSuccess = false;
                } else {
                
                    switch(userInput.charAt(0)) {
                    
                    case '1':
                        operationSuccess = true;
                        backCallee = myParent.displaySubmitManuscriptMenu();
                        break;
                        
                    case '2':
                        operationSuccess = true;
                        backCallee = displayViewMyManuscriptsMenu(scanInput);
                        break;
                        
                    case '3':
                        operationSuccess = true;
                        backCallee = displayMakeManuscriptChangesMenu(scanInput);
                        break;
                        
                    case '4':
                        operationSuccess = true;
                        backCallee = displayUnsubmitManuscriptMenu(scanInput);
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
            } while (!operationSuccess);
        } while (backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);
        
        return backCaller;
    }
    
    /**
     * Displays the manuscripts the Author has submitted to the current Conference.
     * 
     * @param stdin scans the user input
     * @return the page status to determine whether or not the calling method should hold or retire
     */
    public PageStatus displayViewMyManuscriptsMenu(Scanner stdin) {
        
        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;
        
        printHeader();
        printManuscriptsNumberedList(counter);
        printSubMenuBackAndExit();
        
        do {
            System.out.print("\nPlease enter a selection: ");
            userInput = stdin.nextLine(); 
            if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                
                System.out.println("Invalid entry. Please enter a valid corresponding"
                                 + "integer or letter value."); 
                operationSuccess = false;
            } else {
            
                switch(userInput.charAt(0)) {
                    
                case 'b':
                    operationSuccess = true;
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                    break;
                    
                case 'e':
                    operationSuccess = true;
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                    break;
                    
                default:
                    operationSuccess = false;
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    break;
                }
            }
        } while (!operationSuccess);    
        
        return backCaller;
    }
    
    public PageStatus displayMakeManuscriptChangesMenu(Scanner stdin) {

        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;
        
        do {
            printHeader();
            printManuscriptsNumberedList(counter);
            printSubMenuBackAndExit();
            
            do {
                System.out.print("\nPlease enter a selection: ");
                userInput = stdin.nextLine(); 
                if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                    
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    operationSuccess = false;
                } else if (Character.isDigit(userInput.charAt(0))) {
                    
                    int index = Integer.parseInt(userInput) - 1;
                    backCallee = displayModificationsMenu(stdin, myManuscripts.get(index));
                } else {
                
                    switch(userInput.charAt(0)) {
                        
                    case 'b':
                        operationSuccess = true;
                        backCaller = PageStatus.BACK; // Tell calling method to hold.
                        break;
                        
                    case 'e':
                        operationSuccess = true;
                        backCaller = PageStatus.EXIT; // Tell calling method to retire.
                        break;
                        
                    default:
                        operationSuccess = false;
                        System.out.println("Invalid entry. Please enter a valid corresponding"
                                         + "integer or letter value."); 
                        break;
                    }
                }
            } while (!operationSuccess);    
        } while (backCallee == PageStatus.BACK);

        return backCaller;
    }
    
    public PageStatus displayUnsubmitManuscriptMenu(Scanner stdin) {
        
        boolean operationSuccess = false;
        String userInput = "";
        int counter = 0;
        
        printHeader();
        printManuscriptsNumberedList(counter);
        printSubMenuBackAndExit();
        
        do {
            
            System.out.print("\nPlease enter a selection: ");
            userInput = stdin.nextLine(); 
            if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                
                System.out.println("Invalid entry. Please enter a valid corresponding"
                                 + "integer or letter value."); 
                operationSuccess = false;
            } else if (Character.isDigit(userInput.charAt(0))) {
                
                int index = Integer.parseInt(userInput) - 1;
                myRole.removeManuscript(myManuscripts.get(index));
                String title = myManuscripts.get(index).getTitle();
                myManuscripts.remove(index);
                System.out.println("\n\"" + title + "\" removed successfully.");
                printManuscriptsNumberedList(counter);
                backCaller = PageStatus.BACK; //Tell the calling method to hold.
            } else {
            
                switch(userInput.charAt(0)) {
                    
                case 'b':
                    operationSuccess = true;
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                    break;
                    
                case 'e':
                    operationSuccess = true;
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                    break;
                    
                default:
                    operationSuccess = false;
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    break;
                }
            }
        } while (!operationSuccess);
        
        return backCaller;
    }
    
    private PageStatus displayModificationsMenu(Scanner stdin, Manuscript theManuscript) {
        
        boolean operationSuccess = false;
        String userInput = "";
        
        printHeader();
        System.out.println("\n Manuscript: " + theManuscript.getTitle()
                         + "\n"
                         + "\n Changes to Make"
                         + "\n ----------------"
                         + "\n 1> Change Title"
                         + "\n 2> Resubmit Manuscript");
        printSubMenuBackAndExit();
        
        do {
            
            System.out.print("\nPlease enter a selection: ");
            userInput = stdin.nextLine(); 
            if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                
                System.out.println("Invalid entry. Please enter a valid corresponding"
                                 + "integer or letter value."); 
                operationSuccess = false;
            } else {
            
                switch(userInput.charAt(0)) {
                
                case '1':
                    operationSuccess = true;
                    System.out.println("\nEnter the new Title for the Manuscript: ");
                    userInput = stdin.nextLine();
                    theManuscript.setTitle(userInput);
                    System.out.println("\nManuscript title successfully changed to \"" + theManuscript.getTitle() + "\".");
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                    break;
                    
                case '2':
                    operationSuccess = true;
                    System.out.println("Enter the full pathname for the file: ");
                    userInput = stdin.nextLine();
                    myRole.editManuscript(theManuscript, userInput);
                    System.out.println("\nManuscript successfully uploaded.");
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                    break;
                    
                case 'b':
                    operationSuccess = true;
                    backCaller = PageStatus.BACK; // Tell calling method to hold.
                    break;
                    
                case 'e':
                    operationSuccess = true;
                    backCaller = PageStatus.EXIT; // Tell calling method to retire.
                    break;
                    
                default:
                    operationSuccess = false;
                    System.out.println("Invalid entry. Please enter a valid corresponding"
                                     + "integer or letter value."); 
                    break;
                }
            }
        } while (!operationSuccess);
        
        return backCaller;
    }
    
    /** Helper method that prints the list of Manuscripts. */
    private int printManuscriptsNumberedList(int counter) {

        counter = 0;
        System.out.println("\n Submitted Manuscripts"
                         + "\n ----------------------");
        
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
