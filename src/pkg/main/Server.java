package pkg.main;

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
    private int mousePositionX;
    private int mousePositionY;
    private boolean switchPressed;
    private static final int SWITCH_BTN = NativeKeyEvent.VC_META;//CMD

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started");
            System.out.println("Waiting for client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            output = new DataOutputStream(socket.getOutputStream());

            switchPressed = false;
            mousePositionX = 0;
            mousePositionY = 0;

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
        if(e.getKeyCode() == SWITCH_BTN) {
            switchPressed = true;
        }
        if(switchToClient(e.getKeyCode())) {
            writeOutput("kp", e.getKeyCode());
        } 
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if(e.getKeyCode() == SWITCH_BTN) {
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
    public void nativeMouseReleased(NativeMouseEvent e) { /*TODO */ }
    @Override
    public void nativeMouseDragged(NativeMouseEvent e) { /* TODO */ }

    private void writeOutput(String action, int code) {
        try {
            output.writeUTF(mousePositionX + "," + mousePositionY + "," + action + "," + code);
            System.out.println(code);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    private boolean switchToClient(int code) {
        return switchPressed && code != SWITCH_BTN;
    }
}