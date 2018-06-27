package pushpul;

import org.zeromq.ZMQ;

public class Receiver {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PULL);
        socket.bind("tcp://*:5558");

        // wait for start signal from producer
        socket.recvStr();

        long timeStart = System.currentTimeMillis();
        for (int reqNo = 0; reqNo < 100; reqNo++) {
            socket.recvStr();
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(String.format("Total time for handling 100 tasks is : %dms", timeEnd - timeStart));

        socket.close();
        context.term();
    }

}
