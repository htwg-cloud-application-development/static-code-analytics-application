package de.htwg.konstanz.cloud.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import java.util.ArrayList;
import java.util.List;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * TODO catch bloecke sinnvoll verarbeiten - Fehlerloggin (console o. Datei)
 */
public class CheckGitRep {
    
	private List<Class> lFormattedClassList = new ArrayList<Class>();
	private File oRepoDir;

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException {
		JSONObject oJsonResult = null;

		if(checkLocalCheckstyle() == true)
		{
			List<List<String>> lRepoList = downloadRepoAndGetPath(gitRepository);
			oJsonResult = checkStyle(lRepoList, gitRepository);
			FileUtils.deleteDirectory(oRepoDir);
		}
		
        return oJsonResult.toString();
    }

    public List<List<String>> downloadRepoAndGetPath(String gitRepo) {
        List<List<String>> list = new ArrayList<List<String>>();
		String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"), gitRepo.length()-1).replace(".","_");
        String localDirectory = "repositories/"  + directoryName + "_" + System.currentTimeMillis() +"/";
		oRepoDir = new File(localDirectory);
		Git git = null;

        // TODO ueberprüfen ob Repo vorhanden bzw. Giturl okay
		// TODO Fehler bei bereits vorhandenem GIT Repo beheben
        try {
            git = Git.cloneRepository()
                    .setURI(gitRepo)
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
		/* Closing Object that we can delete the whole directory later */
		git.getRepository().close();
		
        return list;
    }

	public boolean checkLocalCheckstyle() throws MalformedURLException, IOException, FileNotFoundException
	{
		boolean bSuccess = false;
		final String sCheckstyleJar = "checkstyle-6.17-all.jar";
		final String sDownloadCheckStyleJar = "http://downloads.sourceforge.net/project/checkstyle/checkstyle/6.17/checkstyle-6.17-all.jar?r=https%3A%2F%2Fsourceforge.net%2Fp%2Fcheckstyle%2Factivity%2F%3Fpage%3D0%26limit%3D100&ts=1463416596&use_mirror=vorboss";
		
		File oFile = new File(sCheckstyleJar);
		ReadableByteChannel oReadableByteChannel = null;
		FileOutputStream oFileOutput = null;
		URL oURL = null;
		
		if(oFile.exists()) 
		{ 
		    System.out.println("Checkstyle .jar already exists!");
		    bSuccess = true;
		}
		else
		{
			System.out.println("Checkstyle .jar doesnt exists, Starting download");
			oURL = new URL(sDownloadCheckStyleJar);
			oReadableByteChannel = Channels.newChannel(oURL.openStream());
			oFileOutput = new FileOutputStream(sCheckstyleJar);
			oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);
			bSuccess = true;
		}
		
		return bSuccess;
	}
	
    public JSONObject checkStyle(List<List<String>> lRepoList, String gitRepository) throws ParserConfigurationException, SAXException, IOException
	{
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
		final String sRuleSetPath = "/google_checks.xml";
		JSONObject oJson = null;

		/* Listeninhalt kuerzen, um JSON vorbereiten */
		formatList(lRepoList);

		for(int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) 
		{
			String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();
		
			if (sFullPath.endsWith(".java")) 
			{
				sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
			}
		
			String sCheckStyleCommand = "java -jar " + sCheckStylePath + " -c " + sRuleSetPath + " " + sFullPath + ".java -f xml -o " + sFullPath + ".xml";

			try 
			{
				Runtime runtime = Runtime.getRuntime();
				Process proc = runtime.exec(sCheckStyleCommand);
				
				try { 
					proc.waitFor(); 
				} 
				catch (InterruptedException e) { }
			}
			catch (IOException ex) { }
	
			/* Checkstyle Informationen eintragen */
			storeCheckstyleInformation(sFullPath + ".xml", nClassPos);
		}
		
		if(lFormattedClassList != null)	
		{
			/* Schoene einheitliche JSON erstellen */
			oJson = buildJSON(gitRepository);
		}
		
        return oJson;
    }

    public void storeCheckstyleInformation(String sXmlPath, int nClassPos) throws ParserConfigurationException, SAXException, IOException
	{
		File oFileXML = new File(sXmlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(oFileXML);
		doc.getDocumentElement().normalize();
				
		NodeList nList = doc.getElementsByTagName("error");

		for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) 
		{

			Node nNode = nList.item(nNodePos);
					 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element eElement = (Element) nNode;
				
				/* Default Values */
				int nLine = 0;
				int nColumn = 0;
				String sSeverity = "";
				String sMessage = "";
				String sSource = "";

				if(isParsable(eElement.getAttribute("line")))
				{
					nLine = Integer.parseInt(eElement.getAttribute("line"));
				}
				if(isParsable(eElement.getAttribute("column")))
				{
					nColumn = Integer.parseInt(eElement.getAttribute("column"));
				}	
				if(!eElement.getAttribute("severity").isEmpty())
				{
					sSeverity = eElement.getAttribute("severity");
				}	
				if(!eElement.getAttribute("message").isEmpty())
				{
					sMessage = eElement.getAttribute("message");
				}
				if(!eElement.getAttribute("source").isEmpty())
				{
					sSource = eElement.getAttribute("source");
				}
				
				Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);
				
				lFormattedClassList.get(nClassPos).getErrorList().add(oError);
			}
		}
	}

	public boolean isParsable(String input)
	{
	    boolean bParsable = true;
	    
	    try
	    {
	        Integer.parseInt(input);
	    }
	    catch(NumberFormatException e)
	    {
	        bParsable = false;
	    }
	    
	    return bParsable;
	}
	
	public void formatList(List<List<String>> lRepoList)
	{
		for (int nExcercisePos = 0; nExcercisePos < lRepoList.size(); nExcercisePos++)
		{
			Class oClass = null;

			for (int nClassPos = 0; nClassPos < lRepoList.get(nExcercisePos).size(); nClassPos++)
			{
				String[] sFullPathSplit_a = lRepoList.get(nExcercisePos).get(nClassPos).split("\\\\");
				String sFullPath = lRepoList.get(nExcercisePos).get(nClassPos);

				String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1].toString();
				String sTmpExerciseName = sFullPathSplit_a[1].toString();
				
				oClass = new Class(sTmpClassName, sFullPath, sTmpExerciseName);
				
				lFormattedClassList.add(oClass);
			}
		}
	}
	
	public JSONObject buildJSON(String sRepo)
	{
		List<Error> lTmpErrorList = new ArrayList<Error>();
		JSONObject oJsonRoot = new JSONObject();
		JSONObject oJsonExercise = new JSONObject();
		JSONArray lJsonClasses = new JSONArray();
		JSONArray lJsonExercises = new JSONArray();
		String sTmpExcerciseName = "";
		boolean bExcerciseChange = false;
		boolean bLastRun = false;
		boolean bExcerciseNeverChanged = true;
		
		/* add general information to the JSON object */
		oJsonRoot.put("repositoryUrl", sRepo);
		//oJsonRoot.put("groupID", nGroupID);
		//oJsonRoot.put("name", sName);
		
		/* all Classes */
		for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++)
		{
			if(bExcerciseChange == true)
			{
				nClassPos--;
				bExcerciseChange = false;
			}

			String sExcerciseName = lFormattedClassList.get(nClassPos).getsExcerciseName();

			/* first run the TmpName is empty */
			if(sExcerciseName.equals(sTmpExcerciseName) || sTmpExcerciseName.equals(""))
			{
				lTmpErrorList = lFormattedClassList.get(nClassPos).getErrorList();
				JSONArray lJsonErrors = new JSONArray();
				JSONObject oJsonClass = new JSONObject();

				/* all Errors */
				for (int nErrorPos = 0; nErrorPos < lTmpErrorList.size(); nErrorPos++)
				{
					JSONObject oJsonError = new JSONObject();

					oJsonError.put("line", Integer
							.toString(lTmpErrorList.get(nErrorPos).getErrorAtLine()));
					oJsonError.put("column",
							Integer.toString(lTmpErrorList.get(nErrorPos).getColumn()));
					oJsonError.put("severity",
							lTmpErrorList.get(nErrorPos).getSeverity());
					oJsonError.put("message",
							lTmpErrorList.get(nErrorPos).getMessage());
					oJsonError.put("source", lTmpErrorList.get(nErrorPos).getSource());

					lJsonErrors.put(oJsonError);
				}

				sTmpExcerciseName = sExcerciseName;
				
				oJsonClass.put("filepath", lFormattedClassList.get(nClassPos).getFullPath());
				oJsonClass.put("errors", lJsonErrors);
				lJsonClasses.put(oJsonClass);
				
				/* last run if different exercises were found */
				if(bLastRun == true)
				{
					oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
					lJsonExercises.put(oJsonExercise);
				}
				
				/* last run if there was just one exercise */
				if((nClassPos + 1) == lFormattedClassList.size() && bExcerciseNeverChanged == true)
				{
					oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
					lJsonExercises.put(oJsonExercise);
				}
			}
			/* swap for a different exercise */
			else
			{
				oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
				lJsonExercises.put(oJsonExercise);
				oJsonExercise = new JSONObject();
				lJsonClasses = new JSONArray();
				sTmpExcerciseName = lFormattedClassList.get(nClassPos).getsExcerciseName();
				bExcerciseChange = true;
				bExcerciseNeverChanged = false;
				
				/* decrement the position to get the last class from the list */
				if((nClassPos + 1) == lFormattedClassList.size())
				{
					nClassPos--;
					bExcerciseChange = false;
					bLastRun = true;
				}
			}
		}

		oJsonRoot.put("files", lJsonExercises);
		
		return oJsonRoot;
	}
}