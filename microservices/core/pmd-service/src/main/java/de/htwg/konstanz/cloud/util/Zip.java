package de.htwg.konstanz.cloud.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * to unpack the downloaded pmd binary files
 */
class Zip {

    void unzipFile(String sZipFile) {
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
