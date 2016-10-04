package education.stemi.hexapod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * STEMI hexapod robot expects to receive binary messages we called 'Packets'.
 * Since these messages contain a lot of parameters, object 'Packet' is introduced
 * for simplicity. Unchanged, it contains all the values necessary for the robot
 * to stay still. By changing the parameters of Packet we can issue movement
 * commands for the hexapod. This is the short explanation of the parameters
 * and their valid values:
 *
 *  Translational motion:
 *   power - [0..100] speed of the robot
 *   angle - [-180..180] 0 deg -> forward; 90 deg -> right; -90 deg -> left;
 *           180 deg -> back;
 *
 *  Rotational motion:
 *   rotation - [-100..100]; speed and direction of rotation;
 *              [0..100] - clockwise; [-100..0] counterclockwise
 *
 *  Special flags (Tilt bytes are MUTUALLY EXCLUSIVE):
 *   staticTilt - [0,1] 1 -> enable body tilting according to the accelerometer
 *   movingTilt - [0,1] 1 -> enable body tilting while walking *EXPERIMENTAL*
 *   onOff      - [0,1] 1 -> robot operational; 0 -> robot sleeping
 *
 *  Accelerometer (one of the *Tilt bytes must be 1):
 *   Bytes should contain acceleration force in (m/s^2 * 10), saturated at -40 and 40
 *    accX - x axis acceleration
 *    accY - y axis acceleration
 *
 *  Sliders array:
 *   Array of 9 bytes that represent the state of 9 sliders of the Android app.
 *   Has a default value of [50, 25, 0, 0, 0, 0, 0, 0, 0]
 *    slidersArray[0]    - [0..100] robot height
 *    slidersArray[1]    - [0..100] gait
 *    slidersArray[2..8] - [0..255] user defined data; this is where users can
 *                         encode the special messages to the robot. Arduino
 *                         firmware needs to be modified in order to utilize
 *                         these bytes.
 *
 *  duration - [0..65535] specifies how long will a packet be
 *             "executed" on a robot. If 0, the robot will go in
 *             rest state as soon as the timer on robot expires.
 *             Value represents number of cycles, which is 20ms
 *             for STEMI hexapod. As you can see in method toByteArray,
 *             those last two bytes are set to zero, to expire as soon
 *             as possible, in order to have a real-time control over
 *             robot movements.
 *
 * Example of packet "on the wire" (going right while tilting):
 *    1    2    3    4   5   6  7  8  9  10  11  12  13 14 15 16  17 18 19 20 21 22
 * ['P', 'K', 'T', 100,  45, 0, 0, 1, 1,  0,  0, 50, 25, 0, 0, 0, 50, 0, 0, 0, 0, 0]
 *
 */
public class Packet {

    public int power = 0;
    public int angle = 0;
    public int rotation = 0;
    public int staticTilt = 0;
    public int movingTilt = 0;
    public int onOff = 1;
    public int accelerometer_x = 0;
    public int accelerometer_y = 0;
    public int[] sliders_array = {50, 25, 0, 0, 0, 50, 0, 0, 0};
    public int duration = 0; //duration measured in 20ms cycles

    public Packet() {}

    byte[] toByteArray() {
        // dividing angle by 2 in order to pack it into single byte
        byte[] movingHelperArray = {(byte) power, (byte) (angle / 2), (byte) rotation, (byte) staticTilt,
                (byte) movingTilt, (byte) onOff, (byte) accelerometer_x, (byte) accelerometer_y};

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(23);

        try {
            outputStream.write("PKT".getBytes());
            outputStream.write(movingHelperArray);
            for(int i = 0; i < sliders_array.length; i++) outputStream.write((byte) sliders_array[i]);
            outputStream.write((byte) duration / 256);
            outputStream.write((byte) duration % 256);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public static byte[] addRedundantBytes(byte[] arr) {
        //TODO: add a method for adding redundancy bytes
        return arr;
    }

}
