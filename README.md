# STEMI hexapod Java Library

The purpose of this library is to provide high level functions for interacting with
the STEMI hexapod robot. With this library it is really easy to get started with
programming apps for STEMI hexapod. It provides unified API for experienced developers,
and easy-to-use building blocks for programmers that are just starting out.

Look at how easy is to make STEMI hexapod move:

  ```java
import education.stemi.hexapod.Hexapod;

public class GoSTEMI {

    public static void main(String[] args) {

        Hexapod hexapod = new Hexapod();
        hexapod.connect();

        //make a square!
        try {
            Thread.sleep(1000);
            hexapod.goForward();

            Thread.sleep(1000);
            hexapod.goLeft();

            Thread.sleep(1000);
            hexapod.goBackward();

            Thread.sleep(1000);
            hexapod.goRight();            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hexapod.disconnect();
    }
}
  ```

For more information, visit our [website](https://www.stemi.education/).
