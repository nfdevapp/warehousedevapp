//package org.example.backend.security.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/",
//                                "/oauth2/**",
//                                "/login",
//                                "/api/auth/me"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth -> oauth
//                        .loginPage("/oauth2/authorization/github")
//                        .defaultSuccessUrl("http://localhost:5173/", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/auth/logout") //
//                        .logoutSuccessUrl("http://localhost:5173/login") // wohin nach logout
//                        .deleteCookies("JSESSIONID") // session cookie löschen
//                        .invalidateHttpSession(true)
//                );
//
//        return http.build();
//    }
//}
//
//
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(a -> a
////                        //user muss nicht eingeloggt sein, für alle zugänglichv => loginPage
////                        .requestMatchers("/api/example").permitAll()
////                        //user muss eingeloggt sein => WarehousePage
//////                        .requestMatchers(HttpMethod.GET, "/pi/warehouse").authenticated()
////                        .requestMatchers(HttpMethod.GET,"/api/warehouse").hasAnyAuthority("USER")
////                        .requestMatchers(HttpMethod.GET,"/api/product").hasAnyAuthority("USER")
////                        .anyRequest().permitAll())
////                //ausgeloggt => zurück zu loginPage
////                .logout(l -> l.logoutSuccessUrl("/api/example"))
////                //Logout => erfolgreich zurück zu loginPage
////                .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:5173"));
////        return http.build();
////    }
////    //Muster von Video
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http
//////                .csrf(AbstractHttpConfigurer::disable)
//////                .authorizeHttpRequests(a -> a
//////                        .requestMatchers(HttpMethod.GET, "/api/example").authenticated()
//////                        .requestMatchers("/api/example").permitAll()
//////                        .requestMatchers("/api/example").hasAnyAuthority("USER")
//////                        .anyRequest().permitAll())
//////                .logout(l -> l.logoutSuccessUrl("http://localhost:5173"))
//////                .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:5173"));
//////        return http.build();
//////    }
////    //Mein Beispiel
//////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//////        http
//////                .csrf(csrf -> csrf.disable())
//////
//////                .authorizeHttpRequests(auth -> auth
//////                        .requestMatchers("/api/auth/me").authenticated()
//////                        .anyRequest().permitAll()
//////                )
//////                .oauth2Login(oauth -> oauth
//////                        .loginPage("/oauth2/authorization/github")
//////                )
//////
//////                .logout(logout -> logout
//////                        .logoutSuccessUrl("http://localhost:5173")
//////                );
//////
//////        return http.build();
//////    }
////
////
