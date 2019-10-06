package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea messageList;
    @FXML
    TextField messegeEdit;
    @FXML
    Button sendButton;

    private Socket socket = null;
    final String IP_ADRESS = "localhost";
    final int PORT = 10532;
    private DataInputStream mRead;
    private DataOutputStream mWrite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            mRead = new DataInputStream(socket.getInputStream());
            mWrite = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String text = "";
                            text = mRead.readUTF();
                            messageList.appendText(text + "\n");
                            System.out.println("Сообщение сервера " + text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            mWrite.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mRead.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            // Ошибка открытия сокета клиента
            e.printStackTrace();
        }
    }

    public void SendMsg() {
        SendMsg(messegeEdit.getText());
        messegeEdit.clear();
        messegeEdit.requestFocus();
    }

    public void SendMsg(String text) {

        try {
            messageList.appendText(text + "\n");
            System.out.println(text);
            mWrite.writeUTF(text);

        } catch (IOException e) {
            messageList.appendText("Сообщение не прошло !" + "\n");
            System.out.println("Сообщение не отправлено !");
            e.printStackTrace();
        }

    }
}
