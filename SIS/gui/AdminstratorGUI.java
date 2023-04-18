package gui;

import database.UserDB;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminstratorGUI {

    String id;

    JLabel userLabel;
    JFrame frame;
    AdminstratorGUI(){
        this.id  = id;
        userLabel = new JLabel("User Id");
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 400);
        JPanel p1 = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Search user");
        JTextField text = new JTextField(20);
        JButton search = new JButton("Search");
        JPanel top = new JPanel(new FlowLayout());
        top.add(label);
        top.add(text);
        top.add(search);
        p1.add(top, BorderLayout.NORTH);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userFound = null;
                for(User user : UserDB.readUsers()) {
                    if (user.getId().equalsIgnoreCase(text.getText())) {
                        userFound = user;
                    }
                }

                if(userFound!= null){
                    if(userFound.getAccess().equalsIgnoreCase("instructor")){
                        frame.dispose();
                        new InstructorGUI(userFound.getId());
                        InstructorGUI window = new InstructorGUI(userFound.getId());
                        window.frame.setVisible(true);
                    }else{

                        frame.dispose();
                        StudentGUI window = new StudentGUI(userFound.getId());
                        window.getF().setVisible(true);
                    }
                }
                if(userFound == null) {
                    JOptionPane.showMessageDialog(null, "User Not found");
                }
            }
        });


        frame.getContentPane().add(p1, BorderLayout.CENTER);
        frame.setVisible(true);


//
    }

}
