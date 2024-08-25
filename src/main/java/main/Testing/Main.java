package main.java.main.Testing;

import main.java.main.Server.Server;

public class Main {

    public static void main(String[] args) {
        mainThread().start();
    }
    private static Thread mainThread() {
        return new Thread(() -> {
            Server.MakeServer(8080, "main.java.main.Testing.routes");
        });
    }

}

