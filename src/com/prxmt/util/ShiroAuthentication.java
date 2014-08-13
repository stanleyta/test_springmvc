package com.prxmt.util;

import org.apache.logging.log4j.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroAuthentication {
	private static final Logger log = LogManager.getLogger(ShiroAuthentication.class.getName());
	public Subject Init() {

        //Init shiro
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);

        Subject currentUser = SecurityUtils.getSubject();
        
        if ( !currentUser.isAuthenticated() ) {
            //collect user principals and credentials in a gui specific manner 
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
            token.setRememberMe(true);

            try {
                currentUser.login( token );
                //if no exception, that's it, we're done!
            } catch ( UnknownAccountException uae ) {
                //username wasn't in the system, show them an error message?
            } catch ( IncorrectCredentialsException ice ) {
                //password didn't match, try again?
            } catch ( LockedAccountException lae ) {
                //account for that username is locked - can't login.  Show them a message?
            } catch ( AuthenticationException ae ) {
                //unexpected condition - error?
            }
            
            Session session = currentUser.getSession();
            session.setAttribute( "customKey", "value" );

        	log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
        }

        if ( currentUser.hasRole( "schwartz" ) ) {
            log.info("May the Schwartz be with you!" );
        } else {
            log.info( "Hello, mere mortal." );
        }
        
        if ( currentUser.isPermitted( "lightsaber:weild" ) ) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }
        
        if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
            log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
                        "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }
        
        //currentUser.logout(); //removes all identifying information and invalidates their session too.
        return currentUser;
	}
}
