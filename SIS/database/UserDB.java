package database;

import model.Student;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class UserDB {

    public static final String DB_FILENAME = "UserRecord.csv";
    public static final String SEPARATOR = ",";


    public static ArrayList<User> readUsers()  {
        ArrayList<String> stringArray;
        ArrayList<User> alr = new ArrayList<User>() ;

        try {
            stringArray = DatabaseIO.read(DB_FILENAME);
            for (int i = 0 ; i < stringArray.size() ; i++) {
                String st = (String)stringArray.get(i);
                StringTokenizer star = new StringTokenizer(st , SEPARATOR);
                String  ID = star.nextToken().trim();
                String  name = star.nextToken().trim();
                String  pass= star.nextToken().trim();
                String  access= star.nextToken().trim();

                User user = new User(ID,name,pass,access);
                // add to list
                alr.add(user) ;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return alr ;
    }

    public static void saveUser(List<User> al)  {
        List<String> alw = new ArrayList<>();
        for (int i = 0 ; i < al.size() ; i++) {
            User stud = (User) al.get(i);
            StringBuilder st =  new StringBuilder();
            st.append(stud.getId().trim());
            st.append(SEPARATOR);
            st.append(stud.getName());
            st.append(SEPARATOR);
            st.append(stud.getPassword());
            st.append(SEPARATOR);
            st.append(stud.getAccess());
//            st.append("\n");
            alw.add(st.toString()) ;
        }

        try {
            DatabaseIO.write(DB_FILENAME,alw);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
