package controller;

import database.StudentDB;
import exception.ApplicationValidation;
import model.Course;
import model.Student;

import java.util.List;

public class StudentController {

    private static List<Student> studentList;

    public StudentController() {

    }

    public static List<Student> getStudentList() {
        return studentList;
    }

    public static void setStudentList(List<Student> studentList) {
        StudentController.studentList = studentList;
    }

    public void addStudent(Student student){
        studentList.add(student);
    }

    public static void addCourseToStudent(String studentId, Course course) throws ApplicationValidation {
        for(Student student : studentList){
            if(student.getId().equalsIgnoreCase(studentId)){
                if(student.getCourse().size() == 5) throw new ApplicationValidation("Course Limit excedded");
                student.getCourse().add(course);
            }
        }
        StudentDB.saveStudent(studentList);
    }


}
