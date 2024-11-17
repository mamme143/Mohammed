package client;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class AdminDashboard {
    private LibraryService service;

    public AdminDashboard() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Connect to port 1099
            service = (LibraryService) registry.lookup("LibraryService");
            createAdminGUI();
            showWelcomeMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAdminGUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton viewBooksButton = new JButton("View All Books");
        viewBooksButton.addActionListener(e -> viewBooks());

        JPanel panel = new JPanel();
        panel.add(viewBooksButton);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to the Admin Dashboard!");
    }

    private void viewBooks() {
        try {
            List<String> books = service.getAllBooks();
            StringBuilder bookList = new StringBuilder("Books:\n");
            for (String book : books) {
                bookList.append(book).append("\n");
            }
            JOptionPane.showMessageDialog(null, bookList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}