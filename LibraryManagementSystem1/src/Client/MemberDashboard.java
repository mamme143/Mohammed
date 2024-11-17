package client;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class MemberDashboard {
    private LibraryService service;

    public MemberDashboard() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Connect to port 1099
            service = (LibraryService) registry.lookup("LibraryService");
            createMemberGUI();
            showWelcomeMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMemberGUI() {
        JFrame frame = new JFrame("Member Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton viewAvailableBooksButton = new JButton("View Available Books");
        JButton requestBookButton = new JButton("Request a Book");

        viewAvailableBooksButton.addActionListener(e -> viewAvailableBooks());
        requestBookButton.addActionListener(e -> requestBook());

        JPanel panel = new JPanel();
        panel.add(viewAvailableBooksButton);
        panel.add(requestBookButton);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to the Member Dashboard!");
    }

    private void viewAvailableBooks() {
        try {
            List<String> availableBooks = service.getAvailableBooks(); // Fetch available books
            StringBuilder bookList = new StringBuilder("Available Books:\n");
            for (String book : availableBooks) {
                bookList.append(book).append("\n");
            }
            JOptionPane.showMessageDialog(null, bookList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestBook() {
        // Implement the book request functionality
    }

    public static void main(String[] args) {
        new MemberDashboard();
    }
}