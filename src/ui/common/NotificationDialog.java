package ui.common;

import dao.NotificationDAO;
import dao.UserDAO;
import models.User;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificationDialog extends JDialog {

    public NotificationDialog(Frame owner, User user) {
        super(owner, "Inbox - " + user.getUsername(), true);
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // الألوان والتصميم (زي ما انت عاملها)
        Color bgLight = new Color(240, 240, 240);
        getContentPane().setBackground(bgLight);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        
        // --- الربط بالداتا ---
        NotificationDAO dao = new NotificationDAO();
        UserDAO userDAO = new UserDAO();
        List<String> rawNotifs = dao.getNotificationsRaw(user.getId());

        if (rawNotifs.isEmpty()) {
            model.addElement("📭 No notifications.");
        } else {
            for (int i = rawNotifs.size() - 1; i >= 0; i--) {
                String line = rawNotifs.get(i);
                String[] parts = line.split(";");
                // parts: 0=Reciever, 1=Sender, 2=Msg, 3=IsRead
                
                String senderId = parts[1];
                User sender = userDAO.findById(Integer.parseInt(senderId));
                String senderName = (sender != null) ? sender.getUsername() : "Unknown";
                
                String msg = parts[2];
                boolean isRead = Boolean.parseBoolean(parts[3]);
                
                String icon = isRead ? "📖 " : "📬 ";
                model.addElement(icon + "From " + senderName + ": " + msg);
            }
        }

        // زرار Mark as Read
        JButton markReadBtn = new JButton("✅ Mark all as read");
        markReadBtn.setBackground(new Color(70, 130, 180));
        markReadBtn.setForeground(Color.WHITE);
        
        markReadBtn.addActionListener(e -> {
            dao.markAllAsRead(user.getId());
            JOptionPane.showMessageDialog(this, "All marked as read!");
            dispose();
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(markReadBtn, BorderLayout.SOUTH);
    }
}
