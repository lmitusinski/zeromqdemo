package broker;

import org.zeromq.ZMQ;

import java.util.Random;

public class Client {
    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5559");

        Random random = new Random(System.currentTimeMillis());
        long myId = random.nextInt(1000) + 1000;

        for (int requestNo = 0; requestNo < 10; requestNo++) {
            requester.send(String.valueOf(myId), 0);
            String reply = requester.recvStr(0);
            System.out.println("Received reply " + requestNo + " [" + reply + "]");
        }

        requester.close();
        context.term();
    }
}
