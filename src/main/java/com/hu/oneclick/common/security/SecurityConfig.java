package com.hu.oneclick.common.security;

import com.hu.oneclick.common.security.flutter.OptionsRequestFilter;
import com.hu.oneclick.common.security.handler.JsonLoginSuccessHandler;
import com.hu.oneclick.common.security.handler.JwtRefreshSuccessHandler;
import com.hu.oneclick.common.security.handler.TokenClearLogoutHandler;
import com.hu.oneclick.common.security.service.JwtAuthenticationProvider;
import com.hu.oneclick.common.security.service.JwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${onclick.config.interceptor.enable}")
    private String enable;

    private final JwtUserServiceImpl jwtUserService;
    private final JsonLoginSuccessHandler jsonLoginSuccessHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtRefreshSuccessHandler jwtRefreshSuccessHandler;
    private final TokenClearLogoutHandler tokenClearLogoutHandler;

    public SecurityConfig(JwtUserServiceImpl jwtUserService, JsonLoginSuccessHandler jsonLoginSuccessHandler, JwtAuthenticationProvider jwtAuthenticationProvider, JwtRefreshSuccessHandler jwtRefreshSuccessHandler, TokenClearLogoutHandler tokenClearLogoutHandler) {
        this.jwtUserService = jwtUserService;
        this.jsonLoginSuccessHandler = jsonLoginSuccessHandler;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtRefreshSuccessHandler = jwtRefreshSuccessHandler;
        this.tokenClearLogoutHandler = tokenClearLogoutHandler;
    }

//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login").anonymous()
//                .antMatchers("/user/register").anonymous()
//                .antMatchers("/user/sendEmailCode").anonymous()
//                .antMatchers("/user/sendEmailRegisterCode").anonymous()
//                .antMatchers("/user/activateAccount").anonymous()
//                .antMatchers("/user/forgetThePassword").anonymous()
//                .antMatchers("/user/forgetThePasswordIn").anonymous()
//                .antMatchers("/user/applyForAnExtension").anonymous()
//                .antMatchers("/user/applyForAnExtensionIn").anonymous()
//                .antMatchers("/user/verifyLinkString").anonymous()
//                .antMatchers("/swagger-ui.html").anonymous()
//                .antMatchers("/swagger-ui/**").anonymous()
//                .antMatchers("/v2/**").anonymous()
//                .antMatchers("/v3/**").anonymous()
//                .antMatchers("/swagger-resources/**").anonymous()
//                .antMatchers("/doc.html").anonymous()
//                .antMatchers("/webjars/**").anonymous()
//                .antMatchers("/webjars/springfox-swagger-ui").anonymous()
//                .antMatchers("/webjars/springfox-swagger-ui/**").anonymous()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .sessionManagement().disable()
//                .cors()
//                .and()
//                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
//                        new Header("Access-Control-Allow-Origin", "*"),
//                        new Header("Access-Control-Expose-Headers", "Authorization"))))
//                .and()
//                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
//                .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(jsonLoginSuccessHandler)
//                .and()
//                .apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler)
//                //设置无权限接口
//                .permissiveRequestUrls("/login","/user/register","/user/sendEmailCode",
//                        "/user/sendEmailRegisterCode","/user/activateAccount",
//                        "/user/forgetThePassword","/user/forgetThePasswordIn",
//                        "/user/applyForAnExtension","/user/applyForAnExtensionIn","/user/verifyLinkString",
//                        "/swagger-ui.html","/swagger-resources/**","/swagger-ui/**","/v3/**",
//                        "/doc.html","/webjars/**",
//                        "/v2/**","/webjars/springfox-swagger-ui/**","/webjars/springfox-swagger-ui"
//                )
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .addLogoutHandler(tokenClearLogoutHandler)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
//                .and()
//                .sessionManagement().disable();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 是否开启安全拦截, false 关闭
        if (!Boolean.parseBoolean(enable)) {
            // 不做任何安全检查
            http.authorizeHttpRequests((authorize) -> authorize
                            .anyRequest().permitAll()
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .formLogin(AbstractHttpConfigurer::disable)
                    .sessionManagement(AbstractHttpConfigurer::disable);
            return http.build();
        }
        // 设置无权限接口
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/user/register",
                                "/user/sendEmailCode",
                                "/user/sendEmailRegisterCode",
                                "/user/activateAccount",
                                "/user/forgetThePassword",
                                "/user/forgetThePasswordIn",
                                "/user/applyForAnExtension",
                                "/user/applyForAnExtensionIn",
                                "/user/verifyLinkString",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v2/**",
                                "/v3/**",
                                "/swagger-resources/**",
                                "/doc.html",
                                "/webjars/**",
                                "/webjars/springfox-swagger-ui",
                                "/webjars/springfox-swagger-ui/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers((headers) -> headers
                        .addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                                new Header("Access-Control-Allow-Origin", "*"),
                                new Header("Access-Control-Expose-Headers", "Authorization")
                        )))
                )
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                .addFilterBefore(new JsonLoginConfigurer<>().loginSuccessHandler(jsonLoginSuccessHandler), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtLoginConfigurer<>().tokenValidSuccessHandler(jwtRefreshSuccessHandler), UsernamePasswordAuthenticationFilter.class)
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(tokenClearLogoutHandler)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                );

        // 解决重复构建 AuthenticationManager 的问题
        AuthenticationManager authManager = authenticationManager(http);
        http.authenticationManager(authManager);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        // 配置 AuthenticationProvider
        auth.authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(jwtAuthenticationProvider);
        return auth.build();
    }

    @Bean("daoAuthenticationProvider")
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(jwtUserService);
        // 注意：这里应该设置密码编码器
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTION", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        // 忽略接口，不会经过 Spring Security
        return (web) -> web.ignoring()
                .requestMatchers("/login", "/user/register",
                        "/user/sendEmailCode",
                        "/user/sendEmailRegisterCode",
                        "/user/activateAccount",
                        "/user/forgetThePassword",
                        "/user/forgetThePasswordIn",
                        "/user/applyForAnExtension",
                        "/user/applyForAnExtensionIn",
                        "/user/verifyLinkString",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v2/**",
                        "/v3/**",
                        "/swagger-resources/**",
                        "/doc.html",
                        "/webjars/**",
                        "/webjars/springfox-swagger-ui",
                        "/webjars/springfox-swagger-ui/**");
    }

}

