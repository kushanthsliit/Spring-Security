package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtRequestFilter;
import com.example.demo.services.myUserDetailsService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private myUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

//AUTHENTICATION
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(myUserDetailsService);
	}
	
//AUTHORIZATION
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/hi").hasAnyAuthority("USER")
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
//		 http.authorizeRequests()
//         .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//         .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
//         .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//         .antMatchers("/delete/**").hasAuthority("ADMIN")
//         .anyRequest().authenticated()
//         .and()
//         .formLogin().permitAll()
//         .and()
//         .logout().permitAll()
//         .and()
//         .exceptionHandling().accessDeniedPage("/403")
//         ;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
