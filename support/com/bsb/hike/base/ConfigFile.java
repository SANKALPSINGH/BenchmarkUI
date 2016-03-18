package com.bsb.hike.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigFile
{

    /**
     * ***********************************************************************************************
     * ***************************************  Utility Methods **************************************
     * ***********************************************************************************************
     */
    protected int getIntProperty(Properties props, String propName, int defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }

    protected long getLongProperty(Properties props, String propName, long defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return Long.parseLong(value.trim());
    }

    protected float getFloatProperty(Properties props, String propName, float defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return Float.parseFloat(value.trim());
    }

    protected boolean getBooleanProperty(Properties props, String propName, boolean defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    protected String getStringProperty(Properties props, String propName, String defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return value;
    }

    protected double getDoubleProperty(Properties props, String propName, double defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            return defaultValue;
        }
        return Double.parseDouble(value.trim());
    }

    protected List<String> getListProperty(Properties props, String propName, String defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            if ((defaultValue == null) || defaultValue.isEmpty())
            {
                return new ArrayList<String>();
            }
            else
            {
                value = defaultValue;
            }
        }
        return Arrays.asList(value.split(","));

    }

    protected String[] getArrayProperty(Properties props, String propName, String defaultValue)
    {
        String value = props.getProperty(propName);
        if (value == null || value.isEmpty())
        {
            if ((defaultValue == null) || defaultValue.isEmpty())
            {
                return new String[]{};
            }
            else
            {
                value = defaultValue;
            }
        }
        return value.split(",");

    }
}
