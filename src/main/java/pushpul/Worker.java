package pushpul;

import org.zeromq.ZMQ;

public class Worker {

    public static void main (String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        receiver.connect("tcp://localhost:5557");
        sender.connect("tcp://localhost:5558");

        while (!Thread.currentThread().isInterrupted()) {
            String message = receiver.recvStr().trim();
            int workload = Integer.parseInt(message);
            Thread.sleep(workload);
            sender.send("");
        }

        sender.close();
        receiver.close();
        context.close();
    }
}
