package database;

import controller.StudentController;
import model.Course;
import model.Student;
import model.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CourseDB {

    public static final String DB_FILENAME = "Course.csv";
    public static final String SEPARATOR = ",";

    public static ArrayList<Course> readCourses() {
        ArrayList<String> stringArray;
        ArrayList<Course> alr = new ArrayList<Course>();
        try {
            stringArray = DatabaseIO.read(DB_FILENAME);
            for (int i = 0; i < stringArray.size(); i++) {
                String st = (String) stringArray.get(i);
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);

                String number = star.nextToken().trim();
                String name = star.nextToken().trim();
                String credit = star.nextToken().trim();
                Course course = new Course(name, number, credit);
                alr.add(course);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return alr;


    }
}
