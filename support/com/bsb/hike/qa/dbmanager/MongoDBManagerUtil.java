package com.bsb.hike.qa.dbmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bsb.hike.base.HikeAutomationConfig;
import com.bsb.hike.qa.constants.HikeConstants;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class MongoDBManagerUtil
{
    private Mongo mongo;
    private DB userDB;

    private static MongoDBManagerUtil mongoDBManager;
    List<ServerAddress> mongoReplicaProps;
    public static final String MONGO_GROUP_USERS = "users";
    public static final String MONGO_USER_GROUPS = "groups";
    public static final String MONGO_GROUP_ID = "gid";

    /*
     * This is for unit testing
     */
    public static MongoDBManagerUtil getInstance(String dbPrefix)
    {
        if (mongoDBManager == null)
        {
            synchronized (MongoDBManagerUtil.class)
            {
                if (mongoDBManager == null)
                {
                    mongoDBManager = new MongoDBManagerUtil(dbPrefix);
                }
            }
        }
        return mongoDBManager;
    }

    private MongoDBManagerUtil(String dbPrefix) {
        init(dbPrefix);
    }
    
    public static MongoDBManagerUtil getInstance()
    {
        return getInstance("");
    }

    @SuppressWarnings("deprecation")
    private void init(String dbPrefix)
    {
        try
        {
            mongoReplicaProps = new ArrayList<ServerAddress>(); 
        	mongoReplicaProps.add(new ServerAddress(HikeAutomationConfig.getInstance().getMongodbHost(), HikeAutomationConfig.getInstance().getMongodbPort()));
        	mongoReplicaProps.add(new ServerAddress(HikeAutomationConfig.getInstance().getMongodbHost(), HikeAutomationConfig.getInstance().getMongodbPort()));
            MongoOptions options = new MongoOptions();
            options.autoConnectRetry = true;
            options.readPreference = ReadPreference.primaryPreferred();
            options.setThreadsAllowedToBlockForConnectionMultiplier(50);
            options.setConnectionsPerHost(100);
            options.slaveOk = true;
            mongo = new Mongo(mongoReplicaProps,options);
            userDB = mongo.getDB(HikeAutomationConfig.getInstance().getMongoUserDBName());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public List<String> getListOfDB(){
    	return mongo.getDatabaseNames();
    }
    
    public Mongo getMongo(){
    	return mongo;
    }
    

    public int messageEntryInDB(String uid){
  	  
    	  List<String> list = getListOfDB();		
    	    for (String messagedb : list) {
    	    	if(messagedb.startsWith("message")){
    	    		DB messageDB = getMongo().getDB(messagedb);
    	            DBCollection collection = messageDB.getCollection("messages");
    	            BasicDBObject query = new BasicDBObject();
    	            query.put("uid", uid);
    	            DBCursor result = collection.find(query);
    	            if (result!= null && result.hasNext()){
    	            {          
    	            	System.out.println("DB for messsage==>>" + messagedb);
    	            	return result.count();
    	            	}
    	            }  
    	    	}
    	    }
    		  
    		  return 0;
    	  }
    
    public List<String> getGroupsOfUser(String msisdn)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
        BasicDBObject query = new BasicDBObject(MONGO_GROUP_USERS+"."+HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject(MONGO_GROUP_ID, 1);
        DBCursor cursor = collection.find(query, fields);
        List<String> list=new ArrayList<String>();
        while(cursor.hasNext())
            list.add((String)cursor.next().get(MONGO_GROUP_ID));
        return list;
    }
    
    public String getUidFromMsisdn(String msisdn , DB userdb)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append("reward_token", 1));
        if ((result == null) || !result.containsField("reward_token"))
        {
            return null;
        }

        return result.get("reward_token").toString();
    }
 
    public String getCurrentToken(String msisdn , DB userDB)
    {
        try{
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        
        BasicDBList deviceList = (BasicDBList) result.get("devices");
        BasicDBObject device = (BasicDBObject) deviceList.get(0);     
        return  device.get("token").toString();
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getRewardToken(String msisdn , DB userDB)
    {
        try{
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        
        String rewardToken = result.get("reward_token").toString();
        return rewardToken;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getInvitedContacts(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        System.out.println(result.get("invited").toString());
        return result.get("invited").toString();
    }

    public String getDetailsFromMsisdn(String msisdn , String field)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append(field, 1));
        if ((result == null) || !result.containsField(field))
        {
            return null;
        }

        return result.get(field).toString();
    }

    public String getAccountConnected(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        DBObject result = collection.findOne(query, new BasicDBObject().append("accounts", 1));
        if ((result == null) || !result.containsField("accounts"))
        {
            return null;
        }

        return result.get("accounts").toString();
    }

    public static String getUserTimeLineStatus(String msisdn, DB userDB){
    	
    	DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
    	BasicDBObject query = new BasicDBObject();
    	query.put(HikeConstants.MSISDN, msisdn);
    	   DBObject result = collection.findOne(query, new BasicDBObject().append("pushsu", 1));
           if ((result == null) || !result.containsField("pushsu"))
           {
               return null;
           }

           return result.get("pushsu").toString();   	
    }
    
    public List<String> getGroups(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject(HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject("groups", 1);
        DBObject value = collection.findOne(query, fields);
        if (value == null)
        {
            return Collections.EMPTY_LIST;
        }

        List<String> elements = (List<String>) value.get("groups");
        if (elements != null)
        {
            return elements;
        }
        else
        {
            return Collections.EMPTY_LIST;
        }
    }
    
    public void deleteGroup(String groupId)
    {
    	DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
    	BasicDBObject query = new BasicDBObject();
    	query.put("gid", groupId);
    	DBObject result = collection.findOne(query);
    	if(result != null)
    	{
    		collection.remove(query); 
    		System.out.println("Group with gid:"+groupId + "deleted");
    	}
    }
    public String getBlockedUserList(String msisdnBlocker,DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.COMMON_BLOCKLIST);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdnBlocker);
        DBObject result = collection.findOne(query);
        if ((result == null) || !result.containsField("blocklist") || result.get("blocklist").toString().equals("[ ]"))
        {
            return null;
        }

        return result.get("blocklist").toString();
    }
    
    public List<String> getGroupsOfUser(String msisdn , DB userDB)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
        BasicDBObject query = new BasicDBObject(MONGO_GROUP_USERS+"."+HikeConstants.MSISDN, msisdn);
        BasicDBObject fields = new BasicDBObject(MONGO_GROUP_ID, 1);
        DBCursor cursor = collection.find(query, fields);
        List<String> list=new ArrayList<String>();
        while(cursor.hasNext())
            list.add((String)cursor.next().get(MONGO_GROUP_ID));
        return list;
    }
    
    public DBObject getGroup(String groupId)
    {
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(MONGO_GROUP_ID, groupId);
        DBObject result = collection.findOne(query);
        if( result == null){
            return null;
        }
        return result;
    }
    
    public String getGroupIcon(String gid , String field)
	{
		DBCollection collection = userDB.getCollection(HikeConstants.MONGO_GROUPS_COLLECTION);
		BasicDBObject query = new BasicDBObject();
		query.put("gid", gid);
		DBObject result = collection.findOne(query, new BasicDBObject().append(field, 1));
		if ((result == null) || !result.containsField(field))
		{
			return null;
		}

		return result.get(field).toString();
	}
   

    public String getBackUpToken(String msisdn)
    {
    	DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);
        
        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        System.out.println(result.get("backup_token").toString());
        return result.get("backup_token").toString();
    }

	public String getReferredBy(String msisdn) {
		// TODO Auto-generated method stub
		DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put(HikeConstants.MSISDN, msisdn);

        DBObject result = collection.findOne(query);
        if (result == null)
        {
            return null;
        }
        System.out.println(result.get("referredby").toString());
        return result.get("referredby").toString();
		//return null;
	}

    public enum FAVORITE_STATE
    {
        ADDED(null),
        PENDING(Boolean.TRUE),
        REMOVED(Boolean.FALSE);
        
        Boolean booleanNotation;
        private FAVORITE_STATE(Boolean booleanNotation)
        {
            this.booleanNotation = booleanNotation;
        }
        
        public Boolean getBooleanNotation()
        {
            return booleanNotation;
        }
    };
    
    public void modifyFavorites(String m_clientId, String msisdn, boolean add, boolean isHikeUser,DB userDB)
    {
        FAVORITE_STATE state = add ? FAVORITE_STATE.ADDED : FAVORITE_STATE.REMOVED;
        DBCollection collection = userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION);
        modifyFavorites(collection, m_clientId, msisdn, state);
        if(add)
        {
            collection = isHikeUser ? userDB.getCollection(HikeConstants.MONGO_USERS_COLLECTION) : userDB.getCollection(HikeConstants.MONGO_NON_HIKERS_COLLECTION);
            modifyFavorites(collection, msisdn, m_clientId, FAVORITE_STATE.PENDING);
        }
    }
    
    private void modifyFavorites(DBCollection collection, String m_clientId, String msisdn, FAVORITE_STATE favoriteState)
    {
        BasicDBObject query = new BasicDBObject(HikeConstants.MSISDN, m_clientId);
        String key = HikeConstants.MONGO_FAVORITES + "." + msisdn;
        String op = "$set";
        BasicDBObject value = new BasicDBObject();
        if(favoriteState == FAVORITE_STATE.PENDING) 
        {
            value.append("pending", true);
            DBObject clause1 = new BasicDBObject(key, new BasicDBObject("$exists", false));
            DBObject clause2 = new BasicDBObject(key, new BasicDBObject("pending", false));
            BasicDBList or = new BasicDBList();
            or.add(clause1);
            or.add(clause2);
            query.append("$or", or);
        }
        else if(favoriteState == FAVORITE_STATE.REMOVED)
        {
            value.append("pending", false);
            query.append(key, new BasicDBObject("$exists", true));
        }
        
        BasicDBObject update = new BasicDBObject(op,
                                                  new BasicDBObject(key, value));
        collection.update(query, update, true, false);
    }
	
}
