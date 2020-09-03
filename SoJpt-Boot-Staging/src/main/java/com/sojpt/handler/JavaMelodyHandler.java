package com.sojpt.handler;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;
import com.sojpt.constant.Const;
import com.sojpt.model.DicUser;

public class JavaMelodyHandler extends Handler{

	private Pattern skipedUrlPattern;
	
	public JavaMelodyHandler(String skipedUrlRegx, boolean isCaseSensitive) {
		if (StrKit.isBlank(skipedUrlRegx)) {
			throw new IllegalArgumentException("The para excludedUrlRegx can not be blank.");
		}
		skipedUrlPattern = isCaseSensitive ? Pattern.compile(skipedUrlRegx) : Pattern.compile(skipedUrlRegx, Pattern.CASE_INSENSITIVE);
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		DicUser adminuser = (DicUser) request.getSession().getAttribute(Const.session_username);
		if (adminuser == null && skipedUrlPattern.matcher(target).matches()) {
			return ;
		} else {
			next.handle(target, request, response, isHandled);
		}
	}

}
