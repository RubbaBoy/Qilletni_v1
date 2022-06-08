package is.yarr.queuegen.grpc;

import is.yarr.queuegen.auth.SessionHandler;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The security configuration. If you use spring security for web applications most of the stuff is already configured.
 *
 * @author Daniel Theuke (daniel.theuke@heuboe.de)
 */
@Configuration(proxyBeanMethods = false)
// proxyTargetClass is required, if you use annotation driven security!
// However, you will receive a warning that GrpcServerService#bindService() method is final.
// You cannot avoid that warning (without massive amount of work), but it is safe to ignore it.
// The #bindService() method uses a reference to 'this', which will be used to invoke the methods.
// If the method is not final it will delegate to the original instance and thus it will bypass any security layer that
// you intend to add, unless you re-implement the #bindService() method on the outermost layer (which Spring does not).
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    // This could be your database lookup. There are some complete implementations in spring-security-web.
//    @Bean UserDetailsService userDetailsService() {
//        return username -> {
//            LOGGER.info("User: {}", username);
//            switch (username) {
//                case "guest" -> {
//                    return new User(username, passwordEncoder.encode(username + "Password"), Collections.emptyList());
//                }
//                case "user" -> {
//                    final List<SimpleGrantedAuthority> authorities =
//                            Arrays.asList(new SimpleGrantedAuthority("ROLE_GREET"));
//                    return new User(username, passwordEncoder.encode(username + "Password"), authorities);
//                }
//                default -> {
//                    throw new UsernameNotFoundException("Could not find user!");
//                }
//            }
//        };
//    }

    // Add the authentication providers to the manager.
    @Bean AuthenticationManager authenticationManager(SessionAuthenticationProvider sessionAuthenticationProvider) {
        return new ProviderManager(List.of(sessionAuthenticationProvider));
    }

    @Bean
    GrpcAuthenticationReader authenticationReader() {
        return new SessionAuthorizationReader();
    }

}
