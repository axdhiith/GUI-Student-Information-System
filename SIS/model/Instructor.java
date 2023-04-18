package model;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User{

    private String semester;
    private String department;
    private List<Course> courseList;
    private List<Student> students;

    public Instructor(String id, String name, String password, String access) {
        super(id, name, password, access);
    }

    public Instructor(User user, String semester, String department) {
        super(user.getId(), user.getName(), user.getPassword(), user.getAccess());
        this.semester = semester;
        this.department = department;
        courseList = new ArrayList<>();
        students = new ArrayList<>();

    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
