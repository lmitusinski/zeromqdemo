package hearbeat;

import org.zeromq.ZMQ;

public class Client {

    public static void main (String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = initSocket(context);

        while(!Thread.currentThread().isInterrupted()) {
            socket.send("Heartbeat");
            String response = socket.recvStr();
            if (response == null) {
                socket.close();
                socket = initSocket(context);
            }
            System.out.println(response != null ? response : "Server is dead baby, server is dead");
        }

        socket.close();
        context.term();
    }

    private static ZMQ.Socket initSocket(ZMQ.Context context) {
        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect("tcp://localhost:5555");
        socket.setReceiveTimeOut(1000);
        return socket;
    }
}
