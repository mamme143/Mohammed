package server;

import service.LibraryService;
import service.LibraryServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LibraryServer {
    public static void main(String[] args) {
        try {
            int port = 1099; // Use port 1099 for the RMI registry
            // Create and export the RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI Registry started on port: " + port);

            // Create an instance of the library service
            LibraryService libraryService = new LibraryServiceImpl();
            // Bind the library service to the registry
            registry.rebind("LibraryService", libraryService);

            System.out.println("LibraryService is bound and ready.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}