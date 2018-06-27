package reqrep;

import org.zeromq.ZMQ;

public class Client {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect("tcp://localhost:5555");

        for (int reqNo = 0; reqNo < 10; reqNo++) {
            socket.send("Hello");
            String response = socket.recvStr();
            System.out.println(response);
        }

        socket.close();
        context.term();
    }
}
