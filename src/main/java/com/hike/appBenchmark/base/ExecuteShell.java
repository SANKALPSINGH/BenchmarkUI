package com.hike.appBenchmark.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteShell {


	public void ExecuteShellCommand(String ... parameters){

		String command= "";

		for(int counter=0; counter < parameters.length ; counter++){
			command = command + parameters[counter] + " ";
		}
		System.out.println("COMMAND "+ command);
		executeCommand(command);

	}

	public String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {

				//output.append(line + "\n");
				System.out.println(line);

			}
			p.waitFor();
		} catch (Exception e) {
			System.out.println("Unable to Execute Command "+ command);
			e.printStackTrace();
		}

		return output.toString();
	}

	public String getAppPid()
	{
		System.out.println("Getting device id: ");

		Process p;
		String line = "";
		String id = "";
		try {

			String command = "ps | grep com.bsb.hike";
			p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			line = input.readLine();

			id = line.substring(10, 15);



		} catch (IOException e) {

			e.printStackTrace();

		}
		return id;
	}

}
