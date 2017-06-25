package org.academiadecodigo.bootcamp8.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp8.client.service.ClientService;
import org.academiadecodigo.bootcamp8.client.utils.Navigation;
import org.academiadecodigo.bootcamp8.client.utils.Values;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientService cs = new ClientService();

        Navigation.getInstance().setClientService(cs);
        Navigation.getInstance().setStage(primaryStage);
        Navigation.getInstance().loadScreen(Values.USER_SCENE);
    }
}