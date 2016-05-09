package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.Recommendation;
import model.RegisteredUser;
import model.Reviewer;
import model.Role;
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

	private void displayAuthorMenu() {
		// TODO Auto-generated method stub
		
	}

	private void displaySubprogramChairMenu() {

		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		SubprogramChair spc = currConf.getSubProgramChair(currUser.getUsername());
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		
		stdout.println("\n\n" +
					   "1> Designate a User as a Reviewer\n" + 
					   "2> Assign a Manuscript to a Reviewer\n" + 
					   "3> Submit a Recommendation for a Manuscript\n\n" +
					   "e> exit");
		
		do {
			
			stdout.print("Select an action: ");
			uIn = stdin.nextLine();
			
			switch(uIn.charAt(0)) {
			case '1':
				opSucc = true;
				designateReviewer();
				break;
			case '2':
				opSucc = true;
				assignManToReviewer();
				break;
			case '3':
				opSucc = true;
				submitRecForManuscript();
				break;
			case 'e':
				opSucc = true;
			}
		} while(!opSucc);
	}

	private void submitRecForManuscript() {

		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		SubprogramChair me = currConf.getSubProgramChair(currUser.getUsername());
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		stdout.println("Submitting a Recommendation for a Manuscript.\n");
		
		stdout.println("ALL MANUSCRIPTS ASSIGNED TO ME:\n");
		
		int i = 1;
		for(Manuscript man : me.getAssignedManuscripts()) {
			stdout.println(String.format("%d> %s", i++, man.getTitle()));
		}
		stdout.println("\ne> exit \n");
		Manuscript chosnMan = null;
		do {
			
			stdout.print("Select a Manuscript: ");
			uIn = stdin.nextLine();
			int option = 0;
			
			try {
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= me.getAssignedManuscripts().size()) {
				
				opSucc = true;
				chosnMan = me.getAssignedManuscripts().get(option - 1);
				submitRecommendation(chosnMan);
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else  {
				stdout.println("Invalid input. Try again.");
			}
		} while (!opSucc);
	}
	private void submitRecommendation(Manuscript chosnMan) {

		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn = "";
		boolean opSucc = false;
		SubprogramChair me = currConf.getSubProgramChair(currUser.getUsername());
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		stdout.println("Submitting a Recommendation for a Manuscript.\n");
		
		stdout.println("Enter the body of your recommendation (type \'exit\' to exit): ");
		uIn += stdin.nextLine();
		if(!uIn.equals("exit")) {
			Recommendation rec = new Recommendation(me, uIn);
			chosnMan.setRecommendation(rec);
		}
	}

	private void assignManToReviewer() {

		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		SubprogramChair me = currConf.getSubProgramChair(currUser.getUsername());
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		stdout.println("Assigning a Reviewer to a Manuscript.\n");
		
		stdout.println("ALL MANUSCRIPTS ASSIGNED TO ME:\n");
		
		int i = 1;
		for(Manuscript man : me.getAssignedManuscripts()) {
			stdout.println(String.format("%d> %s", i++, man.getTitle()));
		}
		stdout.println("\ne> exit \n");
		Manuscript chosnMan = null;
		do {
			
			stdout.print("Select a Manuscript: ");
			uIn = stdin.nextLine();
			int option = 0;
			
			try {
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= me.getAssignedManuscripts().size()) {
				
				opSucc = true;
				chosnMan = me.getAssignedManuscripts().get(option - 1);
				assignReviewer(chosnMan);
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else  {
				stdout.println("Invalid input. Try again.");
			}
		} while (!opSucc);
	}

	private void assignReviewer(Manuscript chosnMan) {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		SubprogramChair me = currConf.getSubProgramChair(currUser.getUsername());
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		stdout.println("Assigning a Reviewer to a Manuscript.\n");
		
		stdout.println("CHOOSE A REVIEWER: "); 
		
		int i = 1;
		for(String key : currConf.getReviewers().keySet()) {
			
			stdout.println(String.format("%d> %s, KEY: %s", i++, myUsers.get(key), key));
		}
		stdout.println("\ne> exit");
		do {
			stdout.print("Please enter the key for the rever you want to assign the manuscript to: ");
			
			uIn = stdin.nextLine();
			
			if(currConf.getReviewers().containsKey(uIn)) {
				
				opSucc = true;
				chosnMan.addReviewer(currConf.getReviewers().get(uIn));
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else  {
				stdout.println("Invalid input. Try again.");
			}
		} while (!opSucc);
		
	}

	private void designateReviewer() {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Subprogram Chair.", currConf.toString()));
		stdout.println("Assigning a user as a Reviewer.\n");
		
		stdout.println("ALL USERS:");
		int i = 0;
		
		String[] unArr = new String[myUsers.keySet().size()];
		
		for(String s : myUsers.keySet()) {
			unArr[i++] = s;
		}
		
		i = 1;		
		for(String key : unArr) {
			stdout.println(String.format("%d> %s", i++, myUsers.get(key)));
		}
		stdout.println("\ne> exit \n");
		
		do{
			
			stdout.print("Enter the number corresponding to the user that will become a reviewer: ");
			int option = 0;
			uIn = stdin.nextLine();
			try {
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= unArr.length) {
				opSucc = true;
				currConf.assignReviewer(myUsers.get(unArr[option - 1]));
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				stdout.println("Invalid input. Try again.");
			}
		} while(!opSucc);
	}

	private void displayReviewerMenu() {
		// TODO Auto-generated method stub
		
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
					   "3> Designate a user as a Subprogram Chair\n" + 
					   "4> Assign a Subprogram Chair a manuscript\n\n" +
					   "e> Exit");
		
		do {
			
			stdout.print("Select an action: ");
			uIn = stdin.nextLine();
			switch(uIn.charAt(0)) {
			case '1':
				opSucc = true;
				viewAllPapers();
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

	private void assignManToSPC() {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = currConf.getProgramChair();
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.\n\n", currConf.toString()));
		
		stdout.println("\nSELECT A PROGRAM CHAIR TO ASSIGN A MANUSCRIPT:\n");
		
		HashMap<String, SubprogramChair> spcs = currConf.getSPCs();
		
		int i = 0;
		
		//init a username array
		String[] unArr = new String[spcs.keySet().size()];
		
		for(String s : spcs.keySet()) {
			unArr[i++] = s;
		}
		
		i = 1;
		//print spcs
		for(String key : unArr) {
			stdout.println(String.format("%d> %s %s", i++, myUsers.get(key).getFirstName(), myUsers.get(key).getLastName()));
		}
		stdout.println("\ne> exit\n");
		
		do {
			
			stdout.print("Enter a number corresponding to the SPC you want to assign a paper to: ");
			
			uIn = stdin.nextLine();
			int option = 0;
			try {
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= unArr.length) {
				opSucc = true;
				assignMan(spcs.get(unArr[option - 1]));
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				opSucc = false;
				stdout.println("Invalid Input. Try again.");
			}
		} while(!opSucc);
	}

	private void assignMan(SubprogramChair subprogramChair) {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = currConf.getProgramChair();
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.\n\n", currConf.toString()));
		stdout.println(String.format("Assigning a manuscript to %s.\n", 
									 myUsers.get(subprogramChair.getUserName())));
		
		stdout.println("AVAILABLE MANUSCRIPTS:\n");
		int i = 1;
		for(Manuscript man : currConf.getManuscripts()) {
			stdout.println(String.format("%d> %s", i++, man.getTitle()));
		}
		stdout.println("\ne> exit");
		
		do {
			
			stdout.print("Enter the number of the manuscript you want to assign: ");
			int option = 0;
			uIn = stdin.nextLine();
			
			try{
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= currConf.getManuscripts().size()) {
				
				opSucc = true;
				Manuscript paper = currConf.getManuscripts().get(option - 1);
				
				try {
					paper.setSPCsUsername(subprogramChair.getUserName());
					subprogramChair.addManuscript(paper);
				} catch(Exception e) {
					
					stdout.println(e.getMessage());
					opSucc = false;
				}
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				stdout.println("Invalid input. Try again.");
			}
		} while (!opSucc);
	}

	private void designateSPC() {

		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = currConf.getProgramChair();
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.\n\n", currConf.toString()));
		
		stdout.println("ALL USERS:");
		int i = 0;
		
		String[] unArr = new String[myUsers.keySet().size()];
		
		for(String s : myUsers.keySet()) {
			unArr[i++] = s;
		}
		
		i = 1;		
		for(String key : unArr) {
			stdout.println(String.format("%d> %s", i++, myUsers.get(key)));
		}
		stdout.println("e> exit \n");
		do {
			
			stdout.print("Enter the number correlating to the user that will become a Subprogram Chair: ");
			uIn = stdin.nextLine();
			int option = 0;
			try {
				option = Integer.parseInt(uIn);
			} catch (NumberFormatException ne) {
				option = 0;
			}
			if(option > 0 && option <= unArr.length) {
				opSucc = true;
				pc.addSPC(unArr[option - 1]);
				currConf.assignSubprogramChair(myUsers.get(unArr[option - 1]));
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				opSucc = false;
				stdout.println("Invalid input, try again.");
			}
		} while(!opSucc);
	}

	private void acceptRejectManuscript() {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.", currConf.toString()));
		stdout.println("Accepting/Rejecting a Manuscript.\n");
		stdout.println("AVAILABLE MANUSCRIPTS:\n");
		
		int i = 1;
		for(Manuscript man : currConf.getManuscripts()) {
			stdout.println(String.format("%d> %s", i++, man.getTitle()));
		}
		stdout.println("\ne> exit\n");
		
		do {
			
			stdout.print("Select the number of the paper to accept/reject: ");
			int option = 0;
			uIn = stdin.nextLine();
			
			try {
				option = Integer.parseInt(uIn);
			} catch(NumberFormatException ne) {
				option = 0;
			}
			
			if(option > 0 && option <= currConf.getManuscripts().size()) {
				
				opSucc = true;
				acceptReject(currConf.getManuscripts().get(option - 1));
			} else if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				stdout.println("Invalid input. Try again.");
			}
		} while(!opSucc);
	}

	private void acceptReject(Manuscript manuscript) {
		
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		
		printHeader();
		stdout.println(String.format("Logged into conference %s as Program Chair.", currConf.toString()));
		stdout.println(String.format("Accepting/Rejecting a %s.\n", manuscript.getTitle()));
		
		stdout.println("1> Accept\n" + 
					   "2> Reject\n\n" + 
					   "e> exit\n");
		
		do {
			
			stdout.print("Select an action: ");
			uIn = stdin.nextLine();
			
			switch(uIn.charAt(0)) {
			
			case '1':
				opSucc = true;
				manuscript.setAcceptance(true);
				break;
			case '2':
				opSucc = true;
				manuscript.setAcceptance(false);
				break;
			case 'e':
				opSucc = true;
				break;
			default:
				stdout.println("Invalid input. Try again.");
				break;
			}
		} while(!opSucc);
	}

	private void viewAllPapers() {
		
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
			stdout.println("\n\n");
		}
		
		stdout.println("\ne> Exit");
		do {
			
			stdout.print("Enter an Action: ");
			uIn = stdin.nextLine();
			if(uIn.charAt(0) == 'e') {
				opSucc = true;
			} else {
				stdout.println("Invalid option, try again.");
			}
		} while(!opSucc);
	}
}
