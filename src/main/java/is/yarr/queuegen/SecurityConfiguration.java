package is.yarr.queuegen;

import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // This could be your database lookup. There are some complete implementations in spring-security-web.
    @Bean UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
        return username -> {
            log.debug("Searching user: {}", username);
            switch (username) {
                case "guest" -> {
                    return new User(username, passwordEncoder.encode(username + "Password"), Collections.emptyList());
                }
                case "user" -> {
                    final List<SimpleGrantedAuthority> authorities =
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_GREET"));
                    return new User(username, passwordEncoder.encode(username + "Password"), authorities);
                }
                default -> {
                    throw new UsernameNotFoundException("Could not find user!");
                }
            }
        };
    }

    // One of your authentication providers.
    // They ensure that the credentials are valid and populate the user's authorities.
    @Bean DaoAuthenticationProvider daoAuthenticationProvider(
            final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder) {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Add the authentication providers to the manager.
    @Bean AuthenticationManager authenticationManager(final DaoAuthenticationProvider daoAuthenticationProvider) {
        final List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    // Configure which authentication types you support.
    @Bean GrpcAuthenticationReader authenticationReader() {
        return new BearerAuthenticationReader(token -> {
            System.out.println("token = " + token);
            return new BearerAuth(token.split(":")[0], token.split(":")[1]);
        });
//        return new BasicGrpcAuthenticationReader();
//         final List<GrpcAuthenticationReader> readers = new ArrayList<>();
//         readers.add(new BasicGrpcAuthenticationReader());
//         readers.add(new SSLContextGrpcAuthenticationReader());
//         return new CompositeGrpcAuthenticationReader(readers);
    }

    static class BearerAuth extends AbstractAuthenticationToken {

        private final Object principal;

        private Object credentials;

        /**
         * This constructor can be safely used by any code that wishes to create a
         * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
         * will return <code>false</code>.
         *
         */
        public BearerAuth(Object principal, Object credentials) {
            super(null);
            this.principal = principal;
            this.credentials = credentials;
            setAuthenticated(false);
        }

        /**
         * This constructor should only be used by <code>AuthenticationManager</code> or
         * <code>AuthenticationProvider</code> implementations that are satisfied with
         * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
         * authentication token.
         * @param principal
         * @param credentials
         * @param authorities
         */
        public BearerAuth(Object principal, Object credentials,
                                                   Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.principal = principal;
            this.credentials = credentials;
            super.setAuthenticated(true); // must use super, as we override
        }

        @Override
        public Object getCredentials() {
            return this.credentials;
        }

        @Override
        public Object getPrincipal() {
            return this.principal;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            Assert.isTrue(!isAuthenticated,
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
            super.setAuthenticated(false);
        }

        @Override
        public void eraseCredentials() {
            super.eraseCredentials();
            this.credentials = null;
        }

    }

}
