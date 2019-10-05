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
                    while (true) {

                        String text ="";
                        try {

                                text = mRead.readUTF();
                                //  mRead.reset();
                                messageList.appendText(text + "\n");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            ).start();
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
            mWrite.writeUTF(text);
            messageList.appendText( text+ "\n");
        } catch (IOException e) {
            // В принципе можно сделать несколько попыток !
            messageList.appendText( "Сообщение не прошло !"+ "\n");
            System.out.println("Сообщение не отправлено !");
            e.printStackTrace();
        }

    }
}
