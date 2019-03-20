/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author timro
 */
public class Player {
    private final FieldStatusEnum sign;
    private Socket socket;
    private String name;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    
    public Player(FieldStatusEnum sign) {
        this.sign = sign;
    }
    public FieldStatusEnum getSign() {
        return this.sign;
    }
    public String getName() {
        return this.name;
    }
    public Socket getSocket() {
        return this.socket;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String setSocket(Socket s) {
        this.socket = s;
        try {
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            return "Opened IO-Stream";
        } catch (Exception e) {
            return "Unable to open Data Input/Output Stream cause:" + e.toString();
        }
    }
    public String send(String text) {
        try {
            dataOut.writeUTF(text);
            return "send: " + text;
        } catch (IOException e) {
            return "Unable to send message:" + text + " | cause: " + e.toString();
        }
    }
    public String receive() {
        try {
            return dataIn.readUTF();
        } catch (IOException e) {
            return "Unable to receive message cause: " + e.toString();
        }
    }
    public String close() {
        dataIn = null;
        dataOut = null;
        try {
            socket.close();
            return "";
        } catch (IOException ex) {
            return "Unable to close connection cause: " + ex.toString();
        }
    }
}
