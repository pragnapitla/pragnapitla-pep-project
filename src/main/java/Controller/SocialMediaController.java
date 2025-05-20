package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Model.Message;
import Service.SocialMediaService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService service = new SocialMediaService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::createMessage);
        
        app.get("/messages", this::getAllMessages);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);


        app.get("/messages/{message_id}", this::getMessageById);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
private void handleRegister(Context ctx) {
    Account user = ctx.bodyAsClass(Account.class);
        Account registered = service.registerUser(user);
        if (registered == null) {
            ctx.status(400).result("");;
        } else {
            ctx.json(registered);
        }
        
    }
    private void handleLogin(Context ctx) {
        Account user = ctx.bodyAsClass(Account.class);
        Account loginUser = service.loginUser(user);
        if (loginUser != null) {
            ctx.json(loginUser);
        } else {
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message created = service.createMessage(message);
        if (created != null) {
            ctx.json(created);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx) {
    List<Message> messages = service.getAllMessages();
    ctx.json(messages); 
    }

    public void getMessagesByUser(Context ctx) {
    
       int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = service.getMessagesByUser(accountId);
    
        if (messages == null) {
            messages = new ArrayList<>();
        }
        ctx.status(200).json(messages);
}

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = service.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    private void updateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessage = ctx.bodyAsClass(Message.class);
        Message updated = service.updateMessage(messageId, newMessage);
        if (updated != null) {
            ctx.json(updated);
        } else {
            ctx.status(400);
        }
    }

    private void deleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = service.deleteMessageById(messageId);
        if (deleted != null) {
            ctx.json(deleted); 
        } else {
            ctx.status(200).result(""); 
        }
    }

}