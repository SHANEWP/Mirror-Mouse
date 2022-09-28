package pkg.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String line;
    private DataInputStream input;
    private String[] data;
    private Robot robot;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            robot = new Robot();
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException | AWTException e) {
            System.out.println(e);
        }

        line = "";

        while (line != null) {
            try {
                line = input.readUTF();
                data = line.split(",");
                robot.mouseMove(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                System.out.println("x: "+data[0]+", y: "+data[1]+", action: "+ data[2]+", key: "+data[3]);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
