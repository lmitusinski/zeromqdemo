package pubsub;

import org.zeromq.ZMQ;

import java.util.Random;

public class Publisher {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:5556");

        Random rand = new Random(System.currentTimeMillis());
        long messageId = 0;
        while (true) {
            int code = 10000 + rand.nextInt(10000);
            int value = rand.nextInt(100);
            socket.send(String.format("%d %d %d", code, value, messageId++));
        }
    }
}
