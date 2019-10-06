package MyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerAll {
    private final int PORT = 10532;
    private final ControllerServer mControl;
    private final ServerAll mMyServerAll;
    private ServerSocket server = null;
    private Vector<ClientHandler> clients;

    public ServerAll(ControllerServer controllerServer) {
        mControl = controllerServer;
        clients = new Vector<>();
        mMyServerAll = this;
        mControl.setServer(this);
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            mControl.SendMsgServer("Ожидание подключений....");
                            Socket socket = server.accept();
                            int id = clients.size() + 1;
                            clients.add(new ClientHandler(mMyServerAll, socket, id));
                            broadcastMsgWrite("Подключен клиент ID: " + id);
                        } catch (IOException e) {
                            // Обработать
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMsgRead(int ID, String msg) {
        mControl.SendMsgServer("ID "+ID+":"+msg);
        System.out.println("ID "+ID+": "+msg);
    }

    public void broadcastMsgWrite(String msg) {
        mControl.SendMsgServer(msg);
        for (ClientHandler o : clients) {
            try {
                System.out.println(msg);
                o.sendMsg(msg);
            } catch (IOException e) {
                System.out.println("ID "+o.getID() +": Сообщение не прошло !");
                mControl.SendMsgServer("ID "+o.getID() +": Сообщение не прошло !");
                e.printStackTrace();
            }
        }
    }
}
