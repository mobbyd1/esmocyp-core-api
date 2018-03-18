package esmocyp.core.api;

import esmocyp.core.api.auth.CustomUserDetails;
import esmocyp.core.api.dao.UserRepository;
import esmocyp.core.api.service.UserService;
import esmocyp.core.api.model.Role;
import esmocyp.core.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class EsmocypeCoreApiApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EsmocypeCoreApiApplication.class, args);
	}

	@Autowired
	public void authenticationManager(
			AuthenticationManagerBuilder builder
			, UserRepository repository
			, UserService userService ) throws Exception {

		if ( repository.count() == 0) {
			final List<Role> roles = Arrays.asList(new Role("USER"), new Role("ACTUATOR"));
			final User user = new User("user", "user", roles);

			userService.save(user);
		}

		builder.userDetailsService(userDetailsService(userService)).passwordEncoder(passwordEncoder);
	}

	private UserDetailsService userDetailsService(final UserService service) {
		return username -> new CustomUserDetails(service.findByUserName(username));
	}
}
