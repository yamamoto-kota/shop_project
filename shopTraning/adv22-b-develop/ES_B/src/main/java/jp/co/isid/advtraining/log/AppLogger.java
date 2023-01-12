package jp.co.isid.advtraining.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class AppLogger implements HandlerInterceptor {

    // ロガー
    private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
    	if (handler instanceof ResourceHttpRequestHandler) {
            // staticファイルはインターセプトさせずにスルーさせる
            return true;
        }

    	//ユーザID,HTTPメソッド(GETorPOST),コントローラ名,メソッド名
//    	String methodName = ((HandlerMethod) handler).getMethod().getName();
//    	String className = ((HandlerMethod)handler).getBeanType().getName();

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Principalからログインユーザの情報を取得
		String userName = auth.getName();

    	//ユーザID
    	String esqId = "[" + userName + "] ";

    	//httpメソッド(GET or POST)
    	String httpMethod = "[" + request.getMethod() + "] ";

    	//handlerをHandlerMethodにcastする
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        //実行されるControllerのクラス名
        String controllerName = "[" + handlerMethod.getBeanType().getName() + "] ";

        //実行されるメソッドのメソッド名
        String methodName = "[" + handlerMethod.getMethod().getName() + "] ";

		logger.info(esqId + httpMethod + controllerName + methodName);
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		//do something
	}


	@Override
	public void afterCompletion(HttpServletRequest request,
		HttpServletResponse reponse, Object handler, Exception e)
		throws Exception {
		//do something
	}
}