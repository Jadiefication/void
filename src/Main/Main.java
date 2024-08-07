package Main;

import Server.Server;

import java.net.ServerSocket;

public class Main {

    public static ServerSocket MainServer;

    public static void main(String[] args){
        Server.MakeServer(8080);
    }
}
