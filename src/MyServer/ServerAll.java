package MyServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
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
            // DataInputStream()
            System.out.println("Сервер запущен!");
//            while (true) {
//                //  Socket socket;
//                try {
//                    mControl.SendMsgServer("Ожидание подключений....");
//                    Socket socket = server.accept();
//
/////                            ClientHandler clientHandler = new ClientHandler(mMyServerAll, socket, clients.size() + 1);
//                    int id = clients.size() + 1;
//                    clients.add(new ClientHandler(mMyServerAll, socket, id));
//                    broadcastMsg("Подключен клиент ID: " + id);
//                } catch (IOException e) {
//                    // Обработать
//                    e.printStackTrace();
//                } // Добавить finaly
//            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                      //  Socket socket;
                        try {
                            mControl.SendMsgServer("Ожидание подключений....");
                          Socket socket = server.accept();
                            int id = clients.size() + 1;
                            clients.add(new ClientHandler(mMyServerAll, socket, id));
                            broadcastMsg("Подключен клиент ID: " + id);
                        } catch (IOException e) {
                            // Обработать
                            e.printStackTrace();
                        } // Добавить finaly
                    }

                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void broadcastMsg(String msg) {
        mControl.SendMsgServer(msg);
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
