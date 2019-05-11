/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hamp_it.tictactoe_server;

import javax.swing.UIManager;

/**
 *
 * @author Tim
 */
public class TicTacToe_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 50471;
        boolean useGui = true;
        System.out.println("Usage: TicTacToe_Server [port] [nogui]");
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("nogui")) {
                useGui = false;
            } else {
                port = new Integer(args[0]);
            }
        }
        if (args.length >= 2 && args[1].equalsIgnoreCase("nogui")) {
            useGui = false;
        }
        System.out.println("Using port: " + port);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not use system layout, using default java layout instead.");
        }
        
        Server server = new Server(port, useGui);
        server.openServer();
        server.start();
    }
}
