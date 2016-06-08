package de.htwg.konstanz.cloud.service;

import org.apache.commons.codec.binary.Base64;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SVN {

    public String downloadSVNRepo(String svnLink) throws IOException, BadLocationException {
        //Parameters to access svn
        String local = "";
        String name = System.getenv("SVN_USER");
        String password = System.getenv("SVN_PASSWORD");

        if((name != null)&& (password != null)) {

            // URL Proccesing
            String[] parts = svnLink.split("\\/");

            local = local + parts[parts.length - 1];
            local = "repositories/" + local + "_" + System.currentTimeMillis()
                    + "/";
            File dir1 = new File(local);
            dir1.mkdir();

            svnCheckout(
                    svnLink,
                    genAuthString(name, password), local);
        }

        //Local Targetpath
        return local;
    }

    public String genAuthString(String name, String pass) {
        // HTTP Authentication
        String authString = name + ":" + pass;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);

        return authStringEnc;
    }

    public void svnCheckout(String mainURL, String authStringEnc,
                            String localPath) throws FileNotFoundException, IOException, BadLocationException {
        List<String> listValue = new ArrayList<String>();
        // Generate and open the URL Connection
        URL url = new URL(mainURL);
        URLConnection urlConnection = url.openConnection();
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
        HTMLDocument.Iterator iter = htmlDoc.getIterator(HTML.Tag.A);
        listValue = iterToList(iter);

        for (int i = 0; i < listValue.size(); i++) {
            if (java.net.URLDecoder.decode(listValue.get(i), "UTF-8").endsWith("/")) {
                // String Magic
                String[] parts = java.net.URLDecoder.decode(listValue.get(i), "UTF-8").split("\\/");
                String localPathn = localPath + "/" + parts[parts.length - 1];
                // Create new Dir
                new File(localPathn).mkdir();
                // start new logic for the located dir

                svnCheckout(mainURL + "/" + java.net.URLDecoder.decode(listValue.get(i), "UTF-8"), authStringEnc,
                        localPathn);
            } else {
                // download file
                downloadFile(mainURL + "/" + java.net.URLDecoder.decode(listValue.get(i), "UTF-8"), localPath + "/"
                        + java.net.URLDecoder.decode(listValue.get(i), "UTF-8"), authStringEnc);
            }
        }
    }

    public List<String> iterToList(HTMLDocument.Iterator iter) {
        List<String> list = new ArrayList<String>();
        // Get Headstructure of SVN and store it into List
        do {
            list.add(iter.getAttributes().getAttribute(HTML.Attribute.HREF)
                    .toString());
            iter.next();
        } while (iter.isValid());
        // Remove first and last Object because they contain no relevant data
        list.remove(0);
        list.remove(list.size() - 1);
        return list;
    }

    public void downloadFile(String urlString, String dest,
                             String authStringEnc) throws IOException {
        // Authenticate
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic "
                + authStringEnc);
        // Generate InputSTream
        InputStream is = urlConnection.getInputStream();

        FileOutputStream outputStream = null;
        // Start to Download the File and save it locally
        try {
            File fi = new File(dest);
            outputStream = new FileOutputStream(fi);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}