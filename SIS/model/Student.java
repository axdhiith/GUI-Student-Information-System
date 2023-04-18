package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    private String major;
    private String semester;
    private List<Course> course;
    public Student(String id, String name, String password, String access) {
        super(id, name, password, access);
        course = new ArrayList<>();
    }

    public Student(User user, String major, String semester) {
        super(user.getId(), user.getName(), user.getPassword(), user.getAccess());
        this.major = major;
        this.semester = semester;
        course = new ArrayList<>();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public int calculateGrade(){
        int weightedSum = 0;
        int credits = 0;
        for(Course course : getCourse()){
            credits = credits+ Integer.valueOf(course.getCredit());
            weightedSum = weightedSum + ((Integer.valueOf(course.getCredit()))* (Integer.valueOf(course.getGrade())));
        }
        if(credits != 0) return weightedSum/credits;
        return 0;
    }
}
