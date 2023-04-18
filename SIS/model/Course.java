package model;

public class Course {


    private String name;
    private String number;
    private String credit;
    private String grade;

    public Course(String name, String number, String credit, String grade) {
        this.name = name;
        this.number = number;
        this.credit = credit;
        this.grade = grade;
    }

    public Course(String name, String number, String credit) {
        this.name = name;
        this.number = number;
        this.credit = credit;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
