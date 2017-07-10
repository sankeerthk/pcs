	package com.pcs.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
    public SecurityConfiguration() {
        super();
    }

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		auth.authenticationEventPublisher(defaultAuthenticationEventPublisher());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/login**", "/logout**", "/forgotPassword**", "/setPassword/**", "/resetPassword/**",
				"/changePassword**", "/userConfirmation**", "/static/**").permitAll()
		.antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERUSER')")
		.antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
		.antMatchers("/internalAdmin/**").access("hasRole('ROLE_SUPERUSER')").anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login").loginProcessingUrl("/login")
			.defaultSuccessUrl("/user/home", true).failureUrl("/login?error=true")
			.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
			.usernameParameter("userName").passwordParameter("password")
		.and()
		.sessionManagement()
			.invalidSessionUrl("/login?session=expired").sessionFixation().changeSessionId()
		.and()
		.logout()
			.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		.and()
		.userDetailsService(userDetailsService).exceptionHandling().accessDeniedPage("/accessDenied");
	}

	/**
	 * Configure the PasswordEncoder that uses the BCrypt strong hashing
	 * function.
	 *
	 * @return PasswordEncoder the implementation of BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure the authentication provider with user details service ans
	 * password encoder.
	 * 
	 * @return DaoAuthenticationProvider
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setHideUserNotFoundExceptions(false);
		return authenticationProvider;
	}

	/**
	 * Basic implementation of AuthenticationTrustResolver.
	 * 
	 * @return AuthenticationTrustResolver
	 */
	/*@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}*/
	
	@Bean
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
}
