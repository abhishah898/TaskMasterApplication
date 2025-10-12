    package com.learn.TaskMaster.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    public class AuthConfig {
        @Bean
        public PasswordEncoder _passwordEncoder() {
            return new BCryptPasswordEncoder(11);
        }

        @Bean
        public SecurityFilterChain securityFilterChainAuth(HttpSecurity httpSecurity) throws Exception {

            httpSecurity
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/register").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(Customizer.withDefaults());
            return httpSecurity.build();
        }
    }
