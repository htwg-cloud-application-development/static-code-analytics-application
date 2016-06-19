package de.htwg.konstanz.cloud.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.RemoteSession;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class Git {
    private static final Logger LOG = LoggerFactory.getLogger(Git.class);

    private String sFileSeparator = "";

    private boolean isValidRepository(URIish repoUri) {
        if (repoUri.isRemote()) {
            return isValidRemoteRepository(repoUri);
        } else {
            return isValidLocalRepository(repoUri);
        }
    }

    private boolean isValidLocalRepository(URIish repoUri) {
        boolean result;

        try {
            result = new FileRepository(repoUri.getPath()).getObjectDatabase().exists();
        } catch (IOException e) {
            result = false;
        }

        return result;
    }

    /*
    String downloadGITRepo(String gitRepo) throws GitAPIException, IOException {
        // Checkout Git-Repo
        org.eclipse.jgit.api.Git git = null;

        // return Array
        String returnValue = null;
        // String Magic
        String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"),
                gitRepo.length()).replace(".", "_");
        String localDirectory = "repositories/" + directoryName + "_"
                + System.currentTimeMillis() + "/";

        // Clone Command with jGIT
        URL f = new URL(gitRepo);
        if (isValidRepository(new URIish(f))) {
            git = git.cloneRepository().setURI(gitRepo)
                    .setDirectory(new File(localDirectory)).call();
            returnValue = localDirectory;
        }
        // Closing Object that we can delete the whole directory later
        git.getRepository().close();

        // Local Targetpath and Last Commit
        return returnValue;
    }
    */

	private String getLastCommit(org.eclipse.jgit.api.Git git) throws IOException, GitAPIException {
        //Get Last Commit of Git Repo
        Iterable<RevCommit> revCommits =git.log().call();
        return String.valueOf(revCommits.iterator().next().getCommitTime());
    }

    String [] downloadGITRepo(String gitRepo) throws GitAPIException, IOException {
        // Checkout Git-Repo
        org.eclipse.jgit.api.Git git = null;
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        sFileSeparator = oOperatingSystemCheck.getOperatingSystemSeparator();

        // return Array
        String[] returnValue = null;

        // String Magic
        String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"),
                gitRepo.length()).replace(".", "_");

        //test den ersten / removen
        directoryName = directoryName.substring(1);

        String localDirectory = "repositories" + sFileSeparator + directoryName + "_"
                + System.currentTimeMillis() + sFileSeparator;

        // Clone Command with jGIT
        URL f = new URL(gitRepo);
        if (isValidRepository(new URIish(f))) {
            git = org.eclipse.jgit.api.Git.cloneRepository().setURI(gitRepo)
                    .setDirectory(new File(localDirectory)).call();
            returnValue= new String[]{localDirectory, getLastCommit(git)};
        }

        // Closing Object that we can delete the whole directory later
        git.getRepository().close();

        // Local Targetpath and Last Commit
        return returnValue;
    }

    private boolean isValidRemoteRepository(URIish repoUri) {
        boolean result;

        if (repoUri.getScheme().toLowerCase().startsWith("http") ) {
            String path = repoUri.getPath();
            URIish checkUri = repoUri.setPath(path);

            InputStream ins = null;
            try {
                URLConnection conn = new URL(checkUri.toString()).openConnection();

                conn.setReadTimeout(1000);
                ins = conn.getInputStream();
                result = true;
            } catch (FileNotFoundException e) {
                LOG.info("URI not found: " + checkUri.toString());
                result=false;
            } catch (IOException e) {
                LOG.info("IO Error: " + checkUri.toString());
                result = false;
                //TODO:
            } finally {
                try {
                    ins.close();
                }
                catch (Exception e) {
                    /* ignore */
                }
            }
        } else if (repoUri.getScheme().toLowerCase().startsWith("ssh") ) {

            RemoteSession ssh = null;
            Process exec = null;

            try {
                ssh = SshSessionFactory.getInstance().getSession(repoUri, null, FS.detect(), 1000);
                exec = ssh.exec("cd " + repoUri.getPath() + "; git rev-parse --git-dir", 1000);

                Integer exitValue = null;
                do {
                    try {
                        exitValue = exec.exitValue();
                    } catch (Exception e) {
                        //TODO:
                    }
                } while (exitValue == null);

                result = exitValue == 0;

            } catch (Exception e) {
                result = false;

            } finally {
                try { exec.destroy(); } catch (Exception e) { /* ignore */ }
                try { ssh.disconnect(); } catch (Exception e) { /* ignore */ }
            }
        } else {
            // TODO need to implement tests for other schemas
            result = true;
        }

        return result;
    }
}
