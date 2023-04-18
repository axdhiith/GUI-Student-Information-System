package controller;

import database.UserDB;
import model.User;

import javax.swing.*;
import java.util.ArrayList;

public class LoginController{

    public static User validateLoginCredential(String pUserID, String pPassword) {

        if(pUserID.trim().equalsIgnoreCase("") || pPassword.trim().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Enter All Entries!!");
            return null;
        }
        //if the user is Customer

            ArrayList<User> al = UserDB.readUsers();
            for (int i = 0; i < al.size(); i++) {
                User login = (User) al.get(i);
                if (login.getName().equals(pUserID)) {
                    if (login.getPassword().equals(pPassword)) {
                        return login;
                    }
                }



        }
        JOptionPane.showMessageDialog(null, "Invalid Credential!");
        return null;
    }

}


