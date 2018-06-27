package hearbeat;

import org.zeromq.ZMQ;

public class Server {

    public static void main (String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {
            String request = socket.recvStr();
            System.out.println(request);
            socket.send("World");
            Thread.sleep(500);
        }
        socket.close();
        context.term();

    }
}
