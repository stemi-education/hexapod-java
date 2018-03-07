# STEMI hexapod Java Library

The purpose of this library is to provide high level functions for interacting with
the STEMI hexapod robot. With this library it is really easy to get started with
programming apps for STEMI hexapod. It provides unified API for experienced developers,
and easy-to-use building blocks for programmers that are just starting out.

Note: for compatibility with AppInventor, please use __Oracle Java 7__.

Look at how easy is to make STEMI hexapod move:

  ```java
import education.stemi.hexapod.Hexapod;

public class GoSTEMI {

    public static void main(String[] args) {

        Hexapod hexapod = new Hexapod();
        hexapod.connect();

        //make a 25cm x 25cm square!
        try {
            for(int i = 0; i < 4; i++) {
                hexapod.goForward(0.25f);
                hexapod.turnLeft(90);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hexapod.disconnect();
    }
}
  ```
## Building the library

Building `.class` and `.jar` files in possible using `ant`. Look at the  `build.xml` in the root directory.



For more information, visit our [website](https://www.stemi.education/).
