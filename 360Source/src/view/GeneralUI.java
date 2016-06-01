/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import enums.PageStatus;
import enums.Role;
import model.Conference;
import model.Manuscript;
import model.RegisteredUser;

/**
 * Class that provides the default UI for general Users and prepares the role UIs
 * for RegisteredUsers.
 * 
 * @author Nathan Toporek
 * @author Lisa Taylor
 * @version 30 May, 2016
 */
public class GeneralUI {
    
    private ArrayList<Conference> myConfs;
    private HashMap<String, RegisteredUser> myUsers;
    private RegisteredUser currUser;
    private Conference currConf;
    
    public GeneralUI(ArrayList<Conference> theConfs, HashMap<String, RegisteredUser> theUsers) {
        
        myConfs = theConfs;
        myUsers = theUsers;
        
    }
    
    public void displayDefaultMenu() {
        
        String userInput = null;
        Scanner stdin = new Scanner(System.in);
        boolean operationSuccess = false;
        PrintStream stdout = new PrintStream(System.out);
        
        do {
        
            System.out.print("MSEE CONFERENCE MANAGEMENT SYSTEM" 
                         + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                         + "\n 1> Login" 
                         + "\n 2> Register"
                         + "\n --"
                         + "\n x> Shut System Down" 
                         + "\n"
                         + "\n Please enter a selection: ");
            
            userInput = stdin.nextLine();
            System.out.println();
            
            if (userInput.length() > 1 || Character.isWhitespace(userInput.charAt(0))) {
                
                System.out.println("Invalid entry. Please enter a valid corresponding"
                                 + "integer or letter value.\n\n"); 
                operationSuccess = false;
            } else {
            
                switch(userInput.charAt(0)) {
                
                case '1':
                    operationSuccess = true;
                    displayLogin();
                    break;
                
                case '2':
                    operationSuccess = true;
                    displayRegister();
                    break;
                
                case 'x':
                    break;
                    
                default:
                    operationSuccess = false;
                    stdout.println("Invalid entry. Please enter a valid corresponding"
                                 + "integer or letter value.");
                    break;
                }
            }
            
        } while (userInput.length() > 0 && userInput.charAt(0) != 'x');   
    }
    
    private void printHeader() {
        
        System.out.println("MSEE CONFERENCE MANAGEMENT SYSTEM");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("User: " + currUser.getFullName());
    }

    private PageStatus displayLogin() {
        
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
        String userInput = null;
        Scanner stdin = new Scanner(System.in);
        PrintStream stdout = new PrintStream(System.out);
        
        do {
            boolean loginSuccess = false;
            
            do {
                
                stdout.print("\nMSEE CONFERENCE MANAGEMENT SYSTEM"
                           + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                           + "\n"
                           + "\n Please enter your username (type \'b\' to go back, or \'e\' to exit) : ");
                
                userInput = stdin.nextLine();
                System.out.println("\n");
                
                if(myUsers.containsKey(userInput)) {
                    
                    loginSuccess = true; // Exit the inner loop.
                    currUser = myUsers.get(userInput); 
                    backCallee = displayAvailableConferences();// Called function determines
                                                            // what the outer loop does.
                    
                    // If the user wanted to exit somewhere down the option tree, tell the
                    // calling method to exit.
                    if(backCallee == PageStatus.EXIT) {
                        backCaller = PageStatus.EXIT;
                    }
                } else if(userInput.length() > 0 && userInput.charAt(0) == 'b') {
                    
                    loginSuccess = true; // Exits inner loop
                    backCaller = PageStatus.BACK; // Tell the caller to stay put.
                    backCallee = PageStatus.EXIT; //Exit outer loop.
                } else if(userInput.length() > 0 && userInput.charAt(0) == 'e') {
                    
                    loginSuccess = true; // Exit the inner loop.
                    backCallee = PageStatus.EXIT; // Exit the outer loop.
                    backCaller = PageStatus.EXIT; // Tell the caller to exit.
                }
            } while(!loginSuccess);
        } while(backCallee == PageStatus.BACK);
        
        return backCaller;
    }

    private void displayRegister() {
        

        Scanner stdin = new Scanner(System.in);
        boolean opSuccess = false;
        
        String fName;
        String lName; 
        String uName;
        
        System.out.println("\nMSEE CONFERENCE MANAGEMENT SYSTEM"
                         + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");  
        System.out.print("\n Please enter your first name and last name separated by a space or enter \'back\' to go back: ");
        fName = stdin.next();
        if (fName.equals("back")) {
            System.out.println("\n");
        } else {
            lName = stdin.nextLine();
            do {
                
                System.out.print(" Please enter a unique username for yourself: ");
                uName = stdin.nextLine();
                System.out.println("\n");
                
                if(!myUsers.containsKey(uName)) {
                    opSuccess = true;
                    RegisteredUser user = new RegisteredUser(uName, fName, lName);
                    myUsers.put(uName, user);
                } else {
                    System.out.println(" That username is already taken.\n");
                }
            } while(!opSuccess);
        }
    }
    
    private PageStatus displayAvailableConferences() {
        
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
        String userInput = null;
        Scanner stdin = new Scanner(System.in);
        boolean operationSuccess = false;
        
        do {
        
            printHeader();
            System.out.println("\n Available Conferences "
                             + "\n ----------------------");
            
            int i = 1;
            for(Conference c : myConfs) {
                System.out.println(String.format(" %d> %s", i++, c.toString()));
            }
            System.out.print(" --"
                         + "\n b> Back"
                         + "\n e> Exit/Logout"
                         + "\n"
                         + "\n Please enter a selection: ");
            
            do {
                
                userInput = stdin.nextLine();
                System.out.println("\n");
                int k;
                try {
                    k = Integer.parseInt(userInput);
                } catch(NumberFormatException ne) {
                    k = 0;
                }
                
                if(k > 0 && k <= myConfs.size()) {
                    
                    operationSuccess = true;
                    currConf = myConfs.get(k - 1);
                    backCallee = displayConferenceRoleOptions();
                    
                    // If the user wanted to exit at some point down the decision tree
                    // tell the calling method to exit.
                    if(backCallee == PageStatus.EXIT) {
                        
                        backCaller = PageStatus.EXIT;
                    }
                    
                } else if (userInput.length() > 0 && userInput.charAt(0) == 'e') {
                    operationSuccess = true; // Exit inner loop.
                    backCallee = PageStatus.EXIT; // Exit outer loop. 
                    backCaller = PageStatus.EXIT; // Tell calling function to retire.
                } else if (userInput.length() > 0 && userInput.charAt(0) == 'b') {
                    operationSuccess = true;
                    backCallee = PageStatus.EXIT; // Exit outer loop.
                    backCaller = PageStatus.BACK; //Hold calling method.
                }
                    
            } while(!operationSuccess);
        } while(backCallee == PageStatus.BACK);
        
        return backCaller;
    }

    private PageStatus displayConferenceRoleOptions() {

        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
        String userInput = null;
        Scanner stdin = new Scanner(System.in);
        boolean operationSuccess = false;
        do {
            printHeader();
            System.out.println("Conference: " + currConf.toString());
            
            ArrayList<Role> roles = currConf.getRoles(currUser.getUsername());
            int i = 1;
            System.out.println("\n Options"
                             + "\n --------");
            for(Role r : roles) {
                
                switch(r) {
                
                case PROGRAMCHAIR:
                    System.out.println(String.format(" %d> %s", i++, "Program Chair Options"));
                    break;
                case SUBPROGRAMCHAIR:
                    System.out.println(String.format(" %d> %s", i++, "Subprogram Chair Options"));
                    break;
                case REVIEWER:
                    System.out.println(String.format(" %d> %s", i++, "Reviewer Options"));
                    break;
                case AUTHOR:
                    System.out.println(String.format(" %d> %s", i++, "Author Options"));
                    break;
                default:
                    break;
                }
            }
            System.out.println(" " + i++ + "> Submit a Manuscript"
                                  + "\n --"
                                  + "\n b> Back"
                                  + "\n e> Exit/Logout");
            System.out.print("\n Please enter a selection: ");
            do {
                userInput = stdin.nextLine();
                System.out.println("\n");
                int option = 0;
                try {
                    option = Integer.parseInt(userInput);
                } catch(NumberFormatException ne) {
                    option = 0;
                }
                
                if(option > 0 && option <= roles.size()) {
                    
                    operationSuccess = true;
                    backCallee = parseSubMenu(roles.get(option - 1));
                } else if(option == roles.size() + 1) {
                    operationSuccess = true;
                    backCallee = displaySubmitManuscriptMenu();
                } else if(userInput.length() > 0 && userInput.charAt(0) == 'e') {
                    operationSuccess = true; // Exit inner loop
                    backCallee = PageStatus.EXIT; // Exit outer loop
                    backCaller = PageStatus.EXIT; // Tell the calling method to retire.
                } else if(userInput.length() > 0 && userInput.charAt(0) == 'b') {
                    operationSuccess = true; // Exit the inner loop,
                    backCallee = PageStatus.EXIT; // Exit the outer loop.
                    backCaller = PageStatus.BACK    ; // Tell the calling method to hold.
                }
            } while(!operationSuccess);
        } while(backCallee != PageStatus.EXIT);
        
        return backCaller;
    }

    public PageStatus displaySubmitManuscriptMenu() {
        
        PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
        boolean exitFlag = false; // Used to control the second half of this method.
        Scanner stdin = new Scanner(System.in);
        boolean operationSuccess = false;
        
        printHeader();
        System.out.println("Conference: " + currConf.toString());
        
        String Title, pName = null;
        
        System.out.print("\n Please enter the title of your manuscript (type \'b\' to go back, or \'e\' to exit): ");
        
        do {
            
            Title = stdin.nextLine();
            if (Title.length() == 1 && Title.charAt(0) == 'b'){
                operationSuccess = true; // Exit this operation.
                backCaller = PageStatus.BACK; // Tell calling method to hold.
                exitFlag = true;
            } else if (Title.length() == 1 && Title.charAt(0) == 'e'){
                operationSuccess = true; // Exit this operation.
                backCaller = PageStatus.EXIT; // Tell the calling method to expire.
                exitFlag = true;
            } else if(Title.length() > 0) {
                operationSuccess = true;
            } else {
                System.out.print("Try again: ");
            }
        } while(!operationSuccess);
        
        operationSuccess = exitFlag;
        
        System.out.print(" Please enter the pathname to your manuscript (type \'b\' to go back, or \'e\' to exit): ");
        
        while(!operationSuccess) {
            
            pName = stdin.nextLine();
            if (Title.length() == 1 && Title.charAt(0) == 'b'){
                operationSuccess = true; // Exit this operation.
                backCaller = PageStatus.BACK; // Tell calling method to hold.
                exitFlag = true;
            } else if (Title.length() == 1 && Title.charAt(0) == 'e'){
                operationSuccess = true; // Exit this operation.
                backCaller = PageStatus.EXIT; // Tell the calling method to expire.
                exitFlag = true;
            } else if(pName.length() > 0) {
                operationSuccess = true;
            } else {
                System.out.print("Try again: ");
            }
        } 
        
        if(!exitFlag) {
            Manuscript man = new Manuscript(Title,
                                            String.format("%s %s", currUser.getFirstName(), currUser.getLastName()),
                                            currUser.getUsername(),
                                            pName);
            currConf.addManuscript(man);
            System.out.println("\n \"" + Title + "\" was submitted succesfully.\n\n");
        }
        
        return backCaller;
    }

    private PageStatus parseSubMenu(Role role) {

        PageStatus backFlag = PageStatus.GOTO_MAIN_MENU;
        switch(role) {
        
        case PROGRAMCHAIR:
            ProgramChairUI pcUI = new ProgramChairUI(currConf, currUser, myUsers);
            backFlag = pcUI.displayMainMenu();
            break;
            
        case SUBPROGRAMCHAIR:
            SubprogramChairUI spcUI = new SubprogramChairUI(currConf, currUser, this);
            backFlag = spcUI.displayMainMenu();
            break;
            
        case REVIEWER:
            ReviewerUI revUI = new ReviewerUI(currConf, currUser, this);
            backFlag = revUI.displayMainMenu();
            break;
            
        case AUTHOR:
            AuthorUI authUI = new AuthorUI(currConf, currUser, this);
            backFlag = authUI.displayMainMenu();
            break;
        }
        return backFlag;        
    }

    public HashMap<String, RegisteredUser> getMyUsers() {
        return myUsers;
    }
}
