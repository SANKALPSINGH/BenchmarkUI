package com.hike.appBenchmark.jenkins;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;

import com.hike.appBenchmark.utils.GenerateFileStructureForRun;
import com.hike.appBenchmark.utils.IOUtils;
import com.hike.appBenchmark.utils.ShellUtil;

/**
 *
 * @author kumarpratyush
 * Implementation class for jenkins service interface
 */
public class JenkinsServiceImpl implements JenkinsService {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource msgSource) {
		this.messageSource = msgSource;
	}

	@Override
	public boolean triggerJob(String jobName, String branchName) {

		boolean isSuccessful = false;
		String expectedString = "SUCCESS";

		String javaHomePath = System.getProperty(getProperty("java.home.command"));
		String userName = System.getProperty(getProperty("user.name.command"));

		List<String> commandParams =  new ArrayList<String>();
		commandParams.add(javaHomePath + getProperty("java.executable.path"));
		commandParams.add(getProperty("jar.flag"));
		commandParams.add(getProperty("home.path")+userName+getProperty("jenkins.jar.path"));
		commandParams.add(getProperty("jenkins.build.s"));
		commandParams.add(getProperty("jenkins.host.path"));
		commandParams.add(getProperty("jenkins.build.command"));
		commandParams.add(jobName);
		try {
			if(branchName != null) {
				commandParams.add(getProperty("jenkins.build.p"));
				commandParams.add(getProperty("jenkins.branch.command")+branchName);
			}
			commandParams.add(getProperty("jenkins.build.s"));

			String output = IOUtils.convertBRtoString(ShellUtil.executeShellCommand(commandParams.toArray(new String[commandParams.size()])));
			if(output.contains(expectedString)) {
				System.out.println("build successful");
				System.out.println("build successful");
				isSuccessful = true;
			}
			else {
				System.out.println("Build fail:Jenkins build failed ");
				isSuccessful = false;
			}
		} catch (Exception e) {
			System.out.println("Not able to trigger job");
			e.printStackTrace();
		}
		return isSuccessful;

	}

	@Override
	public boolean triggerJob(int runId, int runInfoID, String templateName, String apkVersion, String androidGitBranch, String emailBody, String recipientList) {

		String jobName="run_completed";
		boolean isSuccessful = false;
		String expectedString = "SUCCESS";
		String output;
		String javaHomePath = System.getProperty(getProperty("java.home.command"));
		String userName = System.getProperty(getProperty("user.name.command"));

		List<String> commandParams =  new ArrayList<String>();
		commandParams.add(javaHomePath + getProperty("java.executable.path"));
		commandParams.add(getProperty("jar.flag"));
		commandParams.add(getProperty("home.path")+userName+getProperty("jenkins.jar.path"));
		commandParams.add(getProperty("jenkins.build.s"));
		commandParams.add(getProperty("jenkins.host.path"));
		commandParams.add(getProperty("jenkins.build.command"));
		commandParams.add(jobName);
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.runID.command") + String.valueOf(runId));
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.runInfoID.command") + String.valueOf(runInfoID));
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.templateName.command") + templateName);
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.apkVersion.command") + apkVersion);
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.androidGitBranch.command") + androidGitBranch);
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.email.command") + emailBody);
		commandParams.add(getProperty("jenkins.build.p"));
		commandParams.add(getProperty("jenkins.recipient.list.command") + recipientList);
		commandParams.add(getProperty("jenkins.build.s"));

		try {
			output = IOUtils.convertBRtoString(ShellUtil.executeShellCommand(commandParams.toArray(new String[commandParams.size()])));

			if(output.contains(expectedString)){
				System.out.println("Job Triggered successfully");
				isSuccessful = true;
			}
			else{
				System.out.println("job failure");
				isSuccessful = false;
			}

		} catch (Exception e) {
			System.out.println("Exception occurred while triggering jenkins job");
			e.printStackTrace();
		}
		return isSuccessful;
	}

	@Override
	public boolean getLatestapk(String branchName, int runID) {

		boolean successful = triggerJob("internal_release", branchName);
		if(!successful) {
			System.out.println(runID+":Build APK failed because Job not Triggered:Jenkins Failure");
		}
		else {
			moveApk(runID);
		}
		return successful;

	}

	@Override
	public void moveApk(int runID) {

		String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));
		try {
			GenerateFileStructureForRun fileStructureObject = new GenerateFileStructureForRun(messageSource);
			fileStructureObject.createRunFolder(String.valueOf(runID));
			//File folder=  new File(getProperty("home.path") + userName + getProperty("jenkins.job.apk.path"));
			File folder = new File("/Users/kumarpratyush/Downloads");
			File[] listOfApk = folder.listFiles();

			for (int i = 0; i < listOfApk.length; i++) {
				if (listOfApk[i].isFile()) {
					String fileName= listOfApk[i].getName();
					if(fileName.contains("debug")&& !(fileName.contains("customDev"))) {
						Files.copy(listOfApk[i].toPath(), new File(getProperty("home.path") + userName + getProperty("apk.path") + "/" + runID +
								getProperty("apk.name.suffix")).toPath(), StandardCopyOption.REPLACE_EXISTING);
						return;
					}
				}
			}
		}catch(Exception e){
			System.out.println(runID+":APK is not moved in Run ID folder");
			e.printStackTrace();
		}
	}

	private String getProperty(String code) {
		String message = this.messageSource.getMessage(code, null, null);
		return message;
	}
}
