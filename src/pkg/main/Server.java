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

public class Server implements NativeKeyListener, NativeMouseMotionListener {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataOutputStream output;
    private int mousePositionX;
    private int mousePositionY;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started");
            System.out.println("Waiting for client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            output = new DataOutputStream(socket.getOutputStream());

            mousePositionX = 0;
            mousePositionY = 0;

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.addNativeMouseMotionListener(this);
        } catch (IOException | NativeHookException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        try {
            output.writeUTF(mousePositionX+","+mousePositionY+",kp,"+e.getKeyCode());
            System.out.println(e.getKeyCode());
        } catch (IOException e1) {
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
    public void nativeMouseDragged(NativeMouseEvent e) { /* TODO */ }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) { /* TODO */ }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { /* TODO */ }
}