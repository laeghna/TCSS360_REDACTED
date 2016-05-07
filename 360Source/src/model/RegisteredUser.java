package model;

import java.io.Serializable;

/** Class that provides the functionality for a registered user. 
 * 
 * @author Lisa Taylor
 * @version 6 May 2016
 */
public class RegisteredUser implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7126786747221081727L;

    /** The user's username. */
    private String username;
    
    /** The user's first name. */
    private String firstName;
    
    /** The user's last name. */
    private String lastName;

    /** Constructs a RegisteredUser object. 
     * 
     * @param uname the user's username
     * @param fname the user's first name
     * @param lname the user's last name
     */
    public RegisteredUser(final String uname, final String fname, final String lname) {
        username = uname;
        firstName = fname;
        lastName = lname;
    }
    
    /** Returns the user's username. */
    public String getUsername() {
        return username;
    }
    
    /** Returns the user's first name. */
    public String getFirstName() {
        return firstName;
    }
    
    /** Returns the user's last name. */
    public String getLastName() {
        return lastName;
    }
    
    /** Returns the user's fullname. */
    public String getName() {
        return firstName + " " + lastName;
    }
}
