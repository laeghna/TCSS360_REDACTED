/*****************************************************
 * Group 3: Lisa Taylor, Nathanael Toporek, Anh Tran *
 * TCSS 360, Spring 2016                             *
 * Deliverable #2                                    *
 *****************************************************/

package view;

import java.util.ArrayList;

/** 
 * Class that provides the UI menus for a Subprogram Chair. 
 * 
 * @author Lisa Taylor
 * @version 7 May 2016
 */
public class SubprogramChairUI {
    
    /** The name of the conference. */
    private String myConference;
    
    /** The name of the user. */
    private String myName;
    
    /** The list of manuscripts the Author has submitted to a conference. */
    private ArrayList<Manuscript> myManuscripts;
    
    /** Holds the current menu choice selection. */
    private int mySelection;
    
    public SubprogramChairUI(final String theConference, final String theName) {
        myConference = theConference;
        myName = theName;
        myManuscripts = new ArrayList<Manuscript>();
        mySelection = 0;
    }

    /**
     * Displays the main menu selections.
     */
    public void displayMainMenu() {
        printHeader();
        System.out.println(" Subprogram Chair Options);
        System.out.println(" -------------------------);
        System.out.println(" 1) Assign Manuscript to Reviewer");
        System.out.println(" 2) Submit Manuscript Recommendation");
        System.out.println(" 3) Logout");
        System.out.println();
        do {
            System.out.println("Please enter a selection: \n");
            try {
                mySelection = Integer.parseInt(myScanner.nextLine());
            } catch (InvalidEntryException e){
                System.out.println("Invalid Entry. Must enter a valid corresponding integer. ");
            }
            if (mySelection < 1 || mySelection > 3)
                System.out.println("Invalid Entry. Must enter a valid corresponding integer.");
        } while (mySelection < 0 || mySelection > 3);
        switch(mySelection) {
            case 1:
                displayAssignManuscriptsMenu();
                break;
            case 2:
                displaySubmitRecommendationMenu();
                break;
            case 3:
                //Code here?
                break;
        }
    }
    
    public void displayAssignManuscriptMenu() {
        
    }
    
    public void displaySubmitRecommendationMenu() {
        
    }
}