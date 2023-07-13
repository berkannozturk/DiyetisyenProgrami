package com.bilgeadam.diyetisyen.configuration;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDns;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDyt;


import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

	@Autowired
	private JpaUserDetailsServiceDyt serviceDyt;
	
	@Autowired
	private JpaUserDetailsServiceDns serviceDns;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable()).
				authorizeRequests(auth->auth
						.antMatchers("/").permitAll()	
						.antMatchers("/iletisim").permitAll()
						.antMatchers("/error").permitAll()
						.antMatchers("/searchDyt").permitAll()
						.antMatchers("/searchDns").permitAll()
						.anyRequest().authenticated()
						)
				.formLogin()
						.loginPage("/login").permitAll()
						.loginProcessingUrl("/dologin").permitAll()
						.failureUrl("/login?error=true").permitAll()
						
						.successHandler(new AuthenticationSuccessHandler() {
						
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {
							
							UserDetails user =(UserDetails)authentication.getPrincipal();
								
								log.info(" {} , {} sistemden giriş yaptı.", user.getUsername() , LocalDateTime.now());
							
								response.sendRedirect(request.getContextPath());
								
						}
						})
						.and()
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login")
						.addLogoutHandler(new LogoutHandler() {
							
							@Override
							public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
							
								if(authentication != null) {
									
									UserDetails user =(UserDetails)authentication.getPrincipal();
									
									log.info(" {} , {} sistemden çıkış yaptı.", user.getUsername() , LocalDateTime.now());
								}
								
								
							}
						})
						.invalidateHttpSession(true)
						)
				.userDetailsService(serviceDns)
				.userDetailsService(serviceDyt)
				.exceptionHandling()
				.accessDeniedPage("/accessdenied")
				.and()
				.build();
	}
	
	@Bean
	PasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}

	
	public class SavedRequestAwareAuthenticationSuccessHandler extends
	SimpleUrlAuthenticationSuccessHandler {


private RequestCache requestCache = new HttpSessionRequestCache();

@Override
public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response, Authentication authentication)
		throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

	if (savedRequest == null) {
		super.onAuthenticationSuccess(request, response, authentication);

		return;
	}
	String targetUrlParameter = getTargetUrlParameter();
	if (isAlwaysUseDefaultTargetUrl()
			|| (targetUrlParameter != null && (request
					.getParameter(targetUrlParameter)) != "")) {
		requestCache.removeRequest(request, response);
		super.onAuthenticationSuccess(request, response, authentication);

		return;
	}

	clearAuthenticationAttributes(request);

	
	String targetUrl = savedRequest.getRedirectUrl();
	
	getRedirectStrategy().sendRedirect(request, response, targetUrl);
}

	}
}

