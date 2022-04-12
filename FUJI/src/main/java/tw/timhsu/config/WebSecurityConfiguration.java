package tw.timhsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import tw.timhsu.security.oauth.CustomOAuth2UserService;
import tw.timhsu.security.oauth.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomOAuth2UserService oAuth2UserService;
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	@Autowired
	private AuthUserDetailsService myUserDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//                .antMatchers("/","/assest/**","/register","/home")
				.antMatchers("/oauth2/**").permitAll()
				.antMatchers("/", "/assest/**", "/register", "/home/**", "/stylesheets/**", "/js/**", "/images/**",
						"/register2", "/gotore","/api/**","/backend/**")
				.permitAll()
				.antMatchers("/profile/**").authenticated()
				// .antMatchers("/home").hasAnyAuthority("USER","ADMIN")
				.antMatchers("/admin").hasAuthority("ADMIN").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/home", true)
//                .defaultSuccessUrl("/authenticated")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll().and().oauth2Login().loginPage("/login").userInfoEndpoint().userService(oAuth2UserService)
				.and().successHandler(oAuth2LoginSuccessHandler);
		http.logout().deleteCookies("JSESSIONID").logoutSuccessUrl("/login")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.csrf().disable();

	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.authorizeRequests()
//		.antMatchers("/index.html").permitAll()
//		.antMatchers("/profile/**").authenticated()
//		.and()
//		.formLogin()
//		.loginPage("/login")
//		.usernameParameter("username")
//		.passwordParameter("password")
//		.and()
//		.logout()
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutSuccessUrl("/login")
//		.and()
//		.csrf().disable();
//	}

}
