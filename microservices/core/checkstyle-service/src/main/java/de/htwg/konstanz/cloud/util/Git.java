package de.htwg.konstanz.cloud.util;

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

public class Git {
    private static final Logger LOG = LoggerFactory.getLogger(Git.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    /**
     * Collects the last Commit of the Repo
     * @param git - current git-Object
     * @return - Committimestamp
     * @throws IOException - throw for the handling in CheckstyleService
     * @throws GitAPIException - throw for the handling in CheckstyleService
     */
    private String getLastCommit(org.eclipse.jgit.api.Git git) throws IOException, GitAPIException {
        //Get Last Commit of Git Repo
        Iterable<RevCommit> revCommits = git.log().call();
        return String.valueOf(revCommits.iterator().next().getCommitTime());
    }

    /**
     * Download Git-Repo to an Localpath
     * @param gitRepo - HTTP-Link to Git-Repo
     * @return - Array with localPath and Committimestamp
     * @throws IOException - throw for the handling in CheckstyleService
     * @throws GitAPIException - throw for the handling in CheckstyleService
     */
    public String[] downloadGitRepo(String gitRepo) throws GitAPIException, IOException {
        /* Checkout Git-Repo */
        org.eclipse.jgit.api.Git git = null;

        /* return Array */
        String[] returnValue = null;

        /* String Magic */
        String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"),
                gitRepo.length()).replace(".", "_");
        String localDirectory = "repositories" + oOperatingSystemCheck.getOperatingSystemSeparator() + directoryName + "_"
                + System.currentTimeMillis() + oOperatingSystemCheck.getOperatingSystemSeparator();

        /* Clone Command with jGIT */
        URL f = new URL(gitRepo);
        if (isValidRepository(new URIish(f))) {
            git = org.eclipse.jgit.api.Git.cloneRepository().setURI(gitRepo)
                    .setDirectory(new File(localDirectory)).call();
            returnValue = new String[]{localDirectory, getLastCommit(git)};
        }

        /* Closing Object that we can delete the whole directory later */
        if (null != git) {
            git.getRepository().close();
        }
        /* Local Targetpath and Last Commit*/
        return returnValue;
    }

    /**
     * Validates the given repoURI
     * @param repoUri - HTTP-Link to Git-Repo
     * @return - validation
     */
    private boolean isValidRepository(URIish repoUri) {
        if (repoUri.isRemote()) {
            return isValidRemoteRepository(repoUri);
        } else {
            return isValidLocalRepository(repoUri);
        }
    }

    /**
     * Validates the given Local-Repository
     * @param repoUri - HTTP-Link to Git-Repo
     * @return - validation
     */
    private boolean isValidLocalRepository(URIish repoUri) {
        boolean result;

        try {
            result = new FileRepository(repoUri.getPath()).getObjectDatabase().exists();
        } catch (IOException e) {
            result = false;
        }

        return result;
    }

    /**
     * Validates the given Remote-Repository
     * @param repoUri - HTTP-Link to Git-Repo
     * @return - validation
     */
    private boolean isValidRemoteRepository(URIish repoUri) {
        boolean result;
        /*  Check Repository URI */
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

    /**
     * Validates the given ssh-Repository
     * @param repoUri - HTTP-Link to Git-Repo
     * @return - validation
     */
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
            try {
                if (exec != null) {
                    exec.destroy();
                }
            } catch (Exception e) {
                /* ignore */
            }
            /* Disconnect SSH */
            try {
                if (ssh != null) {
                    ssh.disconnect();
                }
            } catch (Exception e) {
                /* ignore */
            }
        }
        return result;
    }

    /**
     * HTTP-Validation
     * @param repoUri - HTTP-Link to Git-Repo
     * @return - validation
     */
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
        } catch (FileNotFoundException e) {
            /* URI NOT FOUND */
            LOG.info("URI not found: " + checkUri.toString());
            result = false;
        } catch (IOException e) {
            /* IO ERROR */
            LOG.info("IO Error: " + checkUri.toString());
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