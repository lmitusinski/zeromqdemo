package pushpul;

import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.Random;

public class Producer {

    public static void main (String[] args) throws IOException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5557");
        ZMQ.Socket control = context.socket(ZMQ.PUSH);
        control.connect("tcp://localhost:5558");

        Random rand = new Random(System.currentTimeMillis());
        System.out.println("Press any key to start");
        System.in.read();
        control.send("");

        int totalTime = 0;
        for (int taskNo = 0; taskNo < 100; taskNo++) {
            int workload = rand.nextInt(100) + 1;
            totalTime+= workload;
            sender.send(String.valueOf(workload));
        }
        System.out.println(String.format("Expected cost: %dms", totalTime));
        sender.close();
        control.close();
        context.term();

    }
}
