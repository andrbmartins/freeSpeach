package org.academiadecodigo.bootcamp8.client.service;

import javafx.scene.control.TextField;
import org.academiadecodigo.bootcamp8.client.utils.Values;
import org.academiadecodigo.bootcamp8.message.Message;

import java.io.*;
import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class ClientService {

    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientService() {
        try {
            clientSocket = new Socket(Values.HOST, Values.SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupStreams();
    }

    public void setupStreams() {
        try {
            output = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            input = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("output stream: " + output);
        System.out.println("input stream: " + input);
    }

    public void sendUserText(TextField textField) {
        Message<String> message = new Message(Message.Type.DATA, textField.getText());
        writeObject(message);
        System.out.println("SENT: " + message);
        textField.clear();
        textField.requestFocus();
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void writeObject(Message message){
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message readObject() {
        Object serverMessage = null;
        try {
            serverMessage = input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Message) serverMessage;
    }
}