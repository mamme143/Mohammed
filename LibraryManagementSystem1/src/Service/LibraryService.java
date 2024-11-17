package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.User;

public interface LibraryService extends Remote {
    List<String> getAllBooks() throws RemoteException; // Method to get all books
    List<String> getAvailableBooks() throws RemoteException; // Method to get available books
    void registerUser(User user) throws RemoteException; // Method for user registration
    User login(String username, String password) throws RemoteException; // Method for user login
}