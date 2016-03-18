package com.hike.appBenchmark.utils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author amita
 *
 */
public class IOUtils {

	/**
	 * @author amita
	 * @param input -- Buffered reader object to be converted into output string
	 * @return the string obtained after reading the buffered reader input
	 * @throws Exception
	 */
    public static String convertBRtoString(BufferedReader input) throws Exception {
        StringBuffer output = new StringBuffer();
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
            	System.out.println(line + "\n");
                output.append(line + "\n");
            }
        } catch (IOException e) {
        	input.close();
            throw new Exception("Exception caught while converting Buffered Reader input  into String", e);
        }
        input.close();
        return output.toString();
    }
}
