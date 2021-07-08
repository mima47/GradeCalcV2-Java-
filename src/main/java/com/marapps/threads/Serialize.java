package com.marapps.threads;

import com.marapps.handler.Handler;
import com.marapps.models.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialize implements Runnable{

    public User user;

    public Serialize(User user){
        this.user = user;
    }

    public void run() {
        Handler.username = user.getUsername();
        Handler.password = user.getPassword();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Handler.pathToHome + "\\login.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        Handler.loggedInUser = user;
    }
}
