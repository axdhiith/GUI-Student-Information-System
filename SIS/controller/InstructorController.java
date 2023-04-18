package controller;

import database.StudentDB;
import database.UserDB;
import model.User;

import java.util.List;

public class InstructorController {



    public static User getUser(String id){
        List<User> userList = UserDB.readUsers();
        for(User user : userList){
            if(user.getId().equalsIgnoreCase(id)){
                return user;
            }
        }
        return null;
    }

    public static void updateUser(String id, String name, String password){
        List<User> userList = UserDB.readUsers();

        for(User user : userList){
            if(user.getId().equalsIgnoreCase(id)){
                user.setName(name);
                user.setPassword(password);
            }
        }
        UserDB.saveUser(userList);
    }
}
