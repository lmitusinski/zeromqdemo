package pubsub;

import org.zeromq.ZMQ;

import java.util.Random;
import java.util.StringTokenizer;

public class Subscribent {
    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.SUB);
        socket.connect("tcp://localhost:5556");

        Random rand = new Random(System.currentTimeMillis());

        String filter = String.valueOf(rand.nextInt(1000)+10000);
        socket.subscribe(filter);

        int sum = 0;
        long msgId = 0;
        for (int updateNo = 0; updateNo < 100; updateNo++) {
            System.out.print(".");
            String message = socket.recvStr().trim();
            StringTokenizer tokenizer = new StringTokenizer(message, " ");
            int code = Integer.valueOf(tokenizer.nextToken());
            int value = Integer.valueOf(tokenizer.nextToken());
            msgId = Long.valueOf(tokenizer.nextToken());
            sum += value;
        }

        System.out.println(String.format("\nAfter 100 messages average value is %d and last message id was %d", sum/100, msgId));
        socket.close();
        context.term();
    }
}
