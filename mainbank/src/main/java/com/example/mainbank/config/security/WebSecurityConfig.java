package com.example.mainbank.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
 
/**
 * Spring Securityの設定を行うクラス。
 * 1. ConfigurationとEnableWebSecurityアノテーションを付ける
 * 2. SecurityFilterChainを返すメソッドにBeanアノテーションを付ける
 * 上記の2つが必要。
 * パスワードをハッシュ化する場合は
 * 3.PasswordEncoderを返すメソッドにBeanアノテーションを付ける
 * も行う必要あり。
 * 
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

	
	
	
	
//    /**
//     * 基本的な設定はここで行う。
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // @formatter:off
//  
//        // アクセス権限に関する設定
//        http
//            .authorizeHttpRequests(
//                // /はアクセス制限をかけない
//                (requests) -> requests.requestMatchers("/").permitAll()
//                // /adminはADMINロールを持つユーザだけアクセス可能
//                .requestMatchers("/admin").hasRole("ADMIN")
//                // /userはUSERロールを持つユーザだけアクセス可能
//                .requestMatchers("/user").hasRole("USER")
//                // それ以外のページは認証が必要
//                .anyRequest().authenticated()
//            ).formLogin((form) -> form
//                // ログインを実行するページを指定。
//                // この設定だと/にPOSTするとログイン処理を行う
//                .loginProcessingUrl("/")
//                // ログイン画面の設定
//                .loginPage("/")
//                // ログインに失敗した場合の遷移先
//                .failureUrl("/")
//                // ユーザIDとパスワードのname設定
//                .usernameParameter("username")
//                .passwordParameter("password")
//                // ログインに成功した場合の遷移先
//                .defaultSuccessUrl("/common", true)
//            ).logout((form) -> form
//                // ログアウト処理を行うページ指定、ここにPOSTするとログアウトする
//                .logoutUrl("/logout")
//                // ログアウトした場合の遷移先
//                .logoutSuccessUrl("/")
//            );
// 
//        // @formatter:on
//       // return http.build();
//        return null;
//    }
// 
// 
//    /**
//     * パスワードのハッシュ化を行うアルゴリズムを返す
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
     
}
