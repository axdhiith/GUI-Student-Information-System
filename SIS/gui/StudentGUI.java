package gui;

import controller.StudentController;
import database.StudentDB;
import exception.ApplicationValidation;
import model.Course;
import model.Student;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

public class StudentGUI {
    
    JFrame f;
    private String id;
    private Student student;
    private JTable table_1;
    public StudentGUI(String id) {
        this.id  = id;
        f = new JFrame();
        f.setBounds(100, 100, 700, 400);
        JPanel p1 = new JPanel(new BorderLayout());
//        JPanel p2 = new JPanel();
//        JPanel p3 = new JPanel();
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(0, 0,500,500);
        tp.add("Student Detail", p1);
//        tp.add("visit", p2);
//        tp.add("help", p3);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JButton getDetail = new JButton("Show Detail");
        JButton addCourse = new JButton("Add Course");
        JButton accessInfo = new JButton("Access Information");
        topPanel.add(getDetail);
        topPanel.add(addCourse);
        topPanel.add(accessInfo);
        p1.add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        p1.add(scrollPane, BorderLayout.CENTER);

        List<Student> students = StudentDB.readStudent();
        for (Student studentinDB : students) {
            if (studentinDB.getId().equalsIgnoreCase(id)) {
                student = studentinDB;
            }
        }
        String[] columnNames = {"Name", student.getName(), "Id", student.getId(), "Major", student.getMajor()};
//        String[] columnNames = {"Code", "Name", "Price", "Size", "Inventory type", "Quantity", "Image"};
        Object[][] data = {};
        table_1 = new JTable(new DefaultTableModel(data, columnNames));
        scrollPane.setViewportView(table_1);
        table_1.setFillsViewportHeight(true);
        display();

        accessInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] message = {"Username : " + student.getName() , " Password : " + student.getPassword()};
                JOptionPane.showMessageDialog(null, message);
            }
        });
        getDetail.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                display();
                }

        });

        addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                // set the selection mode to directories only
                j.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // invoke the showsSaveDialog function to show the save dialog
                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    // set the label to the path of the selected directory
                    File file = new File(j.getSelectedFile().getAbsolutePath());
                    try {
                        List<String> alLInes =  Files.readAllLines(Paths.get(j.getSelectedFile().getAbsolutePath()));
                        processCousreLines(alLInes);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }

            }
        });

// Open the save dialog



        f.add(tp);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void display() {
        DefaultTableModel model = (DefaultTableModel) table_1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        table_1.setTableHeader(null);
        adddHeader(row, model);
        addSemesetr(row,model);
        addCoursesHeader(row,model);
        for (int i = 0; i < student.getCourse().size(); i++) {

            row[0] = i+1;
            row[1] = student.getCourse().get(i).getName();
            row[2] = student.getCourse().get(i).getNumber();
            row[3] = student.getCourse().get(i).getCredit();
            row[4] = student.getCourse().get(i).getGrade();
            model.addRow(row);


        }
        addTotal(row,model);
    }

    private void addTotal(Object[] row, DefaultTableModel model) {
        row[0] = "";
        row[1] = "";
        row[2] = "";
        row[3]= "GPA";
        int weightedSum = 0;
        int credits = 0;
        for(Course course : student.getCourse()){
            credits = credits+ Integer.valueOf(course.getCredit());
            weightedSum = weightedSum + ((Integer.valueOf(course.getCredit()))* (Integer.valueOf(course.getGrade())));
        }
        row[4] = weightedSum/credits;
        model.addRow(row);
    }

    private void addCoursesHeader(Object[] row, DefaultTableModel model) {
        row[0] = "Courses";
        row[1] = "Name";
        row[2] = "Number";
        row[3] = "Credits";
        row[4] = "Grades";

        model.addRow(row);
    }

    private void addSemesetr(Object[] row, DefaultTableModel model) {
        row[0] = "Semester";
        row[1] = student.getSemester();
        row[2] = "";
        row[3] = "";
        row[4] = "";
        row[5] = "";
        model.addRow(row);
    }

    private void adddHeader(Object [] row, DefaultTableModel tableModel) {
        row[0] = "Name";
        row[1] = student.getName();
        row[2] = "Id";
        row[3] = student.getId();
        row[4] = "Major";
        row[5] = student.getMajor();
        tableModel.addRow(row);
    }

    private void processCousreLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            StringTokenizer star = new StringTokenizer(lines.get(i), ",");
            String ID = star.nextToken().trim();
            String name = star.nextToken().trim();
            String credit = star.nextToken().trim();
            String grade = star.nextToken().trim();
            Course course = new Course(ID, name, credit, grade);
//            student.getCourse().add(course);
            try {
                StudentController.addCourseToStudent(student.getId(), course);
            } catch (ApplicationValidation applicationValidation) {
                JOptionPane.showMessageDialog(null, applicationValidation.getMessage());
            }
        }

    }

    public JFrame getF() {
        return f;
    }
}
