package com.prxmt.backend;

import java.net.UnknownHostException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoManager {
	private static final Logger logger = LogManager.getLogger(MongoManager.class.getName());
	private static MongoManager instance = null;
	protected MongoManager() {} // Exists only to defeat instantiation.
	public static MongoManager getInstance() throws UnknownHostException {
	  if (instance == null) {
		  instance = new MongoManager();
	  }
	  return instance;
	}
	
	private MongoClient mongoClient = null;
	private DB db;
	private DBCollection coll;

//    public void initMongoFirstCollection() throws UnknownHostException {
//		mongoClient = new MongoClient("localhost", 27017 );
//		db = mongoClient.getDB("firstDb");
//		coll = db.getCollection("firstCollection");
//    }

	public void initMongoFirstCollection(MongoClient client, DB d, DBCollection dbc) {
		logger.debug("in initMongoFirstCollection");
		//context = getServletContext();
//		mongoClient = (MongoClient) context.getAttribute("mongoClient");
//		db = (DB) context.getAttribute("db");
//		coll = (DBCollection) context.getAttribute("coll");
		mongoClient = client;
		db = d;
		coll = dbc;
	}

    public void setDB(String dbName) {
		logger.debug("in setDB");
		db = mongoClient.getDB(dbName);
    }
    public void setCollection(String collectionName) {
		logger.debug("in setCollection");
		coll = db.getCollection(collectionName);
    }
    
	public String listAllRecordsHTML() {
		logger.debug("in listAllRecordsHTML");
		String message = "<br/><br/>listing ALL records: <br/>";

		DBCursor cursor = coll.find();
		try {
    	   while(cursor.hasNext()) {
    		   message += cursor.next() + "<br/>";
    	   }
    	} finally {
    	   cursor.close();
    	}
    	message += "ALL records listed<br/>";
    	
    	return message;
    }
    public String deleteAllRecordsHTML() {
		logger.debug("in deleteAllRecordsHTML");
    	String message = "<br/><br/>delete subset records: <br/>";
    	//delete subset of records
    	DBCursor cursor = coll.find();
    	try {
    	   while(cursor.hasNext()) {
    		   DBObject dbo = cursor.next();
    		   message += dbo;
    		   coll.remove(dbo);
    	   }
    	} finally {
    	   cursor.close();
    	}
    	
    	return message;
    }
    public ArrayList<BasicDBObject> getAllRecords() {
		logger.debug("in getAllRecords");
    	ArrayList<BasicDBObject> list = new ArrayList<BasicDBObject>();//coll.distinct(null);
    	DBCursor cursor = coll.find();
    	try {
     	   while(cursor.hasNext()) {
     		   DBObject dbo = cursor.next();
     		   list.add((BasicDBObject) dbo);
     	   }
     	} finally {
     	   cursor.close();
     	}
    	return list;
    }

    public void insertBasicDBObject(BasicDBObject doc) {
		logger.debug("in insertBasicDBObject");
    	//insert a record
    	coll.insert(doc);
    }
    public DBObject findOne() {
		logger.debug("in findOne");
    	return coll.findOne();
    }
    public DBCursor find() {
		logger.debug("in find");
    	return coll.find();
    }
}