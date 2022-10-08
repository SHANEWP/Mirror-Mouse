package pkg.main;

import java.awt.Point;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class Server implements NativeKeyListener, NativeMouseMotionListener, NativeMouseListener {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataOutputStream output;
    private int mousePositionX = 0;
    private int mousePositionY = 0;
    private boolean switchPressed = false;
    private boolean focusToggle = false;
    private String focusButtonString = "";
    private static final int SWITCH_BUTTON = NativeKeyEvent.VC_META;//CMD
    private static final int FOCUS_BUTTON = NativeKeyEvent.VC_BACKQUOTE; // `
    private static final Point SERVER_RESOLUTION = new Point(2560, 1600);
    private static final Point CLIENT_RESOLUTION = new Point(2560, 1600);
    private static final double RESOLUTION_X_RATIO = CLIENT_RESOLUTION.getX() / SERVER_RESOLUTION.getX();
    private static final double RESOLUTION_Y_RATIO = CLIENT_RESOLUTION.getY() / SERVER_RESOLUTION.getY();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started");
            System.out.println("Waiting for client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            output = new DataOutputStream(socket.getOutputStream());
            
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.addNativeMouseMotionListener(this);
            GlobalScreen.addNativeMouseListener(this);
        } catch (IOException | NativeHookException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        mousePositionX = e.getX();
        mousePositionY = e.getY();
        System.out.println("(" + mousePositionX + ", " + mousePositionY + ")");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if(e.getKeyCode() == SWITCH_BUTTON) {
            switchPressed = true;
        }
        if(e.getKeyCode() == FOCUS_BUTTON) {
            focusToggle = !focusToggle;
        }
        if(switchToClient(e.getKeyCode())) {
            writeOutput("kp", e.getKeyCode());
        } 
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if(e.getKeyCode() == SWITCH_BUTTON) {
            switchPressed = false;
        }
        if(switchToClient(e.getKeyCode())) {
            writeOutput("kr", e.getKeyCode()); 
        }
    }
    @Override 
    public void nativeMouseClicked(NativeMouseEvent e) {
        if(switchToClient(e.getButton())) {
            writeOutput("mc", e.getButton());
        }
    }
    @Override
    public void nativeMousePressed(NativeMouseEvent e) { 
        if(switchToClient(e.getButton())) {
            writeOutput("mp", e.getButton());
        } 
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { /* TODO */ }
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) { /* TODO */ }
    @Override
    public void nativeMouseDragged(NativeMouseEvent e) { /* TODO */ }

    private void writeOutput(String action, int code) {
        try {
            if (focusToggle) {
                focusButtonString = String.valueOf(FOCUS_BUTTON);
            } else {
                focusButtonString = "-1";
            }
            output.writeUTF( focusButtonString + "," +
                (mousePositionX * RESOLUTION_X_RATIO) + "," + 
                (mousePositionY * RESOLUTION_Y_RATIO) + "," + 
                action + "," + code);
            System.out.println(code);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    private boolean switchToClient(int code) {
        return switchPressed && code != SWITCH_BUTTON;
    }
}