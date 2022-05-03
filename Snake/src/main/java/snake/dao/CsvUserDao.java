package snake.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.scene.paint.Color;
import snake.domain.User;

public class CsvUserDao implements UserDao {

    private List<User> users;
    private String filePath;

    public CsvUserDao(String path) {
        this.users = new ArrayList<>();
        this.filePath = path;
        
        try {
            File file = new File(path);
            file.createNewFile();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    this.users.add(new User(UUID.fromString(parts[0]), parts[1], 
                            Color.web(parts[2])));
                }
            }
        } catch (IOException e) {
            System.err.println("An IO error occurred while reading a file:");
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void add(User user) throws Exception {
        users.add(user);
        
        // No caching, save directly
        this.save();
    }
    
    @Override
    public boolean remove(User user) throws Exception {
        boolean contained = this.users.remove(user);
        if (!contained) {
            return false;
        }
        
        this.save();
        return true;
    }

    @Override
    public List<User> getAll() {
        return users;
    }
    
    @Override
    public User getByName(String username) {
        for (User u : this.users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        
        return null;
    }
    
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(this.filePath))) {
            for (User u : this.users) {
                writer.write(u.getId() + ";" + u.getUsername() + ";" + u.getColor().toString() + "\n");
            }
        }
    }
}
