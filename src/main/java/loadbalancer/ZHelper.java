package loadbalancer;

import org.zeromq.ZMQ;

import java.util.Random;

public class ZHelper {
    private static Random rand = new Random(System.currentTimeMillis());

    public static void setId(ZMQ.Socket sock) {
        String identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        sock.setIdentity(identity.getBytes(ZMQ.CHARSET));
    }
}
