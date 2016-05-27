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
import java.util.Set;

import enums.AcceptanceStatus;
import enums.PageStatus;
import enums.Recommendation;
import enums.Role;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.RegisteredUser;
import model.Review;
import model.Reviewer;
import model.SubprogramChair;

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
		
		boolean backCaller = false; //Used to control what the calling method does.
		boolean backCallee = true; //Used to control we do based off the actions taken.
		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		
		do {
		
			stdout.print("\n\nMSEE CONFERENCE MANAGEMENT SYSTEM\n\n" + 
							   "1> Login\n" + 
							   "2> Register\n\n" + 
							   "e> Shut System Down\n\n" +
							   "Select an action: ");
			
			userInput = stdin.nextLine();
			
			switch(userInput.charAt(0)) {
				
			case '1':
				displayLogin();
				break;
			
			case '2':
				displayRegister();
				break;
			
			case 'e':
				break;
				
			default:
				stdout.println("Incorrect Option. Try Again.");
				break;
			}
			
		} while (userInput.charAt(0) != 'e');	
	}
	
	private void printHeader() {
		
		System.out.println("\n\nMSEE CONFERENCE MANAGEMENT SYSTEM");
		System.out.println("Logged in as: " + currUser.toString());
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
				
				stdout.print("MSEE CONFERENCE MANAGEMENT SYSTEM\n\n" +
							 "Please enter your username (type \'b\' to go back, or \'e\' to exit) : ");
				
				userInput = stdin.nextLine();
				
				if(myUsers.containsKey(userInput)) {
					
					loginSuccess = true; // Exit the inner loop.
					currUser = myUsers.get(userInput); 
					backCallee = displayConferenceOptions();// Called function determines
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
		
		boolean backCaller = false; //Used to control what the calling method does.
		Scanner stdin = new Scanner(System.in);
		String userInput;
		boolean opSuccess = false;
		
		String fName;
		String lName; 
		String uName;
		
		System.out.println("\n\nCONFERENCE MANAGEMENT SYSTEM\n\n");
			
		System.out.print("Please enter your first name and last name separated by a space, enter \'back\' to go back: ");
		fName = stdin.next();
		if(!fName.equals("back")) {
			lName = stdin.nextLine();
			do {
				
				System.out.print("Please enter a unique username for yourself: ");
				uName = stdin.nextLine();
				
				if(!myUsers.containsKey(uName)) {
					opSuccess = true;
					RegisteredUser user = new RegisteredUser(uName, fName, lName);
					myUsers.put(uName, user);
				} else {
					System.out.println("That username is already taken. Try again.\n");
				}
			} while(!opSuccess);
		}
	}
	
	private PageStatus displayConferenceOptions() {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		
		do {
		
			printHeader();
			System.out.println("\nAvailable Conferences: ");
			
			int i = 1;
			for(Conference c : myConfs) {
				System.out.println(String.format("%d> %s", i++, c.toString()));
			}
			System.out.println("\nb> back\n"+
							   "e> exit");
			
			System.out.print("Enter an option: ");
			
			do {
				
				userInput = stdin.nextLine();
				int k;
				try {
					k = Integer.parseInt(userInput);
				} catch(NumberFormatException ne) {
					k = 0;
				}
				
				if(k > 0 && k <= myConfs.size()) {
					
					operationSuccess = true;
					currConf = myConfs.get(k - 1);
					backCallee = displayConfOptions();
					
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

	private PageStatus displayConfOptions() {

		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		do {
			printHeader();
			System.out.println("Logged into Conference: " + currConf.toString());
			
			ArrayList<Role> roles = currConf.getRoles(currUser.getUsername());
			int i = 1;
			for(Role r : roles) {
				
				switch(r) {
				
				case PROGRAMCHAIR:
					System.out.println(String.format("%d> %s", i++, "Program Chair Options"));
					break;
				case SUBPROGRAMCHAIR:
					System.out.println(String.format("%d> %s", i++, "Subprogram Chair Options"));
					break;
				case REVIEWER:
					System.out.println(String.format("%d> %s", i++, "Reviewer Options"));
					break;
				case AUTHOR:
					System.out.println(String.format("%d> %s", i++, "Author Options"));
					break;
				default:
					break;
				}
			}
			System.out.println(String.format("%d> %s\n\n", i++, "Submit a Manuscript"));
			System.out.println("b> back");
			System.out.println("e> Exit\n\n");
			System.out.print("Select an option for this conference: ");
			do {
				userInput = stdin.nextLine();
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
					backCaller = PageStatus.BACK	; // Tell the calling method to hold.
				}
			} while(!operationSuccess);
		} while(backCallee != PageStatus.EXIT);
		
		return backCaller;
	}

	private PageStatus displaySubmitManuscriptMenu() {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		boolean exitFlag = false; // Used to control the second half of this method.
		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		
		printHeader();
		System.out.println(String.format("Logged into Conference: %s\n\n", currConf.toString()));
		
		String Title, pName = null;
		
		System.out.print("Enter the title of your manuscript (type \'b\' to go back, or \'e\' to exit): ");
		
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
		
		System.out.print("Enter the pathname to your manuscript (type \'b\' to go back, or \'e\' to exit): ");
		
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
			//TODO Implement subprogram chair UI class here.
			break;
			
		case REVIEWER: 
			//TODO Implement reveiwer UI class here.
			break;
			
		case AUTHOR:
			//TODO Implement author UI class here.
			break;
		}
		return backFlag;		
	}
}