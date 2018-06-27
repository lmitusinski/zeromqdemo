package broker;

import org.zeromq.ZMQ;

import java.util.Random;

public class Worker {

    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.connect("tcp://localhost:5560");

        Random random = new Random(System.currentTimeMillis());
        long myId = random.nextInt(1000) + 1000;

        while (!Thread.currentThread().isInterrupted()) {
            String string = responder.recvStr();
            System.out.println(String.format("Received request: [%s]", string));
            Thread.sleep(1000);
            responder.send(string + "_" + String.valueOf(myId));
        }

        responder.close();
        context.term();
    }
}
