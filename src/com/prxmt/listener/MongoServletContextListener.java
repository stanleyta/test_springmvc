package com.prxmt.listener;
import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
 
public class MongoServletContextListener implements ServletContextListener{
	private static final Logger logger = LogManager.getLogger(MongoServletContextListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("MongoServletContextListener destroyed");
	}
	
    //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.debug("MongoServletContextListener started");	
		
		MongoClient mongoClient = null;
		DB db;
		DBCollection coll;
		
		try {
			mongoClient = new MongoClient("localhost", 27017 );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		db = mongoClient.getDB("firstDb");
		coll = db.getCollection("firstCollection");
		
		ServletContext cntxt = event.getServletContext();
		cntxt.setAttribute("mongoClient", mongoClient);
		cntxt.setAttribute("db", db);
		cntxt.setAttribute("coll", coll);
		
		//this.getServletContext().getAttribute("list")
	}
}