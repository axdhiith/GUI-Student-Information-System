package gui;

import controller.LoginController;
import gui.StudentGUI;
import model.Student;
import model.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class LoginGUI {

    public JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI window = new LoginGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblUserId = new JLabel("User ID");
        panel.add(lblUserId);

        textField = new JTextField();
        textField.setText("");
        panel.add(textField);
        textField.setColumns(10);


        JLabel lblPassowrd = new JLabel("Passowrd");
        panel.add(lblPassowrd);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel label_1 = new JLabel("");
        panel.add(label_1);

        JLabel label_9 = new JLabel("");
        panel.add(label_9);
        JLabel label_10 = new JLabel("");
        panel.add(label_10);


        JButton btnNewButton_1 = new JButton("Log In");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userID =textField.getText().toString();
                String pass=String.valueOf(passwordField.getPassword());


//                if(userID.equals("Ahmed") && pass.equals("2021") && type.equalsIgnoreCase("Admin")){
//                    try {
//                        Admin window = new Admin();
//                        window.frame.setVisible(true);
//                        frame.dispose();
//                        return;
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                }

                User user= LoginController.validateLoginCredential(userID, pass);
                if(user != null && user.getAccess().equalsIgnoreCase("student")) {
                    frame.dispose();
                    try {
                        StudentGUI window = new StudentGUI(user.getId());
                        window.getF().setVisible(true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }

                if(user != null && user.getAccess().equalsIgnoreCase("instructor")) {
                    frame.dispose();
                    try {
                        InstructorGUI window = new InstructorGUI(user.getId());
                        window.frame.setVisible(true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }

                if(user != null && user.getAccess().equalsIgnoreCase("admin")) {
                    frame.dispose();
                    try {
                        AdminstratorGUI window = new AdminstratorGUI();
                        window.frame.setVisible(true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
        panel.add(btnNewButton_1);

//        JLabel label_10 = new JLabel("");
//        panel.add(label_10);

        JLabel label_11 = new JLabel("");
        panel.add(label_11);

        JLabel label_2 = new JLabel("");
        panel.add(label_2);

        JLabel label_3 = new JLabel("");
        panel.add(label_3);

        JLabel label_4 = new JLabel("");
        panel.add(label_4);

        JLabel label_12 = new JLabel("");
        panel.add(label_12);

        JLabel label_13 = new JLabel("");
        panel.add(label_13);

        JLabel label_14 = new JLabel("");
        panel.add(label_14);

        JPanel panel_1 = new JPanel();
        frame.getContentPane().add(panel_1, BorderLayout.WEST);

        JPanel panel_2 = new JPanel();
        frame.getContentPane().add(panel_2, BorderLayout.EAST);

        JPanel panel_3 = new JPanel();
        frame.getContentPane().add(panel_3, BorderLayout.SOUTH);

        JPanel panel_4 = new JPanel();
        frame.getContentPane().add(panel_4, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Student Information System");
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        lblNewLabel.setBackground(Color.LIGHT_GRAY);
        panel_4.add(lblNewLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
