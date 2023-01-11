package jp.co.isid.advtraining.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

import jp.co.isid.advtraining.log.AppLogger;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    @Bean
    AppLogger appLogger(){
        return new AppLogger();
    }

    @Bean
    public MappedInterceptor interceptor() {
    	//修正が必要
    	String[] url = {"/*", "/list/*", "/answer/*", "/admin/*", "/status/*",
    			"/edit/*", "/resultAll/*", "/result/*", "/login/*"};
    	//http.authorizeRequests().antMatchers("/", "/images/**", "/css/**", "/javascript/**", "/webjars/**").permitAll()
    	//一つで書かける

//    	String[] url = {"/*"};
        return new MappedInterceptor(url, appLogger());
        //{"/**"}から{"/*"}に変更
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(appLogger()).addPathPatterns("/*");
//    }
}