package A1BnB.backend.domain.security.config;

import A1BnB.backend.domain.security.config.filter.JwtAuthenticationFilter;
import A1BnB.backend.domain.security.config.filter.JwtAuthorizationFilter;
import A1BnB.backend.domain.security.config.handler.JwtAccessDeniedHandler;
import A1BnB.backend.domain.security.config.handler.JwtAuthenticationEntryPoint;
import A1BnB.backend.domain.security.config.utils.ResponseWriter;
import A1BnB.backend.domain.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final ResponseWriter responseWriter;


    // 보안 필터 체인 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // jwt 토큰 사용
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 커스텀 필터 적용
                .apply(new MyCustomDsl())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler(responseWriter))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(responseWriter))
                .and()
                .authorizeRequests()
                // 회원가입
                .requestMatchers("/api/users").permitAll()
                // Swagger
                .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    // 빈 생성 시 스프링의 내부 동작으로 UserSecurityService와 PasswordEncoder가 자동으로 설정
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 비밀번호 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 커스텀 필터 설정
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // 인증 필터 설정
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, securityService, responseWriter);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/security/login");

            // 인가 필터 설정
            JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(securityService, responseWriter);

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}
