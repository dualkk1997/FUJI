package tw.timhsu.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;


@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private UsersService usersService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getEmail();
		String name = oAuth2User.getName();
		
		Users user = usersService.findByEmail(email);
		if (user == null) {
			usersService.createNewCustomerAfterOAuthLoginSuccess(email, name);}
//		else {
//			userService.updateCustomerAfterOAuthLoginSuccess(user, name);
//		}

		System.out.println(email);
		super.onAuthenticationSuccess(request, response, authentication);
	}
	

}
