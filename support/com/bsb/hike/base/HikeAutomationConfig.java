package com.bsb.hike.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class HikeAutomationConfig extends ConfigFile
{
    private static volatile HikeAutomationConfig config = null;
    
    private Properties properties;

    private final String LOCAL_CONFIG_FILE ="/data/local/tmp/awsqastaging-apitest-env.properties";
    
    private static final String tcpUri = "tcp://%1$s:%2$d"; 
    
	//redis
    private String redisDbHost;
    private int redisDbPort;
    private int redisDbPort80;
    
    //mysql
    private String mysqlHost;
    private int mysqlPort;
    private String mysqluserName;
    private String mysqlpassword;
    private String mysqlFriendsDB;
    private String mysqlUserBlockDB;
    private String mysqlInviteDB;
    
	//mongodb
    private String mongodbHost;
    private int mongodbPort;
    private String mongoUserDBName;
 
    // http API end point
    public String httphost;
    public int httpport;
    
    // Mqtt server end point 
    private String mqttHost;
    private int mqttPort;
    
    private String zkConnect;
    
    public static HikeAutomationConfig getInstance()
    {
        if (config == null)
        {
            synchronized(HikeAutomationConfig.class) 
            {
                if(config == null) 
                {
                    config = new HikeAutomationConfig();
                }
            }
        }
        return config;
    }
    
    public HikeAutomationConfig()
    {
            loadProperties(getPropertiesFromFile(LOCAL_CONFIG_FILE));
    }

    public Properties getPropertiesFromFile(String propsFile)
    {
        Properties properties = new Properties();
        try
        {
            InputStream propertiesFile = new FileInputStream(new File(propsFile));
            if (propertiesFile != null)
            {
                properties.load(propertiesFile);
            }
        }
        catch( FileNotFoundException e)
        {
           e.printStackTrace();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return properties;
    }
 
    public void loadProperties(Properties properties)
    {
        this.properties = properties;
        
        redisDbHost = getStringProperty(properties, "redis.host", "127.0.0.1");
        redisDbPort = getIntProperty(properties, "redis.port", 6379);
        redisDbPort80=getIntProperty(properties, "redis.port80", 6380);
        
        mysqlHost = getStringProperty(properties, "mysql.host", "127.0.0.1");
        mysqlPort = getIntProperty(properties, "mysql.port", 3306);
        mysqluserName = getStringProperty(properties, "mysql.user", "hike");
        mysqlpassword = getStringProperty(properties, "mysql.passwd", "hike");
        mysqlFriendsDB = getStringProperty(properties, "mysql.friendsdb", "friends");
        mysqlUserBlockDB = getStringProperty(properties, "mysql.userblockdb", "blockdb");
        mysqlInviteDB = getStringProperty(properties, "mysql.invitedb", "inviteLogs");
        
        mongodbHost = getStringProperty(properties, "mongodb.host", "127.0.0.1");
        mongodbPort = getIntProperty(properties, "mongodb.port", 27017);
        mongoUserDBName = getStringProperty(properties, "mongodb.userdb", "userdb");
        
        httphost = getStringProperty(properties, "httpapi.host", "127.0.0.1");
        httpport = getIntProperty(properties, "httpapi.port", 8080);
        
        mqttHost = getStringProperty(properties, "mqtt.host", "127.0.0.1");
        mqttPort = getIntProperty(properties, "mqtt.port", 2883); 
        
        zkConnect = getStringProperty(properties, "zk.connect", "127.0.0.1:2181");
        printProperties();
    }
    
    public void printProperties()
    {
    	System.out.println("redisDbHost:"+redisDbHost);
    	System.out.println("redisDbPort:"+redisDbPort);
    	System.out.println("redisDbPort80:"+redisDbPort80);
    	System.out.println("mysqlHost:"+mysqlHost);
    	System.out.println("mysqlPort:"+mysqlPort);
    	System.out.println("mongodbHost:"+mongodbHost);
    	System.out.println("mongodbPort:"+mongodbPort);
    	System.out.println("httphost:"+httphost);
    	System.out.println("httpport:"+httpport);
    	System.out.println("mqttHost:"+mqttHost);
    	System.out.println("mqttPort:"+mqttPort);
    	System.out.println("zkConnect:"+zkConnect); 	
    }
    public Properties getProperties()
    {
        return properties;
    }
    
    public String getTcpuri() 
    {
		return String.format(tcpUri , getMqtthost(), getMqttport());
	}

	public String getRedisDbHost() {
		return redisDbHost;
	}

	public int getRedisDbPort() {
		return redisDbPort;
	}

	public int getRedisDbPort80() {
		return redisDbPort80;
	}
	
	public String getMysqlHost() {
		return mysqlHost;
	}

	public int getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqluserName() {
		return mysqluserName;
	}

	public String getMysqlpassword() {
		return mysqlpassword;
	}

	public String getMysqlFriendsDB() {
		return mysqlFriendsDB;
	}
	
    public String getMysqlUserBlockDB() {
		return mysqlUserBlockDB;
	}
    
    public String getMysqlInviteDB() {
		return mysqlInviteDB;
	}
    
	public String getMongodbHost() {
		return mongodbHost;
	}

	public int getMongodbPort() {
		return mongodbPort;
	}

	public String getMongoUserDBName() {
		return mongoUserDBName;
	}

	public String getHttphost() {
		return httphost;
	}

	public int getHttpport() {
		return httpport;
	}

	public String getMqtthost() {
		return mqttHost;
	}

	public int getMqttport() {
		return mqttPort;
	}
	  
	public String getZkConnect()
	{
		return zkConnect;
	}
}
