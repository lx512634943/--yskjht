package com.sojpt.boot.listener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SoJptSessionListener implements HttpSessionListener, HttpSessionActivationListener, ServletContextListener{
	
	public static final ConcurrentMap<String, HttpSession> SESSION_MAP_BY_ID = new ConcurrentHashMap<String, HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		addSession(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		removeSession(se.getSession());
	}
	
	private static void addSession(final HttpSession session) {
		SESSION_MAP_BY_ID.put(session.getId(), session);
	}

	public static void removeSession(final HttpSession session) {
		SESSION_MAP_BY_ID.remove(session.getId());
	}
	
	public static HttpSession getSessionById(String sessionId) {
		return SESSION_MAP_BY_ID.get(sessionId);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SESSION_MAP_BY_ID.clear();
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}
}
