package org.academiadecodigo.bootcamp8.freespeech.server;

import org.academiadecodigo.bootcamp8.freespeech.server.service.user.JdbcUserService;

import java.io.IOException;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> PedroMAlves
 */
public class ServerStart {

    public static void main(String[] args) {

        Server server;

        if (args.length < 1 || Integer.parseInt(args[0]) < 1025) {
            System.out.println("Client app is configured to connect to port 4040.");
            server = new Server();
        } else {
            server = new Server(Integer.parseInt(args[0]));
        }

        server.setUserService(new JdbcUserService());

        try {

            server.init();
            server.start();

        } catch (IOException e) {
            e.printStackTrace(); //TODO log?
        } finally {
            server.stop();
        }
    }
}