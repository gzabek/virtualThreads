package com.capgemini.gzabek;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Util {

    public static void log(String message, Class name) {
        final Logger logger = LoggerFactory.getLogger(name);
        logger.info("{} | " + message, Thread.currentThread());
    }
}
