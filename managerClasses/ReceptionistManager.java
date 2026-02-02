package managerClasses;

import java.util.ArrayList;
// import java.util.Scanner;
import java.util.Collections;

import CustomExceptions.UserAlreadyExistsException;
import models.Receptionist;

public class ReceptionistManager implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Receptionist> receptionists;
    private transient FileManager fileManager;

    public ReceptionistManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.receptionists = new ArrayList<>();
    }

     // ------------------ GETTERS & SETTERS ------------------
    public ArrayList<Receptionist> getReceptionists() {
        return receptionists;
    }

    public void setReceptionists(ArrayList<Receptionist> receptionists) {
        this.receptionists = receptionists;
        fileManager.writeToFile(receptionists);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

   

    public void addReceptionist(Receptionist r) throws UserAlreadyExistsException {
        if (r == null)
            return;

        // Check if a receptionist with the same ID already exists
        for (Receptionist existingReceptionist : receptionists) {
            if (existingReceptionist.getReceptionistId() == r.getReceptionistId()) {
                throw new UserAlreadyExistsException(
                        "Receptionist with ID " + r.getReceptionistId() + " already exists!");
            }
        }

        receptionists.add(r);
        saveReceptionistsToFile();
    }

    public void updateReceptionist(Receptionist r) {
        Receptionist existingReceptionist = findReceptionistById(r.getReceptionistId());
        if (existingReceptionist != null) {
            int index = receptionists.indexOf(existingReceptionist);
            receptionists.set(index, r);
            saveReceptionistsToFile();
        } else {
            throw new IllegalArgumentException("Receptionist with ID " + r.getReceptionistId() + " not found.");
        }
    }

    public Receptionist findReceptionistById(int receptionistID) {
        Receptionist searchReceptionist = null;
        for (Receptionist receptionist : receptionists) {
            if (receptionist.getReceptionistId() == receptionistID) {
                searchReceptionist = receptionist;
            }
        }
        return searchReceptionist;
    }

    public void deleteReceptionist(int receptionistID) {
        Receptionist toDelete = findReceptionistById(receptionistID);
        if (toDelete != null) {
            receptionists.remove(toDelete);
            saveReceptionistsToFile();
        } else {
            throw new IllegalArgumentException("Receptionist with ID " + receptionistID + " not found!");
        }
    }


    public boolean login(String userName, String password) {
        for (Receptionist receptionist : receptionists) {
            if (receptionist.getUserName().equals(userName) && receptionist.getPassword().equals(password)) {
                return true;
            }
        }
        return false;

    }

    public Receptionist findReceptionistByUsername(String username) {
        for (Receptionist receptionist : receptionists) {
            if (receptionist.getUserName().equals(username)) {
                return receptionist;
            }
        }
        return null;
    }

    public void saveReceptionistsToFile() {
        if (fileManager != null && receptionists != null) {
            fileManager.writeToFile(receptionists); // 'receptionists' is your ArrayList<Receptionist>
        }
    }

    public void loadReceptionistsFromFile() {
        ArrayList<Object> objs = fileManager.readFromFile();
        if (objs != null) {
            receptionists = new ArrayList<>();
            for (Object obj : objs) {
                if (obj instanceof Receptionist) {
                    receptionists.add((Receptionist) obj);
                }
            }
        }
    }

    public void sortReceptionistsByName() {
        Collections.sort(receptionists); // Uses the compareTo method in Receptionist class
    }

}
