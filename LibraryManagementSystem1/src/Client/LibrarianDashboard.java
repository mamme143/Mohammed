package client;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class LibrarianDashboard {
    private LibraryService service;

    public LibrarianDashboard() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Connect to port 1099
            service = (LibraryService) registry.lookup("LibraryService");
            createLibrarianGUI();
            showWelcomeMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLibrarianGUI() {
        JFrame frame = new JFrame("Librarian Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton viewBooksButton = new JButton("View All Books");
        JButton manageLoansButton = new JButton("Manage Loans");

        viewBooksButton.addActionListener(e -> viewBooks());
        manageLoansButton.addActionListener(e -> manageLoans());

        JPanel panel = new JPanel();
        panel.add(viewBooksButton);
        panel.add(manageLoansButton);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to the Librarian Dashboard!");
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

    private void manageLoans() {
        // Implement the loan management functionality
    }

    public static void main(String[] args) {
        new LibrarianDashboard();
    }
}