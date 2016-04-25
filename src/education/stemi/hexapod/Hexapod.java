package education.stemi.hexapod;


public class Hexapod {

    private PacketSender packetSender;

    String ip;
    public Packet curr_packet;

    public Hexapod() {
        //default IP of ESP8266 chip
        this.ip = "192.168.4.1";
        this.curr_packet = new Packet();
    }

    public Hexapod(String ip) {
        this();
        this.ip = ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getIP() {
        return ip;
    }

    public void setJoystickParams(byte power, byte angle) {
        curr_packet.power = power;
        curr_packet.angle = angle;
    }

    public void setJoystickParams(byte rotation) {
        curr_packet.rotation = rotation;
    }

    public void connect() {
        packetSender = new PacketSender(this);
        new Thread(packetSender).start();
    }

    public void disconnect() {
        packetSender.stop();
        curr_packet = new Packet();
    }

    public void goForward() {
        stopMoving();
        curr_packet.power = 100;
    }

    public void goBackward() {
        stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = 180;
    }

    public void goLeft() {
        stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = -90;
    }

    public void goRight() {
        stopMoving();
        curr_packet.power = 100;
        curr_packet.angle = 90;
    }

    public void turnLeft() {
        stopMoving();
        curr_packet.rotation = -100;
    }

    public void turnRight() {
        stopMoving();
        curr_packet.rotation = 100;
    }

    public void tiltForward() {
        setMovingTilt();
        curr_packet.accelerometer_x = -30;
    }

    public void tiltBackward() {
        setMovingTilt();
        curr_packet.accelerometer_x = 30;
    }

    public void tiltLeft() {
        setMovingTilt();
        curr_packet.accelerometer_y = -30;
    }

    public void tiltRight() {
        setMovingTilt();
        curr_packet.accelerometer_y = 30;
    }

    public void stopMoving() {
        curr_packet.power = 0;
        curr_packet.angle = 0;
        curr_packet.rotation = 0;
    }

    public void turnOn() {
        curr_packet.onOff = 1;
    }

    public void turnOff() {
        curr_packet.onOff = 0;
    }

    public void resetMovingParams() {
        curr_packet.power = 0;
        curr_packet.angle = 0;
        curr_packet.rotation = 0;
        curr_packet.staticTilt = 0;
        curr_packet.movingTilt = 0;
        curr_packet.onOff = 1;
        curr_packet.accelerometer_x = 0;
        curr_packet.accelerometer_y = 0;
    }

    public void setMovingTilt() {
        disableTilt();
        curr_packet.movingTilt = 1;
    }

    public void setStaticTilt() {
        disableTilt();
        curr_packet.staticTilt = 1;
    }

    public void disableTilt() {
        curr_packet.staticTilt = 0;
        curr_packet.movingTilt = 0;
    }

    public void setAccX(byte x) {
        curr_packet.accelerometer_x = x;
    }

    public void setAccY(byte y) {
        curr_packet.accelerometer_y = y;
    }

    public void setSlidersArray(byte[] arr) {
        System.arraycopy(arr, 0, curr_packet.sliders_array, 0, 9);
    }

}
