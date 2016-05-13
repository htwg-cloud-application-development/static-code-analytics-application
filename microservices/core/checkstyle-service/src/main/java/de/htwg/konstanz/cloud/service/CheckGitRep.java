package de.htwg.konstanz.cloud.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONObject;
import org.json.XML;


/**
 * TODO catch bloecke sinnvoll verarbeiten - Fehlerloggin (console o. Datei)
 */
public class CheckGitRep {
    String gitName = "morph0815";
    String gitRepo = "SOTE1";

    public String startIt() {
        List<List<String>> lRepoList = downloadRepoAndGetPath(gitName, gitRepo);
        return checkStyle(lRepoList);
    }

    public List<List<String>> downloadRepoAndGetPath(String gitName, String gitRepo) {
        List<List<String>> list = new ArrayList<List<String>>();
        // TODO + Timestamp in Ordnernamen
        String localDirectory = gitName + "-" + gitRepo + "/";


        // TODO ueberprüfen ob Repo vorhanden bzw. Giturl okay
        try {
            Git git = Git.cloneRepository()
                    .setURI("https://github.com/" + gitName + "/" + gitRepo)
                    .setDirectory(new File(localDirectory)).call();
        } catch (InvalidRemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (GitAPIException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File mainDir = new File(localDirectory + "/src");

        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();

            for (int i = 0; i < files.length; ++i) {

                File[] filesSub = new File(files[i].getPath()).listFiles();
                List<String> pathsSub = new ArrayList<String>();

                for (int j = 0; j < filesSub.length; ++j) {
                    if (filesSub[j].getPath().endsWith(".java")) {
                        pathsSub.add(filesSub[j].getPath());
                    }
                }

                list.add(pathsSub);
            }
        }

        return list;
    }

    public String checkStyle(List<List<String>> lRepoList) {
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
        final String sRuleSetPath = "/sun_checks.xml";

        for (int i = 0; i < lRepoList.size(); i++) {
            for (int j = 0; j < lRepoList.get(i).size(); j++) {
                String sJSON = "";
                String sFileName = lRepoList.get(i).get(j);

                if (sFileName.endsWith(".java")) {
                    sFileName = sFileName.substring(0, sFileName.length() - 5);
                }

                String sCheckStyleCommand = "java -jar " + sCheckStylePath + " -c " + sRuleSetPath + " " + sFileName + ".java -f xml -o " + sFileName + ".xml";

                try {
                    Runtime runtime = Runtime.getRuntime();
                    Process proc = runtime.exec(sCheckStyleCommand);

                    try {
                        proc.waitFor();
                    } catch (InterruptedException e) {
                    }
                } catch (IOException ex) {
                }


                // TODO Ein grosses JSON Object zurück liefern - das hier ist nur eine tmp Lösung zu testen
                // (liefert nur 1 Ergebnis zurück)
                return xmlToJson(sFileName + ".xml");
                //JSON an Database weitersenden
            }
        }
        return null;
    }

    public String xmlToJson(String sXmlFilePath) {
        String sJSON = "";
        String sXML = "";

        try {
            File oFile = new File(sXmlFilePath);

            if (oFile.exists()) {
                InputStream oInputStream = new FileInputStream(oFile);
                StringBuilder oBuilder = new StringBuilder();
                int nStringPos = 0;

                while ((nStringPos = oInputStream.read()) != -1) {
                    oBuilder.append((char) nStringPos);
                }

                oInputStream.close();

                sXML = oBuilder.toString();
                JSONObject jsonObj = XML.toJSONObject(sXML);
                sJSON = jsonObj.toString();

                System.out.println(sJSON);
            } else {
                System.out.println("Error: Pfad: " + oFile.getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sJSON;
    }
}