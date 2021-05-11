import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String[] args) {
        Agent agent = new Agent();
        // game loop
        while (true) {
            agent.read();
            agent.think();
            agent.print();
        }
    }
}