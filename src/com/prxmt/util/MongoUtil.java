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

public class MongoUtil {
	MongoClient mongoClient;
	DB db;
	DBCollection coll;
	
	public MongoUtil() throws UnknownHostException {
		mongoClient = new MongoClient( "localhost" , 27017 );
	}
    public void InitMongoTester() throws UnknownHostException {
		db = mongoClient.getDB( "firstDb" );
		coll = db.getCollection("firstCollection");
    }
    
    public String ListAllRecords() {
    	String message = "<br/><br/>listing ALL records: <br/>";

    	DBCursor cursor = coll.find();
    	try {
    	   while(cursor.hasNext()) {
    		   message += cursor.next() + "<br/>";
    	   }
    	} finally {
    	   cursor.close();
    	}
    	message += "ALL records listed: <br/>";
    	
    	return message;
    }
    public String DeleteAllRecords() {
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
    public ArrayList<BasicDBObject> GetAllRecords() {
    	ArrayList<BasicDBObject> list = new ArrayList<BasicDBObject>();//coll.distinct(null);
    	list.add((BasicDBObject) coll.findOne());
    	return list;
    }
}