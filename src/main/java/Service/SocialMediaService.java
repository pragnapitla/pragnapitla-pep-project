
package Service;

import DAO.UserDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaService {

    private UserDAO userDAO;
    private MessageDAO messageDAO;

    public SocialMediaService() {
        this.userDAO = new UserDAO();
        this.messageDAO = new MessageDAO();
        
    }

    public Account registerUser(Account user) {
    if (user.getUsername() == null || user.getUsername().trim().isEmpty()
            || user.getPassword() == null || user.getPassword().length() < 5) {
        return null; // Invalid input
    }

    // Check if username already exists
    if (userDAO.getUserByUsername(user.getUsername()) != null) {
        return null; // Duplicate username
    }
    return userDAO.registerUser(user);    
    }
    public Account loginUser(Account user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return null;
        }
        return userDAO.getUserByUsernameandpassword(user.getUsername(), user.getPassword());
    }
    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()
                || message.getMessage_text().length() > 255) {
            return null;
        }
        if (message.getPosted_by() <= 0) {
            return null;
        }

        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {

        List<Message> messages = messageDAO.getAllMessages();
        return messages != null ? messages : new ArrayList<>();
        //return messageDAO.getAllMessages();
    }
    public List<Message> getMessagesByUser(int accountId) {
        List<Message> messages = messageDAO.getMessagesByUser(accountId);
    return messages != null ? messages : new ArrayList<>();
        //return messageDAO.getMessagesByUser(accountId);
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
    

    public Message updateMessage(int id, Message newMessageData) {
        // Only message_text is expected to change
        if (newMessageData.getMessage_text() == null || newMessageData.getMessage_text().trim().isEmpty()
                || newMessageData.getMessage_text().length() > 255) {
            return null;
        }
        Message existingMessage = messageDAO.getMessageById(id);
        if (existingMessage == null) {
         return null;
        }
        return messageDAO.updateMessage(id, newMessageData.getMessage_text());
    }

    public Message deleteMessageById(int messageId) {
        // First, check if the message exists
        Message msg = messageDAO.getMessageById(messageId);
        if (msg != null) {
            messageDAO.deleteMessage(messageId); // delete from DB
            return msg;
        }
        return null;
    }


} 