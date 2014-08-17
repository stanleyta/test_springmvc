package com.prxmt.controller;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.prxmt.models.NewModel;
import com.prxmt.util.*;

@Controller
public class FirstController {
	private static final Logger logger = LogManager.getLogger(FirstController.class.getName());

	@Autowired
	ServletContext context;
    @RequestMapping("/first")
    public ModelAndView helloWorld() throws UnknownHostException {
    	String message = "";
    	
    	logger.debug("this is a sample log message.");
    	logger.info("My First Application");
        
    	NewModel model = new NewModel();

    	ShiroAuthentication authentication = new ShiroAuthentication();
    	authentication.Init();
    	Subject subject = SecurityUtils.getSubject();
    	Object obj = subject.getPrincipal();

    	MongoClient mongoClient = (MongoClient) context.getAttribute("mongoClient");
    	DB db = (DB) context.getAttribute("db");
    	DBCollection coll = (DBCollection) context.getAttribute("coll");
    	
    	MongoManager mongoManager = MongoManager.getInstance();
    	mongoManager.initMongoFirstCollection(mongoClient, db, coll);
    	MongoPopulater mongoPopr = new MongoPopulater();

    	//mongoPopUtil.PopulateSampleCollections();
    	//model.message += mongoUtil.ListAllRecords();
    	
    	//model.message += mongoPopUtil.PopulateTestCollection();
    	//model.message += mongoPopUtil.InsertTestRecord();
    	model.message += mongoPopr.findTestRecord();
    	//model.message += mongoPopUtil.FindASubsetOfTestRecords();
    	//model.message += mongoPopUtil.ModifyRecordByPut();
    	//model.message += mongoPopUtil.ModifyTestRecordWith$setAnd$inc();
    	//model.message += mongoUtil.DeleteAllRecords();
    	//model.message += mongoPopUtil.DeleteSubsetTestRecords();
    	model.message += mongoManager.listAllRecordsHTML();
    	
    	model.records = mongoManager.getAllRecords();
    	
    	message += "<br/><br/>ok now the rest: <br/>";
        message += "<br><div align='center'>" + "<h1>Hello World, Spring 3.2.1 Example by Crunchify.com<h1> <br>";
        message += "<a href='http://crunchify.com/category/java-web-development-tutorial/'>More Examples</a>";
        return new ModelAndView("first", "model", model);
    }
}
