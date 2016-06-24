package de.htwg.konstanz.cloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class ReadStream implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ReadStream.class);

    private final String name;

    private final InputStream is;

    ReadStream(String name, InputStream is) {
        this.name = name;
        this.is = is;
    }

    void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                LOG.info("[" + name + "] " + s);
            }
            is.close();
        } catch (Exception ex) {
            LOG.info("Problem reading stream " + name + "... :" + ex);
            ex.printStackTrace();
        }
    }
}