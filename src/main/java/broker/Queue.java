package broker;

import org.zeromq.ZMQ;

public class Queue {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket frontend = context.socket(ZMQ.ROUTER);
        frontend.bind("tcp://*:5559");

        ZMQ.Socket backend = context.socket(ZMQ.DEALER);
        backend.bind("tcp://*:5560");

        ZMQ.proxy(frontend, backend, null);

        frontend.close();
        backend.close();
        context.term();
    }
}
