package reqrep;

import org.zeromq.ZMQ;

public class Server {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {
            String request = socket.recvStr();
            System.out.println(request);
            socket.send("World");
        }
        socket.close();
        context.term();

    }
}
