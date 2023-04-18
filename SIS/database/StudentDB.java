package database;

import controller.StudentController;

import model.Course;
import model.Student;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StudentDB {

    public static final String DB_FILENAME = "StudentRecord.csv";
    public static final String SEPARATOR = ",";
    static ArrayList<User> users = UserDB.readUsers();


    public static ArrayList<Student> readStudent() {
        ArrayList<String> stringArray;
        ArrayList<Student> alr = new ArrayList<Student>();


        try {
            stringArray = DatabaseIO.read(DB_FILENAME);
            for (int i = 0; i < stringArray.size(); i++) {
                String st = (String) stringArray.get(i);
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);

                    String ID = star.nextToken().trim();
                    User user = getUserFromUserDB(ID);
                    String name1 = star.nextToken().trim();
                    String major = star.nextToken().trim();
                    String semester = star.nextToken().trim();
                    Integer countOFCourse = Integer.valueOf(star.nextToken().trim());
                    Student student = new Student(user,major,semester);
                    int index = i;
                    for(int j = i+1;j <= (index+countOFCourse);j++,i++){
                        String st1 = (String) stringArray.get(j);
                        StringTokenizer star1 = new StringTokenizer(st1, SEPARATOR);
                        String number = star1.nextToken().trim();
                        String name = star1.nextToken().trim();
                         String credit  = star1.nextToken().trim();
                         String garde =  star1.nextToken().trim();
                        Course course = new Course(name, number,credit,garde);
                        student.getCourse().add(course);

                    }
                    i++;
                alr.add(student);


                }
            StudentController.setStudentList(alr);

            }
         catch (Exception ex) {
            ex.printStackTrace();
        }

        return alr;


    }

    private static User getUserFromUserDB(String userId){
        for(User user: users){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    public static void saveStudent(List<Student> al)  {
        List<String> alw = new ArrayList<>();

        for (int i = 0 ; i < al.size() ; i++) {
            Student stud = (Student) al.get(i);
            StringBuilder st =  new StringBuilder();
            st.append(stud.getId().trim());
            st.append(SEPARATOR);
            st.append(stud.getName());
            st.append(SEPARATOR);
            st.append(stud.getMajor());
            st.append(SEPARATOR);
            st.append(stud.getSemester());
            st.append(SEPARATOR);
            st.append(stud.getCourse().size());
            int index = i;
            for(int j = 0; j < stud.getCourse().size();j++){
                st.append("\n");
                st.append(stud.getCourse().get(j).getName().trim());
                st.append(SEPARATOR);
                st.append(stud.getCourse().get(j).getNumber().trim());
                st.append(SEPARATOR);
                st.append(stud.getCourse().get(j).getCredit().trim());
                st.append(SEPARATOR);
                st.append(stud.getCourse().get(j).getGrade().trim());
                st.append(SEPARATOR);

            }

            alw.add(st.toString()) ;
        }

        try {
            DatabaseIO.write(DB_FILENAME,alw);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


}
