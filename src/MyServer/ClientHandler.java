package MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private DataOutputStream mWrite;
    private DataInputStream mRead;
    private int ID;
    private Socket socket;
    private ServerAll serv;


    public ClientHandler(ServerAll serv, Socket socket, int id) {
        this.serv = serv;
        ID = id;
        this.socket = socket;
        try {
            this.mRead = new DataInputStream(socket.getInputStream());
            this.mWrite = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String str = mRead.readUTF();
                     ///   mRead.reset();
                        if (str.equalsIgnoreCase("/end")) break;
                        serv.broadcastMsg(str);
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
     //   serv.broadcastMsg("Подключен клиент ID: " + ID);
    }

    public void sendMsg(String msg) {
        try {
            mWrite.writeUTF("Сообщение ID:" + ID + "  " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//
//    public ClientHandler(ServerAll serv, Socket socket, int id,int r) {
//        try {
//            this.serv = serv;
//            ID = id;
//            this.socket = socket;
//            this.mRead = new DataInputStream(socket.getInputStream());
//            this.mWrite = new DataOutputStream(socket.getOutputStream());
//
//          //  mControl.addClient(this);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
//                            String str = mRead.readUTF();
//                            if (str.equalsIgnoreCase("/end")) break; // Проверили конец !
//                            serv.broadcastMsg(str);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            mWrite.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mRead.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
