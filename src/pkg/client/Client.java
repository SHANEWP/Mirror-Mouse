package pkg.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import pkg.utility.CodeConversions;

public class Client {
    private Socket socket;
    private String line = "";
    private DataInputStream input;
    private String[] data;
    private Robot robot;
    private int focusButton = 0;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            robot = new Robot();
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException | AWTException e) {
            System.out.println(e);
        }

        run(); 
    }

    private void run() {
        while (line != null) {
            try {
                line = input.readUTF();
                data = line.split(",");

                focusButton = CodeConversions.getKeyConversion(Integer.parseInt(data[0]));
                
                if(!data[0].equals("-1")) {
                    robot.keyPress(focusButton);
                } else {
                    robot.keyRelease(focusButton);
                }

                robot.mouseMove((int)(Double.parseDouble(data[1]) + 0.5), (int)(Double.parseDouble(data[2]) + 0.5));

                int code = Integer.parseInt(data[4]);
                switch(data[3]) {
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
                System.out.println("~:" + data[0] + "x: "+data[1]+", y: "+data[2]+", action: "+ data[3]+", key: "+data[4]);
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
