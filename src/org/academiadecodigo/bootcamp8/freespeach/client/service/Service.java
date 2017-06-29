package org.academiadecodigo.bootcamp8.freespeach.client.service;

import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> PedroMAlves
 */
public class Service {
    private static Socket clientSocket;
    private static ClientService loginService = new LoginClientService();

    public static ClientService getLoginService() {
        return loginService;
    }


    public static ClientService getTempClientService() {
        return TempClientService.getInstance(clientSocket);
    }

    public static void setClientSocket(Socket clientSocket) {
        Service.clientSocket = clientSocket;
    }

}
