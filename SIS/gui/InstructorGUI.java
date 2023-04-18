package gui;

import controller.InstructorController;
import database.InstrcutorDB;
import database.StudentDB;
import database.UserDB;
import exception.ApplicationValidation;
import model.Course;
import model.Instructor;
import model.Student;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class InstructorGUI {

    private String id;
    JFrame frame;
    private Instructor instructor;
    private JTable personalTable;
    private JTable courseTable;

    public InstructorGUI(String id) {

        this.id = id;
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 400);
        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel(new BorderLayout());
        JPanel p3 = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel p4 = new JPanel(new GridLayout());
//        JPanel p5 = new JPanel(new GridLayout());
        //        JPanel p2 = new JPanel();
//        JPanel p3 = new JPanel();
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(0, 0, 800, 500);
        tp.add("Personal Detail", p1);
        tp.add("Course Detail", p2);
        tp.add("Add Course", p3);


        JLabel cNameLabel = new JLabel("Course Name");
        JTextField cNameField = new JTextField(10);
        JLabel cNumberLabel = new JLabel("Course Number");
        JTextField cNumberField = new JTextField(10);
        JLabel creditLabel = new JLabel("Credit");
        JTextField creditField = new JTextField(10);
        JLabel label_1 = new JLabel("");
        JButton addCourse = new JButton("Add");
        p3.add(cNameLabel);
        p3.add(cNameField);
        p3.add(cNumberLabel);
        p3.add(cNumberField);
        p3.add(creditLabel);
        p3.add(creditField);
        p3.add(label_1);
        p3.add(addCourse);


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JButton showData = new JButton("Show Data");
        JButton accessInfo = new JButton("Access Information");
        JButton updateUserName = new JButton("Update username");
        JButton updatePassword = new JButton("Update password");
        JButton changeGrade = new JButton("Change password");


        topPanel.add(showData);
        topPanel.add(accessInfo);
        topPanel.add(updateUserName);
        topPanel.add(updatePassword);
        topPanel.add(changeGrade);
        p1.add(topPanel, BorderLayout.NORTH);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        List<Instructor> instructors = InstrcutorDB.readInstructor(id);
        for (Instructor instructorInDb : instructors) {
            if (instructorInDb.getId().equalsIgnoreCase(id)) {
                instructor = instructorInDb;
            }
        }
        String[] columnNames = {"Name", instructor.getName(), "Teacher", instructor.getId(), "Department", instructor.getDepartment()};
        Object[][] data = {};
        personalTable = new JTable(new DefaultTableModel(data, columnNames));
        scrollPane.setViewportView(personalTable);
        personalTable.setFillsViewportHeight(true);
        displayPersonalData();
        p1.add(scrollPane, BorderLayout.CENTER);


        JScrollPane scrollPaneCourse = new JScrollPane();
        scrollPaneCourse.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPaneCourse.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Object[][] coursedata = {};
        courseTable = new JTable(new DefaultTableModel(coursedata, columnNames));
        scrollPaneCourse.setViewportView(courseTable);
        courseTable.setFillsViewportHeight(true);
        displayCourseData();
        p2.add(scrollPaneCourse, BorderLayout.CENTER);




        accessInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] message = {"Username : " + instructor.getName(), " Password : " + instructor.getPassword()};
                JOptionPane.showMessageDialog(null, message);
            }
        });

        updateUserName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog("Enter New userName : ");
                User user = InstructorController.getUser(id);
                user.setName(value);
                InstructorController.updateUser(user.getId(), value, user.getPassword());
            }
        });


        updatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog("Enter New Password : ");
                User user = InstructorController.getUser(id);
                user.setPassword(value);
                InstructorController.updateUser(user.getId(), user.getName(), value);
            }
        });

        addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (instructor.getCourseList().size() < 3) {
                    instructor.getCourseList().add(new Course(cNameField.getText(), cNumberField.getText(), creditField.getText()));
                    JOptionPane.showMessageDialog(null, "Course Added Successfully");
                    creditField.setText("");
                    cNameField.setText("");
                    cNumberField.setText("");

                } else
                    JOptionPane.showMessageDialog(null, "Max Course Limit Btreached");
            }
        });

        changeGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String value = JOptionPane.showInputDialog("Enter Student ID : ");
                    ArrayList<Student> user = StudentDB.readStudent();
                    boolean userFound = false;
                    for (Student student : user) {
                        if (student.getId().equalsIgnoreCase(value)) {
                            userFound = true;
                            String courseId = JOptionPane.showInputDialog("Enter Course ID : ");
                            boolean courseFound = false;
                            for (int j = 0; j < student.getCourse().size(); j++) {
                                if (courseId.equalsIgnoreCase(student.getCourse().get(j).getNumber())) {
                                    courseFound = true;
                                    Integer grade = Integer.valueOf(JOptionPane.showInputDialog("Enter Grade : "));
                                    student.getCourse().get(j).setGrade(String.valueOf(grade));
                                    StudentDB.saveStudent(user);
                                    JOptionPane.showMessageDialog(null, "Grade Updated Successfully");
                                    break;
                                }
                            }
                            if (!courseFound) {
                                JOptionPane.showMessageDialog(null, "No course ID found");
                            }
                        }
                        if (!userFound) {
                            JOptionPane.showMessageDialog(null, "No Student  found");
                        }
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Invalid input");
                }
            }
        });


        frame.add(tp);
        frame.setSize(900, 700);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * Display Course Data
     */
    private void displayCourseData() {
        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        courseTable.setTableHeader(null);
        adddHeader(row, model);
        addSemesetr(row, model);
        for (int i = 0; i < instructor.getCourseList().size(); i++) {
            addCoursesHeaderWithName(row, model, instructor.getCourseList().get(i));
            addStudentHeader(row, model);
            List<Student> students = filterStudentByCourseId(instructor.getCourseList().get(i).getNumber());
            for (int j = 0; j < students.size(); j++) {
                addCourseDetailInfo(row, model, students.get(j));
            }
        }

    }

    /**
     * Add Student header in cousre tabe
     * @param row
     * @param model
     */
    private void addStudentHeader(Object[] row, DefaultTableModel model) {
        row[0] = "ID";
        row[1] = "Name";
        row[2] = "Grade";
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    /**
     * Add course Info in JTable
     * @param row
     * @param model
     * @param student
     */
    private void addCourseDetailInfo(Object[] row, DefaultTableModel model, Student student) {
        row[0] = student.getId();
        row[1] = student.getName();
        row[2] = student.calculateGrade();
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    /**
     * Filter student based on course ID
     * @param courseId
     * @return
     */
    private List<Student> filterStudentByCourseId(String courseId) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < instructor.getStudents().size(); i++) {
            for (int j = 0; j < instructor.getStudents().get(i).getCourse().size(); j++) {
                if (instructor.getStudents().get(i).getCourse().get(j).getNumber().equalsIgnoreCase(courseId)) {
                    students.add(instructor.getStudents().get(i));
                    continue;
                }
            }
        }
        return students;
    }


    /**
     * Add row in JTable for course header
     * @param row
     * @param model
     * @param cousre
     */
    private void addCoursesHeaderWithName(Object[] row, DefaultTableModel model, Course cousre) {
        row[0] = "Course";
        row[1] = cousre.getNumber();
        row[2] = cousre.getName();
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }


    /*
        Display Person data
     */
    private void displayPersonalData() {
        DefaultTableModel model = (DefaultTableModel) personalTable.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        personalTable.setTableHeader(null);
        adddHeader(row, model);
        addSemesetr(row, model);
        addCoursesHeader(row, model);
        addCoursesHeaderLine(row, model);
        for (int i = 0; i < instructor.getCourseList().size(); i++) {
            row[0] = instructor.getCourseList().get(i).getName();
            row[1] = instructor.getCourseList().get(i).getNumber();
            row[2] = "";
            row[3] = "";
            row[4] = "";
            model.addRow(row);
        }

    }

    private void addCoursesHeaderLine(Object[] row, DefaultTableModel model) {
        row[0] = "Name";
        row[1] = "Number";
        row[2] = "";
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    private void addCoursesHeader(Object[] row, DefaultTableModel model) {
        row[0] = "Course";
        row[1] = "";
        row[2] = "";
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    private void addSemesetr(Object[] row, DefaultTableModel model) {
        row[0] = "Term";
        row[1] = instructor.getSemester();
        row[2] = "";
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    private void adddHeader(Object[] row, DefaultTableModel model) {
        row[0] = "Name";
        row[1] = instructor.getName();
        row[2] = "Id";
        row[3] = instructor.getId();
        row[4] = "Department";
        row[5] = instructor.getDepartment();
        model.addRow(row);
    }
}



