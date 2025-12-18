package dao;

import config.AppConfig;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private final String filePath = AppConfig.NOTIFICATIONS_FILE;

    public NotificationDAO() {
        createFileIfNotExists();
    }

    public void save(String recipientId, String senderId, String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            // الصيغة: RecipientID;SenderID;Message;IsRead
            bw.write(recipientId + ";" + senderId + ";" + message + ";false");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNotificationsRaw(int userId) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4 && parts[0].equals(String.valueOf(userId))) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markAllAsRead(int userId) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4 && parts[0].equals(String.valueOf(userId))) {
                    parts[3] = "true";
                    line = String.join(";", parts);
                }
                lines.add(line);
            }
        } catch (IOException e) { e.printStackTrace(); }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void createFileIfNotExists() {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }
}