package education.stemi.hexapod;


public class Hexapod {

    private PacketSender packetSender;

    String ip;
    Packet curr_packet;

    public Hexapod() {
        //default IP of ESP8266 chip
        this.ip = "192.168.4.1";
        this.curr_packet = new Packet();
    }

    public Hexapod(String ip) {
        this();
        this.ip = ip;
    }

    public void connect() {
        packetSender = new PacketSender(this);
        new Thread(packetSender).start();
    }

    public void disconnect() {
        packetSender.stop();
    }

    public void goForward() {
        curr_packet.stopMoving();
        curr_packet.power = 100;
    }

    public void goBackward() {
        curr_packet.stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = (byte) 180;
    }

    public void goLeft() {
        curr_packet.stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = -90;
    }

    public void goRight() {
        curr_packet.stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = 90;
    }

    public void turnLeft() {
        curr_packet.stopMoving();
        curr_packet.rotation = -100;
    }

    public void turnRight() {
        curr_packet.stopMoving();
        curr_packet.rotation = 100;
    }

    public void tiltForward() {
        curr_packet.setMovingTilt();
        curr_packet.accelerometer_x = -30;
    }

    public void tiltBackward() {
        curr_packet.setMovingTilt();
        curr_packet.accelerometer_x = 30;
    }

    public void tiltLeft() {
        curr_packet.setMovingTilt();
        curr_packet.accelerometer_y = -30;
    }

    public void tiltRight() {
        curr_packet.setMovingTilt();
        curr_packet.accelerometer_y = 30;
    }

}
