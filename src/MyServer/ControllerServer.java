package MyServer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Vector;

public class ControllerServer {
    @FXML
    TextArea messageList;
    @FXML
    TextField messegeEdit;
    @FXML
    Button sendButton;

    Vector<ClientHandler> clients = new Vector<ClientHandler>();
    private ServerAll server;

    public void SendMsg() {
        SendMsg(messegeEdit.getText());
        messegeEdit.clear();
        messegeEdit.requestFocus();
    }


    public void SendMsgServer(String text) {
        messageList.appendText(text + "\n");
       // System.out.println(text + "\n");
    }

    public void SendMsg(String text) {
      //  messageList.appendText(text + "\n");
        server.broadcastMsgWrite(text);
    }

    public void setServer(ServerAll serverAll) {
        this.server = serverAll;
    }

}
