package managerClasses;

import models.Admin;
import java.util.ArrayList;

public class AdminManager {
    private ArrayList<Admin> admins;
    private FileManager fileManager;

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public AdminManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.admins = new ArrayList<>();
    }

    public boolean login(String userName, String password) {
        for (Admin admin : admins) {
            if (admin.getUserName().equals(userName) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void saveAdminsToFile() {
        if (fileManager!=null && admins!=null) {
            fileManager.writeToFile(admins);
        }
    }

    public void loadAdminsFromFile() {
        ArrayList<Object> objs = fileManager.readFromFile();
        if (objs != null) {
            admins = new ArrayList<>();
            for (Object obj : objs) {
                if (obj instanceof Admin) {
                    admins.add((Admin) obj);
                }
            }
        }
    }

}
