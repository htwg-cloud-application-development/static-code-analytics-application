package de.htwg.konstanz.cloud.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Svn {
    /*
    Actually the Checkout from Subversion should be realized with the LIB svnkit but these Lib wasn't able to
    connect to the Server because of Deprecated Methods --> The other possibility was to perform an CMD-Command
    and checkout the Repository. That solution wont work anyway because the Command dont determine because of Java
    For this Reason the following Workaround is developed:
     */
    private static final Logger LOG = LoggerFactory.getLogger(Svn.class);

    private String sFileSeparator = "";

    /**
     * Logic to Store the SVN-Repo locally with default Pass
     * They are stored in the config file
     * @param svnLink - SVN Server ip address
     */
    public String downloadSvnRepo(String svnLink) throws IOException, BadLocationException {
        //Default TargetPath
        return downloadSvnRepo(svnLink, null);
    }

    /**
     * Logic to Store the SVN-Repo locally
     * @param svnLink - HTTP-Link to SVN-Repository
     * @param sPcdString - CPD-Path
     * @return - Path to the Local Checkout
     * @throws IOException - throw for the handling in PMDService
     * @throws BadLocationException - throw for the handling in PMDService
     */
    public String downloadSvnRepo(String svnLink, String sPcdString) throws IOException, BadLocationException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        sFileSeparator = oOperatingSystemCheck.getOperatingSystemSeparator();
        //Parameters to access svn
        String local = "";
        String name = System.getenv("SVN_USER");
        String password = System.getenv("SVN_PASSWORD");
        File dir = new File("repositories");
        //Check if MainDirectory exists
        if (dir.exists()) {
            LOG.info("Main Directory " + dir.toString() + " already exists");
        }
        //if not create Dir
        else {
            if (!dir.mkdir()) {
                LOG.info("Error by making Directory");
            }
        }
        //Check VPN Credentials
        if (name == null && password == null) {
            LOG.info("invalid VPN credentials");
        } else {
            /* Split URL at every Slash */
            String[] parts = svnLink.split("/");

            local = local + parts[parts.length - 1];
            if(sPcdString == null){
                local = "repositories" + sFileSeparator + local + "_" + System.currentTimeMillis() + sFileSeparator;
            }
            else {
                local = sPcdString + sFileSeparator + local + "_" + System.currentTimeMillis() + sFileSeparator;
            }
            File dir1 = new File(local);

            if (dir1.mkdir()) {
                svnCheckout(svnLink, genAuthString(name, password), local);
            }
        }

        return local;
    }

    /**
     * Generates the authString from the given VPN Credentials
     * @param name - VPN Username
     * @param pass - VPN Pass
     * @return -  authString
     */
    private String genAuthString(String name, String pass) {
        // HTTP Authentication
        String authString = name + ":" + pass;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());

        return new String(authEncBytes);
    }

    /**
     * Method to checkout an given Repository recursivly
     * @param mainUrl - current HTTP-Link
     * @param authStringEnc - VPN Credentials
     * @param localPath - current local-Path
     * @throws IOException - throw for the handling in PMDService
     * @throws BadLocationException - throw for the handling in PMDService
     */
    private void svnCheckout(String mainUrl, String authStringEnc, String localPath)
            throws IOException, BadLocationException {
        // Generate and open the URL Connection
        URL url = new URL(mainUrl);
        URLConnection urlConnection = url.openConnection();
        //Authenticate
        urlConnection.setRequestProperty("Authorization", "Basic "
                + authStringEnc);
        // read HTML File
        BufferedReader br = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream()));
        // Generate Iterator to walk through the HTML File
        HTMLEditorKit editorKit = new HTMLEditorKit();
        HTMLDocument htmlDoc = new HTMLDocument();
        htmlDoc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
        editorKit.read(br, htmlDoc, 0);
        //Get Strings from HTML-TAG A
        HTMLDocument.Iterator iter = htmlDoc.getIterator(HTML.Tag.A);
        List<String> listValue = iterToList(iter);
        //Start Crawling
        for (String aListValue : listValue) {
            //Directory
            if (URLDecoder.decode(aListValue, "UTF-8").endsWith("/")) {
                /*  String Magic
                    CMD-Command for Checkstyle Validation can't handle Blanks in the Source-Paths
                     --> Class-Files should contain no blank anyway, so they are Replaced by ""
                */
                String[] parts = URLDecoder.decode(aListValue, "UTF-8").split("/");
                String localPathN = (localPath + sFileSeparator + parts[parts.length - 1]).replaceAll(" ", "");

                // Create new Dir
                if (!new File(localPathN).mkdir()) {
                    LOG.info("Error by making Directory");
                }

                // Start recursiv Call for the located dir
                svnCheckout(mainUrl + sFileSeparator + aListValue, authStringEnc,
                        localPathN);
            } else {
                /*  Download File
                    CMD-Command for Checkstyle Validation can't handle Blanks in the Source-Paths
                     --> Class-Files should contain no blank anyway, so they are Replaced by ""
                */
                downloadFile(mainUrl + sFileSeparator + aListValue, (localPath
                        + sFileSeparator + URLDecoder.decode(aListValue, "UTF-8")).replaceAll(" ", ""), authStringEnc);
            }
        }
    }

    /**
     * Adjust the collected iterator
     * @param iter - current HTTP-Link
     * @return - Iterator of current HTML Document
     */
    private List<String> iterToList(HTMLDocument.Iterator iter) {
        List<String> list = new ArrayList<>();
        // Get Headstructure of Svn and store it into List
        do {
            list.add(iter.getAttributes().getAttribute(HTML.Attribute.HREF)
                    .toString());
            iter.next();
        } while (iter.isValid());
        /*  Remove first and last Object because they contain no relevant data
            --> .. & SVN-Information
        */
        list.remove(0);
        list.remove(list.size() - 1);
        return list;
    }

    /**
     * Adjust the collected iterator
     * @param urlString - HTTP-Link to File
     * @param dest - Localdestination
     * @param authStringEnc - Credentials
     * @throws IOException - throw for the handling in PMDService
     */
    private void downloadFile(String urlString, String dest,
                              String authStringEnc) throws IOException {
        // Authenticate
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic "
                + authStringEnc);
        // Generate InputStream
        InputStream is = urlConnection.getInputStream();

        FileOutputStream outputStream = null;
        // Start to Download the File and save it Locally
        try {
            File fi = new File(dest);
            outputStream = new FileOutputStream(fi);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            /* ignore */
        } finally {
            //Close InputStream
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                /* ignore */
                }
            }
            //close Outputstream
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    /* ignore */
                }
            }
        }
    }
}