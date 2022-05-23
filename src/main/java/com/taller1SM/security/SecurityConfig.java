package com.taller1SM.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;



	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/templatesProduct/Index").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProduct/").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesLocation/").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProducCostHistoric/").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProductInventory/").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProduct/add-product/").hasRole("admin").
		antMatchers(HttpMethod.GET, "/templatesProduct/edit/**").hasRole("admin").
		antMatchers(HttpMethod.GET, "/templatesProduct/Index/**").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesLocation/add-location/").hasRole("admin").
		antMatchers(HttpMethod.GET, "/templatesLocation/edit/**").hasRole("admin").
		antMatchers(HttpMethod.GET, "/templatesLocation/Index/**").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProductCostHistoric/add-productCostHistoric/").hasRole("operador").
		antMatchers(HttpMethod.GET, "/templatesProductCostHistoric/edit/**").hasRole("operador").
		antMatchers(HttpMethod.GET, "/templatesProductCostHistoric/Index/**").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/templatesProductInventory/add-productInventory/").hasRole("operador").
		antMatchers(HttpMethod.GET, "/templatesProductInventory/edit/**").hasRole("operador").
		antMatchers(HttpMethod.GET, "/templatesProductInventory/Index/**").hasAnyRole("admin","operador").
		antMatchers(HttpMethod.GET, "/").hasAnyRole("admin","operador").and().formLogin().loginPage("/login").permitAll().and().logout()
		.invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
}