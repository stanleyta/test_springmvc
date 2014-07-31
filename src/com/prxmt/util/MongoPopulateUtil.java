package com.prxmt.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoPopulateUtil {
	MongoClient mongoClient;
	DB db;
	DBCollection coll;
	
	public MongoPopulateUtil() throws UnknownHostException {
		mongoClient = new MongoClient( "localhost" , 27017 );
	}
    public void InitMongoDB() throws UnknownHostException {
		db = mongoClient.getDB( "firstDb" );
		coll = db.getCollection("Users");
    }

    public void PopulateSampleCollections() {
    	//insert a record
		BasicDBObject doc = new BasicDBObject("_id", "stanleyta")
		.append("email", "stanleyta@gmail.com")
		.append("created_date", new Date())
		.append("password", "password")
		.append("info", new BasicDBObject("x", 1).append("y", 1));

    	coll.insert(doc);
    }

    public String PopulateTestCollection() {
    	String message = "populating collection with 10 documents";
    	for (int i = 0; i < 10; i++) {
        	Date date = new Date();
        	//insert a record
        	BasicDBObject doc = new BasicDBObject("name", "MongoDB")
            .append("count", i+1)
            .append("timestamp", date)
            .append("info", new BasicDBObject("x", 1).append("y", i));
    		
        	coll.insert(doc);
        	message += "inserted document: " + doc;
    	}
    	
    	return message;
    }
    public String InsertTestRecord() {
    	//initialize
    	Date date = new Date();
    	//insert a record
    	BasicDBObject doc = new BasicDBObject("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("timestamp", date)
        .append("info", new BasicDBObject("x", 205).append("y", 101));
		
    	coll.insert(doc);
    	
    	String message = "inserted document: " + doc;
    	return message;
    }
    public String FindTestRecord() {
    	//find one
        String message = null;
    	DBObject myDoc = coll.findOne();
    	message += "<br/><br/>find one" + myDoc + "<br/>";
    	message += "count: " + myDoc.get("count") + "<br/>";
    	
    	message += "<br/><br/>listall<br/>";
    	//list all
    	DBCursor cursor = coll.find();
    	try {
    	   while(cursor.hasNext()) {
    		   message += cursor.next();
    	   }
    	} finally {
    	   cursor.close();
    	}
    	return message;
    }
    public String FindASubsetOfTestRecords() {
    	String message = "<br/><br/>subset records: <br/>";
    	//find subset of records
    	DBCursor cursor = coll.find();
    	BasicDBObject query = 
			new BasicDBObject("info", new BasicDBObject("x", 205).append("y",  100));

    	cursor = coll.find(query);
    	try {
    	   while(cursor.hasNext()) {
    		   message += cursor.next();
    	   }
    	} finally {
    	   cursor.close();
    	}
    	return message;
    }
    public String ModifyTestRecordByPut() {
    	String message = "<br/><br/>inserted record to be modified (replaced): <br/>";
    	BasicDBObject query = 
    			new BasicDBObject().append("info", new BasicDBObject("x", 205).append("y",  101));
    	message += query;
    	BasicDBObject doc = new BasicDBObject();
    	doc.put("count", 2);
    	coll.update(query, doc);
    	message += "<br/>modified record: " + doc;

    	return message;
    }
    public String ModifyTestRecordWith$setAnd$inc() {
    	//update only single record with $set
//    	Random rand = new Random();
//    	int randomNum = rand.nextInt(10);
    	int randomNum = 1;

    	String message = "<br/><br/>modify property with $set: <br/>";
    	BasicDBObject query = new BasicDBObject().append("count", 3);
    	message += query;
    	BasicDBObject doc = new BasicDBObject().append("$set", new BasicDBObject("count", 4));
		coll.update(query, doc);
    	message += "<br/>modified $set record: " + doc;

    	message += "<br/><br/>increment property with $inc: <br/>";
    	query = new BasicDBObject().append("count", 7);
    	message += query;
    	doc = new BasicDBObject().append("$inc", new BasicDBObject("count", 3));
		coll.update(query, doc);
    	message += "<br/>modified $inc record: " + doc;

    	return message;
    }
    public String DeleteSubsetTestRecords() {
    	String message = "<br/><br/>delete all records: <br/>";
    	//delete subset of records
    	BasicDBObject query = new BasicDBObject("count", 3);
    	DBCursor cursor = coll.find(query);
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
}