package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/sample.fxml"));
        primaryStage.setTitle("Клиент:");
        primaryStage.setScene(new Scene(root, 600, 900));
        primaryStage.show();
        VBox vbox = (VBox) root.lookup("#vbox");
        vbox.setStyle("-fx-background-image: url('images/chat_background.png')");

        Button but = (Button) root.lookup("#sendButton");
        but.setStyle("-fx-background-image:url('/images/send_chat.png')");

        but = (Button) root.lookup("#fotoButton");
        but.setStyle("-fx-background-image:url('/images/foto.png')");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
