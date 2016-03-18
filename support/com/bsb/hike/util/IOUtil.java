package com.bsb.hike.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: bhuvangupta
 * Date: 24/02/12
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class IOUtil
{
    public static String readData(InputStream inputstream, boolean closeStream)
            throws IOException
    {
        try
        {
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            StringBuilder strBuff = new StringBuilder();
            String responseStr = null;
            while ((responseStr = bufferedreader.readLine()) != null)
            {
                strBuff.append(responseStr);
            }
            return strBuff.toString();
        }
        finally
        {
            if (closeStream)
            {
                inputstream.close();
            }
        }
    }
}
