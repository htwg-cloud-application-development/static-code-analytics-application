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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class Git {
    private static final Logger LOG = LoggerFactory.getLogger(Git.class);

    private String getLastCommit(org.eclipse.jgit.api.Git git) throws IOException, GitAPIException {
        //Get Last Commit of Git Repo
        Iterable<RevCommit> revCommits = git.log().call();
        return String.valueOf(revCommits.iterator().next().getCommitTime());
    }

    String[] downloadGitRepo(String gitRepo) throws IOException, GitAPIException {
        //Second Parameter changes the local Target-Path
        return downloadGitRepo(gitRepo, null);
    }

    String[] downloadGitRepo(String gitRepo, String sPcdString) throws GitAPIException, IOException {
        // Checkout Git-Repo
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        String sFileSeparator = oOperatingSystemCheck.getOperatingSystemSeparator();

        // Return Array
        String[] returnValue = null;

        // String Magic
        String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"),
                gitRepo.length()).replace(".", "_");
        directoryName = directoryName.substring(1);
        String localDirectory;
        if (sPcdString == null) {
            //Build Local Target-Path
            localDirectory = "repositories" + sFileSeparator + directoryName + "_"
                    + System.currentTimeMillis() + sFileSeparator;
        } else {
            //Build Local Target-Path
            localDirectory = "repositories" + sFileSeparator + sPcdString + sFileSeparator + directoryName + sFileSeparator;
        }
        LOG.info(localDirectory);
        // Clone Command with jGIT
        URL f = new URL(gitRepo);
        if (isValidRepository(new URIish(f))) {
            org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.cloneRepository().setURI(gitRepo)
                    .setDirectory(new File(localDirectory)).call();
            returnValue = new String[]{localDirectory, getLastCommit(git)};

            // Closing Object that we can delete the whole directory later
            git.getRepository().close();
        }
        // Local Targetpath and Last Commit
        return returnValue;
    }

    private boolean isValidRepository(URIish repoUri) {
        //Logic to Validate the Repository-URI
        if (repoUri.isRemote()) {
            return isValidRemoteRepository(repoUri);
        } else {
            return isValidLocalRepository(repoUri);
        }
    }

    private boolean isValidLocalRepository(URIish repoUri) {
        //Check Repository-URI
        boolean result;
        try {
            result = new FileRepository(repoUri.getPath()).getObjectDatabase().exists();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    private boolean isValidRemoteRepository(URIish repoUri) {
        boolean result;
        /*  Check Repository URI with different schemes. more schems can be added in future */
        if (repoUri.getScheme().toLowerCase().startsWith("http")) {
            result = httpValidation(repoUri);
        } else if (repoUri.getScheme().toLowerCase().startsWith("ssh")) {
            result = sshValidation(repoUri);
        } else {
            // TODO need to implement tests for other schemas
            /* Not necessary at the Moment */
            result = true;
        }

        return result;
    }

    private boolean sshValidation(URIish repoUri) {
        boolean result;/* SSH-Validation */
        RemoteSession ssh = null;
        Process exec = null;

        try {
            /* Check SSH-Connection */
            ssh = SshSessionFactory.getInstance().getSession(repoUri, null, FS.detect(), 1000);
            exec = ssh.exec("cd " + repoUri.getPath() + "; git rev-parse --git-dir", 1000);

            Integer exitValue;
            do {
                exitValue = exec.exitValue();
            }
            while (false);

            result = exitValue == 0;

        } catch (Exception e) {
            result = false;

        } finally {
            /* Close Process */
            if (exec != null) {
                try {
                    exec.destroy();
                } catch (Exception e) {
                    /* ignore */
                }
            }
            /* Disconnect SSH */
            if (ssh != null) {
                try {
                    ssh.disconnect();
                } catch (Exception e) {
                    /* ignore */
                }
            }
        }
        return result;
    }

    private boolean httpValidation(URIish repoUri) {
        boolean result;
        String path = repoUri.getPath();
        URIish checkUri = repoUri.setPath(path);

        InputStream ins = null;
        try {
            /* Check Connection */
            URLConnection conn = new URL(checkUri.toString()).openConnection();
            conn.setReadTimeout(1000);
            ins = conn.getInputStream();
            result = true;
        } catch (Exception e) {
            /* URI NOT FOUND */
            LOG.info("Error: " + checkUri.toString());
            result = false;
        } finally {
            /* Close InputStream  */
            try {
                if (null != ins) {
                    ins.close();
                }
            } catch (Exception e) {
                /* ignore */
            }
        }
        return result;
    }
}
