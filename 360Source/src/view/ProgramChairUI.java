/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #3                                    *
 *****************************************************/

package view;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import enums.AcceptanceStatus;
import enums.PageStatus;
import model.Conference;
import model.Manuscript;
import model.ProgramChair;
import model.RegisteredUser;
import model.SubprogramChair;

/** 
 * Class that provides the UI menus for a Program Chair. 
 * 
 * @author Lisa Taylor
 * @version 30 May 2016
 */
public class ProgramChairUI {
    
    /** The current Conference. */
    private Conference myConference;
    
    /** The current User. */
    private RegisteredUser myUser;
    
    private ProgramChair myRole;
    
    /** Maps each registered user's unique username to its corresponding RegisteredUser object. */
    private HashMap<String, RegisteredUser> myRegisteredUsers;
    
    /** 
     * Constructs a ProgramChair object. 
     * 
     * @param theConference the Conference object to be stored
     * @param theName the user's name
     * @param theManuscripts the list of Manuscripts
     */
    public ProgramChairUI (final Conference theConference, final RegisteredUser me, 
                           final HashMap<String, RegisteredUser> regUsers) {
    	
    	myConference = theConference;
        myUser = me;
        myRegisteredUsers = regUsers;
        myRole = myConference.getProgramChair();        
    }
    
    /** Prints out the header information. */
    private void printHeader() {
        System.out.println("MSEE CONFERENCE MANAGEMENT SYSTEM");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Conference: " + myConference.toString());
        System.out.println("Program Chair: " + myUser.getFullName());
    }
    
    /**
     * Displays the main menu selections.
     * @return Returns whether or not the calling method should hold or 
     * 		   retire.
     */
    public PageStatus displayMainMenu() {
    	
    	PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
    	Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		
		do {
			boolean opSucc = false;
			
			printHeader();
			
			stdout.println("\n Program Chair Options "
			             + "\n ----------------------"
			             + "\n 1> View Manuscripts"
			             + "\n 2> Accept/Reject Manuscript" 
			             + "\n 3> Designate Subprogram Chair" 
			             + "\n 4> Assign Manuscript to Subprogram Chair");
		     printSubMenuBackAndExit();
			
			do {
				
				System.out.print(" Please enter a selection: ");
				uIn = stdin.nextLine();
				System.out.println("\n");
				switch(uIn.charAt(0)) {
				case '1':
					opSucc = true;
					backCallee = viewAllPapers();
					break;
				case '2':
					opSucc = true;
					backCallee = acceptRejectManuscript();
					break;
				case '3': 
					opSucc = true;
					backCallee = displayDesignateSPCMenu();
					break;
				case '4':
					opSucc = true;
					backCallee = displayAssignManuscriptToSPCMenu();
					break;
				case 'b': 
					opSucc = true; // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.BACK; // Tell calling method to hold. 
					break;
				case 'e':
					opSucc = true;  // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.EXIT; // Tell calling method to retire.
					break;
				default:
					opSucc = false;
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n"); 
					break;
				}
				if(backCallee == PageStatus.EXIT) {
					
					backCaller = PageStatus.EXIT;
				}
			} while(!opSucc);
		} while(backCallee == PageStatus.BACK || backCallee == PageStatus.GOTO_MAIN_MENU);
		
		return backCaller;
    }
    private PageStatus displayAssignManuscriptToSPCMenu() {
		
    	PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;

		do {
			printHeader();
			
			stdout.println("\n Subprogram Chairs"
			             + "\n ------------------");
			
			HashMap<String, SubprogramChair> spcs = myConference.getSPCs();
			
			int i = 0;
			
			//init a username array
			String[] unArr = new String[spcs.keySet().size()];
			
			for(String s : spcs.keySet()) {
				unArr[i++] = s;
			}
			
			i = 1;
			//print spcs
			for(String key : unArr) {
				stdout.println(String.format("%d> %s", i++, myRegisteredUsers.get(key).getFullName()));
			}
	        printSubMenuBackAndExit();
			
			do {
				
				stdout.print(" Please enter your selection: ");
				
				uIn = stdin.nextLine();
				System.out.println("\n");
				int option = 0;
				try {
					option = Integer.parseInt(uIn);
				} catch(NumberFormatException ne) {
					option = 0;
				}
				
				if(option > 0 && option <= unArr.length) {
					opSucc = true;
					backCallee = assignMan(spcs.get(unArr[option - 1]));
					
					// If the user wanted to exit at some point down the in the UI
					// Communicate that up the tree.
					if(backCallee == PageStatus.EXIT) {
						backCaller = PageStatus.EXIT;
					}
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'e') {
					opSucc = true; // Exit the inner loop.
					backCallee = PageStatus.EXIT; // Exit the outer loop.
					backCaller = PageStatus.EXIT; // Tell the caller to exit.
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'b') {
					opSucc = true; // Exit the inner loop.
					backCallee = PageStatus.EXIT; // Exit the inner loop.
					backCaller = PageStatus.BACK; // Tel the caller to hold.
				} else {
					opSucc = false;
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n"); 
				}
			} while(!opSucc);
		} while(backCallee == PageStatus.BACK);
		return backCaller;
	}

	private PageStatus assignMan(SubprogramChair subprogramChair) {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean operationSuccess = false;
		
		do {
			printHeader();
			stdout.println(String.format("Assigning a manuscript to %s.\n", 
										 myRegisteredUsers.get(subprogramChair.getUserName())));
			stdout.println("\n Assigned Manuscripts"
			             + "\n ---------------------");
			
			int i = 1;
			for(Manuscript m : myConference.getSPCsManuscripts(subprogramChair.getUserName())) {
				
				stdout.print(i++);
				stdout.print("> ");
				stdout.println(m.getTitle());
			}
			
			stdout.println("\n Available Manuscripts"
			             + "\n ----------------------");
			i = 1;
			for(Manuscript man : myConference.getManuscripts()) {
				stdout.println(String.format("%d> \"%s\"", i++, man.getTitle()));
			}
	        printSubMenuBackAndExit();
			
			do {
				
				stdout.print("Please enter your selection: ");
				int option = 0;
				uIn = stdin.nextLine();
				System.out.println("\n");
				
				try{
					option = Integer.parseInt(uIn);
				} catch(NumberFormatException ne) {
					option = 0;
				}
				
					
				if(option > 0 && option <= myConference.getManuscripts().size()) {
					
					operationSuccess = true;
					Manuscript paper = myConference.getManuscripts().get(option - 1);
					
					try {
						// If the subprogram chair can be assigned a paper.
						if(myConference.getSPCsManuscripts(subprogramChair.getUserName()).size() < SubprogramChair.MAXPAPERS) {
							paper.setSPCsUsername(subprogramChair.getUserName());
						} else {
							System.out.println("SUBPROGRAM CHAIR CANNOT BE ASSIGNED MORE PAPERS. RETURNING TO MAIN MENU.\n\n");
							backCaller = PageStatus.GOTO_MAIN_MENU;
						}
						
					} catch(Exception e) {
						
						stdout.println(e.getMessage());
						operationSuccess = false;
					}
				} else if(uIn.charAt(0) == 'e') {
					operationSuccess = true; // Exit the inner loop.
					backCallee = PageStatus.EXIT; // Exit the outer loop.
					backCaller = PageStatus.EXIT; // Tell the caller to exit.
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'b') {
					operationSuccess = true; // Exit the inner loop.
					backCallee = PageStatus.EXIT; // Exit the outer loop.
					backCaller = PageStatus.BACK; // Tell the caller to hold.
				} else {
				    operationSuccess = false;
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n"); 
				}
			} while (!operationSuccess);
		} while(backCallee == PageStatus.BACK);
		
		return backCaller;
	}

	private PageStatus displayDesignateSPCMenu() {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		ProgramChair pc = myConference.getProgramChair();
		
		do {
			printHeader();
			
			System.out.println("\n Registered Users"
			               + "\n -----------------");
			
			int i = 0;
			
			String[] unArr = new String[myRegisteredUsers.keySet().size()];
			
			for(String s : myRegisteredUsers.keySet()) {
				unArr[i++] = s;
			}
			
			i = 1;		
			for(String key : unArr) {
				stdout.println(String.format("%d> %s", i++, myRegisteredUsers.get(key)));
			}
	        printSubMenuBackAndExit();
			do {
				
				stdout.print(" Please enter your selection: ");
				uIn = stdin.nextLine();
				System.out.println("\n");
				int option = 0;
				try {
					option = Integer.parseInt(uIn);
				} catch (NumberFormatException ne) {
					option = 0;
				}
				if(option > 0 && option <= unArr.length) {
					opSucc = true;
					pc.addSPC(unArr[option - 1]);
					myConference.assignSubprogramChair(myRegisteredUsers.get(unArr[option - 1]));
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'e') {
					opSucc = true; // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.EXIT; // Tell calling method to retire.
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'b') {
					opSucc = true; // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.BACK; // Tell calling method to hold.
				} else {
					opSucc = false;
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n"); 
				}
			} while(!opSucc);
		} while (backCallee == PageStatus.BACK);
		
		return backCaller;
	}

	private PageStatus acceptRejectManuscript() {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		PageStatus backCallee = PageStatus.GOTO_MAIN_MENU; //Used to control we do based off the actions taken.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean opSucc = false;
		
		do {
			printHeader();
			stdout.println("\n Manuscripts to Accept or Reject");
			stdout.println(" --------------------------------");
			
			int i = 1;
			for(Manuscript man : myConference.getManuscripts()) {
				stdout.println(String.format("%d> \"%s\"", i++, man.getTitle()));
			}
	        printSubMenuBackAndExit();
			
			do {
				
				stdout.print(" Please enter your selection: ");
				int option = 0;
				uIn = stdin.nextLine();
				System.out.println("\n");
				
				try {
					option = Integer.parseInt(uIn);
				} catch(NumberFormatException ne) {
					option = 0;
				}
				
				if(option > 0 && option <= myConference.getManuscripts().size()) {
					
					opSucc = true;
					backCallee = acceptReject(myConference.getManuscripts().get(option - 1));
					
					// If the user wanted to exit, tell the caller.
					if(backCallee == PageStatus.EXIT) {
						backCaller = PageStatus.EXIT;
					}
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'e') {
					opSucc = true; // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.EXIT; // Tell calling method to retire.
				} else if(uIn.length() > 0 && uIn.charAt(0) == 'b') {
					opSucc = true; // Exit inner loop.
					backCallee = PageStatus.EXIT; // Exit outer loop.
					backCaller = PageStatus.BACK; // Tell calling method to hold.
				} else {
                    System.out.println(" Invalid entry. Please enter a valid corresponding"
                                     + " integer or letter value.\n\n");
				}
			} while(!opSucc);
		} while(backCallee == PageStatus.BACK);
		
		return backCaller;
	}

	private PageStatus acceptReject(Manuscript manuscript) {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean operationSuccess = false;
		
		printHeader();
		stdout.println(String.format("\n Accepting/Rejecting \"%s\".\n", manuscript.getTitle()));
		stdout.print(" Current Acceptance Status: ");
		
		AcceptanceStatus status = manuscript.getAcceptance();
		
		switch(status) {
		
		case ACCEPTED: 
			stdout.println("Accepted.");
			break;
		case REJECTED:
			stdout.println("Rejected.");
			break;
		default:
			stdout.println("Pending.");
		}
		
		stdout.println(" 1> Accept\n" + 
					   " 2> Reject");
		printSubMenuBackAndExit();
		
		do {
			
			stdout.print(" Please enter your selection: ");
			uIn = stdin.nextLine();
			System.out.println("\n");
			
			switch(uIn.charAt(0)) {
			
			case '1':
				operationSuccess = true;
				manuscript.setAcceptance(AcceptanceStatus.ACCEPTED);
				break;
			case '2':
				operationSuccess = true;
				manuscript.setAcceptance(AcceptanceStatus.REJECTED);
				break;
			case 'e':
				operationSuccess = true;
				backCaller = PageStatus.EXIT;
				break;
			case 'b':
				operationSuccess = true;
				backCaller = PageStatus.BACK;
			default:
			    operationSuccess = false;
                System.out.println(" Invalid entry. Please enter a valid corresponding"
                                 + " integer or letter value.\n\n"); 
				break;
			}
		} while(!operationSuccess);
		
		return backCaller;
	}

	private PageStatus viewAllPapers() {
		
		PageStatus backCaller = PageStatus.GOTO_MAIN_MENU; //Used to control what the calling method does.
		Scanner stdin = new Scanner(System.in);
		PrintStream stdout = new PrintStream(System.out);
		String uIn;
		boolean operationSuccess = false;
		
		printHeader();
		
		stdout.println("\n AT A GLANCE VIEW: \n");
		
		for(Manuscript m : myConference.getManuscripts()) {
			stdout.println(m.toString());
			RegisteredUser r = myRegisteredUsers.get(m.getSPCsUsername());
			String name = "NONE";
			if(r != null) {
				name = r.getFullName();
			}
			stdout.println(String.format(" SUBPROGRAM CHAIR: %s\n", name));
		}
		
		printSubMenuBackAndExit();
		do {
			
			stdout.print(" Please enter your selection: ");
			uIn = stdin.nextLine();
			System.out.println("\n");
			if(uIn.length() > 0 && uIn.charAt(0) == 'e') {
				operationSuccess = true; // Exit the loop.
				backCaller = PageStatus.EXIT; // Tell the caller to exit.
			} else if(uIn.length() > 0 && uIn.charAt(0) == 'b') {
				operationSuccess = true; // Exit the loop.
				backCaller = PageStatus.BACK; // Tell the caller to hold.
			} else {
			    operationSuccess = false;
                System.out.println(" Invalid entry. Please enter a valid corresponding"
                                 + " integer or letter value.\n\n"); 
			}
		} while(!operationSuccess);
		
		return backCaller;
	}
	
    private void printSubMenuBackAndExit() {
        
        System.out.println(" --"
                       + "\n b> Back"
                       + "\n e> Exit/Logout"
                       + "\n");
    }
}
