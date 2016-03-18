package com.hike.appBenchmark.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author amita
 *
 */
public final class ShellUtil {

	/**
	 * @author amita
	 * @param parameters -- Command along with params to be executed in shell
	 * @return Buffered reader for output as well as error stream (redirected to the output stream)
	 * @throws Exception
	 */
	public static BufferedReader executeShellCommand(String ... parameters) throws Exception {
		System.out.println("\nCommand To be Executed:" );
		for(String command:parameters) {
			System.out.print(command + " ");
		}
		System.out.println();
		ProcessBuilder pb = new ProcessBuilder(parameters);
		pb.redirectErrorStream(true);
		Process process =  null;
		try {
			process = pb.start();
			//int x = process.waitFor();
			//System.out.println(x);
		} catch (IOException e) {
			throw new Exception("Exception caught while starting the command:" + parameters , e);
		}
		BufferedReader inReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		return inReader;
	}

	/**
	 * runs the command and saves the output of the command in a file
	 * @param command shell command to be executed
	 * @param fileName fileName by which the output of command will be saved
	 * @return output of the command
	 */
	public static String executeCommandAndSaveToFile(String fileName, String ... command){
		System.out.println(command);
		File file = new File(fileName);
		BufferedWriter bw = null;
		StringBuffer output = new StringBuffer();
		//String finalCommand
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			while ((line = input.readLine())!= null) {
				output.append(line + "\n");
				if(line.contains("doInBackground - starting work"))break;
			}
			bw.write(output.toString());
			bw.close();
		} catch (Exception e) {
			System.out.println("Unable to Execute Command "+ command);
			e.printStackTrace();
			try {
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return output.toString();
	}

	public static String executeCommand(String command){
		System.out.println(command);
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			// BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = "";
			while ((line = input.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			System.out.println("Unable to Execute Command "+ command);
			e.printStackTrace();
		}
		return output.toString();
	}
}
