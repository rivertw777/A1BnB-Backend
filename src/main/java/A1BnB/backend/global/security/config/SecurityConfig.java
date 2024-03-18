package A1BnB.backend.global.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import A1BnB.backend.global.security.config.filter.JwtAuthenticationFilter;
import A1BnB.backend.global.security.config.filter.JwtAuthorizationFilter;
import A1BnB.backend.global.security.config.handler.JwtAccessDeniedHandler;
import A1BnB.backend.global.security.config.handler.JwtAuthenticationEntryPoint;
import A1BnB.backend.global.security.config.handler.JwtAuthenticationFailureHandler;
import A1BnB.backend.global.security.utils.ResponseWriter;
import A1BnB.backend.global.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityService securityService;
    private final ResponseWriter responseWriter;

    // 보안 필터 체인 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // JWT 토큰 사용
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                // 커스텀 필터 적용
                .apply(new MyCustomFilter())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler(responseWriter))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(responseWriter))
                .and()
                .authorizeHttpRequests((authz) -> authz
                        // 회원 가입
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/users")).permitAll()
                        // 게시물 업로드
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/posts")).hasRole("HOST")
                        // 게시물 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/posts")).permitAll()
                        // 게시물 좋아요 수 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/posts/{\\d+}/like/count")).permitAll()
                        // 게시물 상세 조회
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/posts/{\\d+}")).permitAll()
                        // 게시물 검색
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/posts/search")).permitAll()
                        // 게시물 인기순 조회
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/posts/like")).permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    // 빈 생성 시 스프링의 내부 동작으로 UserSecurityService와 PasswordEncoder가 자동으로 설정됨
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
    public class MyCustomFilter extends AbstractHttpConfigurer<MyCustomFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            // 인증 필터 설정
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, securityService, responseWriter);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/security/login");
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler(responseWriter));

            // 인가 필터 설정
            JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(securityService, responseWriter);

            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}
