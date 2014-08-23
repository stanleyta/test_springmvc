package com.prxmt.controller;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.prxmt.backend.MongoManager;
import com.prxmt.backend.MongoPopulater;
import com.prxmt.models.NewModel;
import com.prxmt.security.ShiroAuthentication;
import com.prxmt.util.*;

@Controller
public class FirstController {
	private static final Logger logger = LogManager.getLogger(FirstController.class.getName());

	@Autowired // tells the application context to inject an instance of UserService here
	ServletContext context;

	@RequestMapping("/first")
	public ModelAndView helloWorld() throws UnknownHostException {
		logger.debug("this is a sample log message.");
		logger.info("First Application");

		NewModel model = new NewModel();

    	ShiroAuthentication authentication = new ShiroAuthentication();
    	authentication.Init();
    	Subject subject = SecurityUtils.getSubject();
    	Object obj = subject.getPrincipal();
    	
    	
    	
    	Subject usr = SecurityUtils.getSubject();
    	UsernamePasswordToken token = new UsernamePasswordToken("mike", "abcdef");
    	try {
    	    usr.login(token);
    	} 
    	catch (AuthenticationException ae) {
    		logger.error(ae.toString()) ;
    	    //return ;
    	}
    	logger.info("User [" + usr.getPrincipal() + "] logged in successfully.");
    	
    	

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
    	
    	model.message += "<br/><br/>now the rest: <br/>";
    	model.message += "<br><div align='center'>" + "<h1>Hello World<h1> <br>";
        return new ModelAndView("first", "model", model);
    }
}
