package education.stemi.hexapod;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

class PacketSender implements Runnable {

    private final Logger logger = Logger.getLogger(PacketSender.class.getName());

    private Hexapod hexapod;
    private Closeable socket;
    private int sleepingInterval = 200;
    private boolean connected = true;

    PacketSender(Hexapod hexapod) {
        logger.setLevel(Level.ALL);
        this.hexapod = hexapod;
    }

    PacketSender(Hexapod hexapod, int sleepingInterval) {
        this(hexapod);
        this.sleepingInterval = sleepingInterval;
    }

    void stop() {
        logger.info("Stopping PacketSender...");
        this.connected = false;
    }

    @Override
    public void run() {

        try {
            logger.info("Trying to establish a connection with the robot.");
            socket = new Socket(hexapod.ip, 80);
            OutputStream outputStream = getOutputStream();
            BufferedOutputStream buffOutStream = new BufferedOutputStream(outputStream, 30);

            logger.info("Connection established.");

            while(connected) {
                Thread.sleep(sleepingInterval);
                buffOutStream.write(hexapod.curr_packet.toByteArray());
                buffOutStream.flush();
            }

        } catch (IOException e) {
            logger.warning("Socket IOExceptopn while getting OutputStream.");
        } catch (InterruptedException e) {
            logger.info("Socket InterruptedException. PacketSender finishing !");
        } finally {
            try {
                logger.info("Connection with the robot closed.");
                socket.close();
            } catch (IOException e) {
                logger.warning("IOException while closing the socket");
            }
        }
    }

    private OutputStream getOutputStream() throws IOException {
        return ((Socket) socket).getOutputStream();
    }
}
