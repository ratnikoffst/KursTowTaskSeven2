package MyServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;


//     1. Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения,
//        как на клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать «Привет»,
//        нажать Enter, то сообщение должно передаться на сервер и там отпечататься в консоли.
//        Если сделать то же самое на серверной стороне, то сообщение передается клиенту и печатается у него в консоли.
//        Есть одна особенность, которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд.
//        Такую ситуацию необходимо корректно обработать.
//        Разобраться с кодом с занятия: он является фундаментом проекта-чата
//        *ВАЖНО! * Сервер общается только с одним клиентом, т.е. не нужно запускать цикл, который будет ожидать второго/третьего/n-го клиентов
//
//        2. Решить проблему с ошибками на стороне сервера, при октлючении клиента

public class MyServer extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Сервер:");
        URL location = getClass().getResource("/layout/sampleServer.fxml");
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(location.openStream());
        ControllerServer controllerServer = loader.getController();
        //    Scene scene = new Scene(root, 960, 600);
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.show();

        VBox vbox = (VBox) root.lookup("#vbox");
        vbox.setStyle("-fx-background-image: url('images/server_background_cat.png')"); //chat_background.png')");

        Button but = (Button) root.lookup("#sendButton");
        but.setStyle("-fx-background-image:url('/images/send_chat.png')");
        but = (Button) root.lookup("#fotoButton");
        but.setStyle("-fx-background-image:url('/images/foto.png')");
        new ServerAll(controllerServer);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
