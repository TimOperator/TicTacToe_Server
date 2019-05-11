/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hamp_it.tictactoe_server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Tim
 */
public final class Server extends Thread {

    private int port;
    private ServerSocket serverSocket;
    private ServerFrame gui;
    private Field field;
    private Player player1;
    private Player player2;
    private String winner;
    private boolean useGui;

    public Server(int port, boolean useGui) {
        field = new Field();
        field.resetField();
        if (useGui) {
            this.useGui = useGui;
            gui = new ServerFrame();
            gui.setVisible(true);
        }
        this.port = port;
        show("Welcome to the Tic Tac Toe multiplayer server");
        try {
            serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(80000000);
        } catch (Exception ex) {
            show("Unable to open server socket cause: " + ex.toString());
        }
    }

    public void openServer() {
        show("Connect to the server: " + port);
        player1 = new Player(FieldStatusEnum.X);
        try {
            player1.setSocket(serverSocket.accept());
        } catch (IOException ex) {
            show("Unable to open socket to client 1");
        }
        player1.setName(player1.receive());
        show("Player " + player1.getName() + " connected");
        show(player1.send(player1.getSign().toString()));
        show(player1.send("Waiting for 2nd player."));

        player2 = new Player(FieldStatusEnum.O);
        try {
            player2.setSocket(serverSocket.accept());
        } catch (IOException ex) {
            show("Unable to open socket to client 2");
        }
        player2.setName(player2.receive());
        show("Player " + player2.getName() + " connected");
        show(player2.send(player2.getSign().toString()));
        show(player1.send(player2.getName()));
        show(player2.send("Player 2 already connected"));
        show(player2.send(player1.getName()));
    }

    private boolean isWon() {
        FieldStatusEnum stat = field.isWon();
        if (stat == FieldStatusEnum.NONE) {
            if (field.isDraw()) {
                winner = "";
            } else {
                return false;
            }
        } else if (stat == FieldStatusEnum.X) {
            winner = player1.getName();
        } else {
            winner = player2.getName();
        }
        return true;

    }

    private void setPos(int x, int y, Player p) {
        player1.send("SET_POS");
        player1.send("" + x);
        player1.send("" + y);
        player1.send(p.getSign().toString());
        player2.send("SET_POS");
        player2.send("" + x);
        player2.send("" + y);
        player2.send(p.getSign().toString());
    }

    public void reset() {
        field.resetField();
        player1 = null;
        player2 = null;
        winner = "";
    }
    
    private void show(String text) {
        if (useGui) {
            gui.append(text);
        } else {
            System.out.println(text);
        }
    }

    @Override
    public void run() {
        while (true) {
            show("Starting game with " + player1.getName() + " and " + player2.getName());
            while (true) {

                show("It's " + player1.getName() + " turn");
                player2.send("WAIT_FOR_TURN");
                player1.send("MAKE_A_TURN");
                int x = new Integer(player1.receive());
                int y = new Integer(player1.receive());
                show("Got Position fom " + player1.getName() + " x: " + x + " y: " + y);
                field.setFieldPos(x, y, player1.getSign());

                player1.send("SET_POS");
                player1.send("" + x);
                player1.send("" + y);
                player1.send(player1.getSign().toString());
                player2.send("SET_POS");
                player2.send("" + x);
                player2.send("" + y);
                player2.send(player1.getSign().toString());
                //setPos(x, y, player1);

                show(player1.getName() + " made his turn");

                if (isWon()) {
                    break;
                }

                show("It's " + player2.getName() + " turn");

                player1.send("WAIT_FOR_TURN");
                player2.send("MAKE_A_TURN");
                x = new Integer(player2.receive());
                y = new Integer(player2.receive());
                field.setFieldPos(x, y, player2.getSign());

                player1.send("SET_POS");
                player1.send("" + x);
                player1.send("" + y);
                player1.send(player2.getSign().toString());
                player2.send("SET_POS");
                player2.send("" + x);
                player2.send("" + y);
                player2.send(player2.getSign().toString());
                //setPos(x, y, player2);
                show(player1.getName() + " made his turn");

                if (isWon()) {
                    break;
                }
            }
            show("Player " + winner + " won!");
            player1.send("STOP");
            player2.send("STOP");
            player1.send(winner);
            player2.send(winner);
            
            reset();
            openServer();
        }
    }
}
