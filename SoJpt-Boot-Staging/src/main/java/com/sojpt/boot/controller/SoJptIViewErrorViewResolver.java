package com.sojpt.boot.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import com.jfinal.render.RenderManager;

@Configuration
public class SoJptIViewErrorViewResolver implements ErrorViewResolver  {
	
	@Bean
	public SoJptIViewErrorViewResolver getSoJptIviewErrorViewResolver() {
		return new SoJptIViewErrorViewResolver();
	}

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		
		String view = RenderManager.me().getConstants().getErrorView(status.value());
		if (view != null) {		
			return new ModelAndView(view, status);
		}
		return null;
	}

}
