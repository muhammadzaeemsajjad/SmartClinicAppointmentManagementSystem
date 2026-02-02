

package managerClasses;

import java.io.*;
import java.util.ArrayList;

public class FileManager implements Serializable {
    private static final long serialVersionUID = 1L;

    
    private String fileName;

     public FileManager(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
   
    
    public void writeToFile(ArrayList<?> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to file", e);
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Object> readFromFile() {
        // FIX: Check if file exists and has data before opening
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            // Return null so the Managers know to start with an empty list
            return null; 
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read data from file", e);
        }
        
    }

    
}