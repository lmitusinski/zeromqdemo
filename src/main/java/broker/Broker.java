package broker;

import org.zeromq.ZMQ;

public class Broker {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket frontend = context.socket(ZMQ.ROUTER);
        ZMQ.Socket backend = context.socket(ZMQ.DEALER);
        frontend.bind("tcp://*:5559");
        backend.bind("tcp://*:5560");

        ZMQ.Poller items = context.poller(2);
        items.register(frontend, ZMQ.Poller.POLLIN);
        items.register(backend, ZMQ.Poller.POLLIN);

        boolean more = false;
        byte[] message;

        while (!Thread.currentThread().isInterrupted()) {
            items.poll();
            if (items.pollin(0 )) {
                while (true) {
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();
                    backend.send(message, more ? ZMQ.SNDMORE : 0);
                    if(!more) {
                        break;
                    }
                }
            }
            if (items.pollin(1)) {
                while (true) {
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    frontend.send(message, more ? ZMQ.SNDMORE : 0);
                    if(!more) {
                        break;
                    }
                }
            }
        }
        frontend.close();
        backend.close();
        context.term();
    }
}
