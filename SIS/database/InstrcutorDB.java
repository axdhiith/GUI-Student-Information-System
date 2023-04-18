package database;

import controller.StudentController;
import model.Course;
import model.Instructor;
import model.Student;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InstrcutorDB {

    public static final String DB_FILENAME = "InstructorRecord.csv";
    public static final String SEPARATOR = ",";

    private static List<Instructor> instructors = new ArrayList<>();
    private static List<Student> studentList = StudentDB.readStudent();
    private static List<Course> courseList = CourseDB.readCourses();
    private static List<User> users = UserDB.readUsers();

    public InstrcutorDB() {

    }
    public static List<Instructor> readInstructor(String id){
        User user = getUserFromUserDB(id);
        ArrayList<String> stringArray;
        ArrayList<Instructor> alr = new ArrayList<Instructor>();

        try {
            stringArray = DatabaseIO.read(DB_FILENAME);
            for (int i = 0; i < stringArray.size(); i++) {
                String st = (String) stringArray.get(i);
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);

                String ID = star.nextToken().trim();
                String department = star.nextToken().trim();
                String semester = star.nextToken().trim();
                Integer countOFCourse = Integer.valueOf(star.nextToken().trim());

                Instructor instructor = new Instructor(user,semester,department);
                int index = i;
                for(int j = i+1; j <= (index+ countOFCourse);j++,i++){
                    String st1 = (String) stringArray.get(j);
                    StringTokenizer star1 = new StringTokenizer(st1, SEPARATOR);
                    String courseId = star1.nextToken().trim();
                    Course course = getCourseFromCoutrseList(courseId);
                    instructor.getCourseList().add(course);
                    List<Student> students = getStudentOfParticularCourse(courseId);
                    instructor.getStudents().addAll(students);
                }
                i++;
                alr.add(instructor);
            }
//            StudentController.setStudentList(alr);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return alr;




    }

    private static List<Student> getStudentOfParticularCourse(String courseId) {
        List<Student> students = new ArrayList<>();
        for(Student student: studentList){
            for(Course course : student.getCourse()){
                if(course.getNumber().equalsIgnoreCase(courseId)){
                    students.add(student);
                }
            }
        }
        return students;
    }

    private static Course getCourseFromCoutrseList(String courseId) {
        for(Course course: courseList){
            if(course.getNumber().equalsIgnoreCase(courseId))
                return course;
            }
        return null;
    }

    private static User getUserFromUserDB(String userId){
        for(User user: users){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }


}
