package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class LibraryServiceImpl extends UnicastRemoteObject implements LibraryService {
    
    private List<String> books;

    public LibraryServiceImpl() throws RemoteException {
        books = new ArrayList<>();
        // Sample books
        books.add("The Great Gatsby");
        books.add("1984");
        books.add("To Kill a Mockingbird");
    }

    @Override
    public List<String> getAllBooks() throws RemoteException {
        return books; // Return the list of all books
    }

    @Override
    public List<String> getAvailableBooks() throws RemoteException {
        // For simplicity, returning all books as available
        return books; // You can implement actual logic to filter available books
    }
    
    private List<User> users; // List to store registered users


    @Override
    public void registerUser(User user) throws RemoteException {
        users.add(user); // Add user to the list
    }

    @Override
    public User login(String username, String password) throws RemoteException {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Return the user if login is successful
            }
        }
        return null; // Return null if login fails
    }
}