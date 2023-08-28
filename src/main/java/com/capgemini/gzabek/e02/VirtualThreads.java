package com.capgemini.gzabek.e02;

import static com.capgemini.gzabek.Util.log;

public class VirtualThreads {

    public static void main(String[] args) throws InterruptedException {

        //platform thread
        var platformThread = new Thread(() -> {
            log("platform ", VirtualThreads.class);
        });
        platformThread.start();
        platformThread.join();

        //virtual thread
        var virtualThread = Thread.startVirtualThread(() -> {
            log("virtual ", VirtualThreads.class);
        });
        virtualThread.join();
    }
}
