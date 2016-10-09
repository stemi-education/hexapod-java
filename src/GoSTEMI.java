import education.stemi.hexapod.Hexapod;

public class GoSTEMI {

    public static void main(String[] args) {

        Hexapod hexapod = new Hexapod();
        hexapod.connect();

        //make a 25cm x 25cm square!
        for(int i = 0; i < 4; i++) {
            hexapod.goForward(0.25f);
            hexapod.turnLeft(90);
        }

        hexapod.disconnect();
    }
}
