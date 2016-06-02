package education.stemi.hexapod;


public class Hexapod {
    public Packet curr_packet;

    private PacketSender packetSender;
    private int power = 100;
    private String ip = "192.168.4.1"; //default IP of ESP8266 chip

    public Hexapod() {
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        if(power < 0) {
            this.power = 0;
        } else if(power > 100) {
            this.power = 100;
        } else {
            this.power = power;
        }
    }

    public void setJoystickParams(int power, int angle) {
        curr_packet.power = power;
        curr_packet.angle = angle;
    }

    public void setJoystickParams(int rotation) {
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
        curr_packet.power = power;
    }

    public void goBackward() {
        stopMoving();
        curr_packet.power = power;
        curr_packet.angle = 180;
    }

    public void goLeft() {
        stopMoving();
        curr_packet.power = power;
        curr_packet.angle = -90;
    }

    public void goRight() {
        stopMoving();
        curr_packet.power = power;
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

    public void frontalTilt(int x) {
        setAccX(x);
        setAccY(0);
    }

    public void sideTilt(int y) {
        setAccX(0);
        setAccY(y);
    }

    public void setAccX(int x) {
        curr_packet.accelerometer_x = x;
    }

    public void setAccY(int y) {
        curr_packet.accelerometer_y = y;
    }

    public void setSlidersArray(int[] arr) {
        System.arraycopy(arr, 0, curr_packet.sliders_array, 0, 9);
    }

}
