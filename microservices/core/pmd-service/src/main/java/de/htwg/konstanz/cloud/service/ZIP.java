package de.htwg.konstanz.cloud.service;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Zip {

    public void unzipFile(String sZipFile) {
        try {
            ZipFile oZipFile = new ZipFile(sZipFile);

            if (!oZipFile.isEncrypted()) {
                oZipFile.extractAll(System.getProperty("user.dir"));
            }

        }
        catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
