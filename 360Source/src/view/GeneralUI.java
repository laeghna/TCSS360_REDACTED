package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.RegisteredUser;
import model.Role;

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

	private void displayLogin() {
		
		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		boolean loginSuccess = false;
		
		do {
			
			stdout.print("MSEE CONFERENCE MANAGEMENT SYSTEM\n\n" +
						 "Please enter your username (type \'back\' to return to the main menu) : ");
			
			userInput = stdin.nextLine();
			
			if(myUsers.containsKey(userInput)) {
				
				loginSuccess = true;
				currUser = myUsers.get(userInput);
				displayConferenceOptions();
			}
		} while(!userInput.equals("back") && !loginSuccess);
	}

	private void displayRegister() {
		
		Scanner stdin = new Scanner(System.in);
		String userInput;
		boolean opSuccess = false;
		
		String fName;
		String lName; 
		String uName;
		
		System.out.println("\n\nCONFERENCE MANAGEMENT SYSTEM\n\n");
			
		System.out.print("Please enter your first name and last name separated by a space: ");
		fName = stdin.next();
		lName = stdin.nextLine();
		
		do {
			
			System.out.print("Please enter a unique username for yourself: ");
			uName = stdin.nextLine();
			
			if(!myUsers.containsKey(uName)) {
				opSuccess = true;
				RegisteredUser user = new RegisteredUser(uName, fName, lName);
				myUsers.put(uName, user);
			}
		} while(!opSuccess);
	}
	
	private void displayConferenceOptions() {

		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		
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
				displayConfOptions();
			} else if (userInput.charAt(0) == 'b' || userInput.charAt(0) == 'e') {
				operationSuccess = true;
			}
				
		} while(!operationSuccess);
	}

	private void displayConfOptions() {

		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		
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
		System.out.println(String.format("%d> %s\n", i++, "Submit a Manuscript"));
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
				parseSubMenu(roles.get(option - 1));
			} else if(option == roles.size() + 1) {
				operationSuccess = true;
				displaySubmitManuscriptMenu();
			} else if(userInput.charAt(0) == 'e') {
				operationSuccess = true;
			}
		} while(!operationSuccess);
	}

	private void displaySubmitManuscriptMenu() {

		String userInput = null;
		Scanner stdin = new Scanner(System.in);
		boolean operationSuccess = false;
		
		printHeader();
		System.out.println(String.format("Logged into Conference: %s\n\n", currConf.toString()));
		
		String Title, pName;
		
		System.out.print("Enter the title of your manuscript: ");
		
		do {
			
			Title = stdin.nextLine();
			if(Title.length() > 0) {
				operationSuccess = true;
			} else {
				System.out.print("Try again: ");
			}
		} while(!operationSuccess);
		
		operationSuccess = false;
		
		System.out.print("Enter the pathname to your manuscript: ");
		
		do {
			
			pName = stdin.nextLine();
			if(pName.length() > 0) {
				operationSuccess = true;
			} else {
				System.out.print("Try again: ");
			}
		} while(!operationSuccess);
		
		Manuscript man = new Manuscript(Title,
										String.format("%s %s", currUser.getFirstName(), currUser.getLastName()),
										currUser.getUsername(),
										pName);
		currConf.addManuscript(man);
	}

	private void parseSubMenu(Role role) {

		switch(role) {
		
		case PROGRAMCHAIR:
			displayProgramChairMenu();
		
		case SUBPROGRAMCHAIR:
			displaySubprogramChairMenu();
		
		case REVIEWER: 
			displayReviewerMenu();
		
		case AUTHOR:
			displayAuthorMenu();
		}
	}

	private void displayProgramChairMenu() {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = currConf.getProgramChair();
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.\n\n", currConf.toString()));
		
		stdout.println("Options: \n\n" +
					   "1> At-A-Glance View\n" + 
					   "2> Accept/Reject a Manuscript\n" + 
					   "3> Assign a user as a Subprogram Chair\n" + 
					   "4> Assign a Subprogram Chair a manuscript\n\n" +
					   "e> Exit");
		
		do {
			
			stdout.print("Select an action: ");
			uIn = stdin.nextLine();
			switch(uIn.charAt(0)) {
			case '1':
				opSucc = true;
				pcAtAGlance();
				break;
			case '2':
				opSucc = true;
				acceptRejectManuscript();
				break;
			case '3': 
				opSucc = true;
				designateSPC();
				break;
			case '4':
				opSucc = true;
				assignManToSPC();
				break;
			case 'e':
				opSucc = true;
				break;
			default:
				opSucc = false;
				stdout.println("Invalid option, try again.");
			}
		} while(!opSucc);
	}

	private void pcAtAGlance() {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = currConf.getProgramChair();
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.\n\n", currConf.toString()));
		
		stdout.println("\nAT A GLANCE VIEW: \n");
		
		for(Manuscript m : currConf.getManuscripts()) {
			stdout.println(m.toString());
		}
		
		stdout.println("\ne> Exit");
		stdout
	}
}
