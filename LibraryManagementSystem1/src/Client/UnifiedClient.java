package client;

import model.Book;
import model.User;
import service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UnifiedClient {
    private LibraryService service;

    public UnifiedClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1066);
            service = (LibraryService) registry.lookup("LibraryService");
            createMainGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMainGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        String[] roles = {"Admin", "Member", "Librarian"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        registerButton.addActionListener(e -> createRegistrationForm((String) roleComboBox.getSelectedItem()));
        loginButton.addActionListener(e -> createLoginForm((String) roleComboBox.getSelectedItem()));

        JPanel rolePanel = new JPanel();
        rolePanel.add(new JLabel("Select Role:"));
        rolePanel.add(roleComboBox);
        frame.add(rolePanel);
        frame.add(registerButton);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    private void createRegistrationForm(String role) {
        JFrame registerFrame = new JFrame("Register as " + role);
        registerFrame.setSize(300, 300);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLayout(new GridLayout(5, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        registerFrame.add(new JLabel("ID:"));
        registerFrame.add(idField);
        registerFrame.add(new JLabel("Name:"));
        registerFrame.add(nameField);
        registerFrame.add(new JLabel("Username:"));
        registerFrame.add(usernameField);
        registerFrame.add(new JLabel("Password:"));
        registerFrame.add(passwordField);
        registerFrame.add(registerButton);

        registerButton.addActionListener(e -> {
            try {
                User user = new User(idField.getText(), nameField.getText(),
                        usernameField.getText(), new String(passwordField.getPassword()), role.toLowerCase());
                service.registerUser(user);
                JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                registerFrame.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        registerFrame.setVisible(true);
    }

    private void createLoginForm(String role) {
        JFrame loginFrame = new JFrame("Login as " + role);
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User user = service.login(usernameField.getText(), new String(passwordField.getPassword()));
                    if (user != null) {
                        JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                        loginFrame.dispose();
                        // Open corresponding dashboard based on user role
                        switch (user.getRole()) {
                            case "admin":
                                new AdminDashboard();
                                break;
                            case "librarian":
                                new LibrarianDashboard();
                                break;
                            case "member":
                                new MemberDashboard();
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials or role!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new UnifiedClient();
    }
}