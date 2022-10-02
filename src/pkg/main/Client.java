package pkg.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
                int code = Integer.parseInt(data[3]);
                robot.mouseMove(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                switch(data[2]) {
                    case "kp": 
                        robot.keyPress(CodeConversions.getKeyConversion(code));
                        break;
                    case "kr": 
                        robot.keyRelease(CodeConversions.getKeyConversion(code));
                         break;
                    case "mp": 
                        mouseAction(CodeConversions.getMouseConversion(code));
                        break;
                    default:
                        break;
                }
                System.out.println("x: "+data[0]+", y: "+data[1]+", action: "+ data[2]+", key: "+data[3]);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(-1);
            }
        } 
    }

    private void mouseAction(int code) {
        switch(code) {
            case 1:
                clickMouse(InputEvent.BUTTON1_DOWN_MASK);
                break;
            case 2: 
                clickMouse(InputEvent.BUTTON2_DOWN_MASK);
                break;
            default:
                clickMouse(InputEvent.BUTTON3_DOWN_MASK);
                break;
        }
    }
    
    private void clickMouse(int code) {
        robot.mousePress(code);
        robot.mouseRelease(code);
    }
}
