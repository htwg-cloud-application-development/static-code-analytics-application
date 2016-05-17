package de.htwg.konstanz.cloud.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * TODO catch bloecke sinnvoll verarbeiten - Fehlerloggin (console o. Datei)
 */
public class CheckGitRep {
    
	List<Class> lFormattedClassList = new ArrayList<Class>();

    public String startIt(String gitRepository) {
		JSONObject oJsonResult = null;

		if(checkLocalCheckstyle() == true)
		{
				List<List<String>> lRepoList = downloadRepoAndGetPath(gitRepository);
			oJsonResult = checkStyle(lRepoList, gitRepository);
		}
        return oJsonResult.toString();
    }

    public List<List<String>> downloadRepoAndGetPath(String gitRepo) {
        List<List<String>> list = new ArrayList<List<String>>();
		String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"), gitRepo.length()-1).replace(".","_");
        String localDirectory = "repositories/"  + directoryName + "_" + System.currentTimeMillis() +"/";


        // TODO ueberprüfen ob Repo vorhanden bzw. Giturl okay
		// TODO Fehler bei bereits vorhandenem GIT Repo beheben
        try {
            Git git = Git.cloneRepository()
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

        return list;
    }

		public boolean checkLocalCheckstyle()
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
			
				try
				{
					oURL = new URL(sDownloadCheckStyleJar);
				} 
				catch (MalformedURLException e)
				{
					System.out.println("Invalid URL");
				}

				try
				{
					oReadableByteChannel = Channels.newChannel(oURL.openStream());
				} 
				catch (IOException e)
				{
					System.out.println("Can't open URL");
				}

				try
				{
					oFileOutput = new FileOutputStream(sCheckstyleJar);
				} 
				catch (FileNotFoundException e)
				{
					System.out.println("Did not find an output file");
				}
				
				try
				{
					oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);
					bSuccess = true;
				} 
				catch (IOException e)
				{
					System.out.println("Error while transferring bytes into the output file");
				}
		}
		
		return bSuccess;
	}
	
    public JSONObject checkStyle(List<List<String>> lRepoList, String gitRepository)
	{
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
		final String sRuleSetPath = "/sun_checks.xml";
		JSONObject oJson = null;

		/* Listeninhalt kuerzen, um JSON vorbereiten */
		formatList(lRepoList);

		for(int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
				String sFullPath = lFormattedClassList.get(nClassPos).sFullPath;
			
				if (sFullPath.endsWith(".java")) {
					sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
				}
			
				String sCheckStyleCommand = "java -jar " + sCheckStylePath + " -c " + sRuleSetPath + " " + sFullPath + ".java -f xml -o " + sFullPath + ".xml";

				try {
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
		
		if(lFormattedClassList != null)	{
			/* Schoene einheitliche JSON erstellen */
			oJson = buildJSON(gitRepository);
		}
		
        return oJson;
    }

    public void storeCheckstyleInformation(String sXmlPath, int nClassPos)
	{
		try 
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
		catch (Exception e) 
		{
			e.printStackTrace(); 
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
		for(int nExcercisePos = 0; nExcercisePos < lRepoList.size(); nExcercisePos++)
		{
			Class oClass = null;
			
			for(int x = 0; x < lRepoList.get(nExcercisePos).size(); x++)
			{
				String[] need = lRepoList.get(nExcercisePos).get(x).split("\\\\");
				String sFullPath = lRepoList.get(nExcercisePos).get(x);
				
				oClass = new Class(need[need.length-1].toString(), sFullPath);
				lFormattedClassList.add(oClass);
			}
		}
	}
	
	public JSONObject buildJSON(String sRepo)
	{
		List<Error> lTmpErrorList = new ArrayList<Error>();
		JSONObject oJsonRoot = new JSONObject();
		JSONArray lJsonClasses = new JSONArray();
		
		/* add general information to the JSON object */
		oJsonRoot.put("repositoryUrl", sRepo);
		//oJsonRoot.put("GroupID", nGroupID);
		//oJsonRoot.put("Name", sName);
		
		/* all Classes */
		for(int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++)
		{
			lTmpErrorList = lFormattedClassList.get(nClassPos).getErrorList();	
			JSONArray lJsonErrors = new JSONArray();
			JSONObject oJsonClass = new JSONObject();
				
			/* all Errors */
			for(int nErrorPos = 0; nErrorPos < lTmpErrorList.size(); nErrorPos++)
			{
				JSONObject oJsonError = new JSONObject();
				
				oJsonError.put("Line", Integer.toString(lTmpErrorList.get(nErrorPos).nErrorAtLine));
				oJsonError.put("Column", Integer.toString(lTmpErrorList.get(nErrorPos).nColumn));
				oJsonError.put("Severity", lTmpErrorList.get(nErrorPos).sSeverity);
				oJsonError.put("Message", lTmpErrorList.get(nErrorPos).sMessage);
				oJsonError.put("Source", lTmpErrorList.get(nErrorPos).sSource);
				
				lJsonErrors.put(oJsonError);
			}
			
			oJsonClass.put("Filepath", lFormattedClassList.get(nClassPos).sFullPath);
			oJsonClass.put("Errors", lJsonErrors);
			lJsonClasses.put(oJsonClass);
		}
		
		oJsonRoot.put("Files", lJsonClasses);
		
		return oJsonRoot;
	}
}