package com.capgemini.gzabek.e02;

import static com.capgemini.gzabek.Util.log;

public class ThreadBuilder {

    public static void main(String[] args) throws InterruptedException {
        //platform thread
        var platformThread = Thread.ofPlatform()
                        .name("platform-", 0)
                                .start( () -> {
                                    log("platform ", ThreadBuilder.class);
                                });
        platformThread.join();

        //virtual thread
        var virtualThread = Thread.ofVirtual()
                .name("virtual-", 0)
                .start(() -> {
                    log("virtual ", ThreadBuilder.class);
        });
        virtualThread.join();
    }
}
