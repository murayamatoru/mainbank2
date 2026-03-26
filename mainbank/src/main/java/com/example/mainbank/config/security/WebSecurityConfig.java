package com.example.mainbank.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    /**
     * セキュリティ設定
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
              //  .loginPage("/login") // loginPageを指定無しにすると、Spring Securityは自前のデフォルトログイン画面を表示する
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login")
            );

        return http.build();
    }


    /**
     * 学習用ユーザー
     */
    @Bean
    UserDetailsService userDetailsService() {

        return new InMemoryUserDetailsManager(

            User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build()

        );
    }
}

/*
========
追加説明(1)
========
Spring Securityの挙動について
（１）SpringInitialzrで初期プロジェクト作成時に
DependenciesからSpring Securityを追加した場合
そのプロジェクトを起動するとログイン画面が表示される。
test / test（？） でログイン可能

（２）DependenciesにSpring Securityを追加したプロジェクトで
WebSecurityConfig（@Configuration　@EnableWebSecurityが付いたクラス）で挙動を設定する。
注：@EnableWebSecurityが無くてもテスト出来た。SpringInitialzrが何か設定したかも。

（３）無限リダイレクトびなるケース
            .formLogin(form -> form
              //  .loginPage("/login") // loginPageを指定無しにすると、Spring Securityは自前のデフォルトログイン画面を表示する
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
 .loginPageかあると、/loginを処理するコントローラ、ビューは自作していないので、ログイン処理不能となって
再度ログインを試みる、その結果、リダイレクトが繰り返される。（詳細は必要になったら再調査する。ここではここまで）
無限リダイレクトとなることがSpringSecurityの仕様。
画面はこのサイトは動いていません。リダイレクトが繰り返されました…と表示される。

（４）無限リダイレクトを回避する方法
① /loginを処理するコントローラ、ビューを自作する
②  .loginPage("/login") をコメントアウトする
loginPageが指定されていない場合、SpringSecurityは自前のログイン画面を表示するので、
admin / password でログインできる。
*/ 

/*
ここから 補足解説
Webアプリケーションの認証・認可設定において、
「ログインページは誰でも見れるが、それ以外のページはログインが必要」
という標準的なセキュリティルールを定義しています。
それぞれの意味は以下の通りです。

1. .requestMatchers("/login").permitAll()
意味: /login というパス（URL）に対するアクセスは、認証なし（ログインなし）で誰でも許可する。
解説: permitAll() は、ログインしていないユーザーでもアクセスできるようにする設定です。
ログインページ自体にログインを求めてしまうと、誰もログインできなくなる（無限ループする）ため、通常この設定が必要です。 

2. .anyRequest().authenticated()
意味: 上記（/login）以外のすべてのリクエストは、認証済み（ログイン済み）のユーザーのみアクセスを許可する。
解説: anyRequest() は「上記で設定した以外のすべてのURL」を指し、authenticated() は「ログインしていること」を条件とします。これにより、ログイン画面以外の全ページが保護されます。 

まとめ
この2行をセットで使うことで、アプリケーションのセキュリティの基本を実装できます。
コード 	意味	用途
.requestMatchers("/login").permitAll()	/loginは誰でもOK	ログイン画面を表示する
.anyRequest().authenticated()	それ以外はログイン必須	全ページを保護する
よくある組み合わせ:
これらに加え、ログイン成功時の挙動やログアウト設定と組み合わせて、安全なアプリケーションの入り口を作ります。

http
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login", "/css/**", "/js/**").permitAll() // CSS/JSも許可
        .anyRequest().authenticated()
    )
    .formLogin(form -> form
        .loginPage("/login")
        .permitAll()
    );
ここまで
*/


//package com.example.mainbank.config.security;
//
///*
//SpringSecurotyを無効（デフォルト挙動）にするためには
//@Configuration
//@EnableWebSecurity
//の付いたクラス
//例：public class WebSecurityConfig {
//コメントアウトする or 削除する。
//
//このようにセキュリティ関係を一切未設定にすると、
//SpringSecurityはデフォルト動作になり、
//①全ページ認証必須、
//②デフォルトログイン表示、==> デフォルトユーザは id=test password=test と思われる
//③デフォルトユーザが自動生成される。
//*/
//
///**
// * Spring Securityの設定を行うクラス。
// * 1. ConfigurationとEnableWebSecurityアノテーションを付ける
// * 2. SecurityFilterChainを返すメソッドにBeanアノテーションを付ける
// * 上記の2つが必要。
// * パスワードをハッシュ化する場合は
// * 3.PasswordEncoderを返すメソッドにBeanアノテーションを付ける
// * も行う必要あり。
// * 
// */
////@Configuration
////@EnableWebSecurity
////@RequiredArgsConstructor
////public class WebSecurityConfig {
//// 
////    @Bean
////    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) //全てのページを認証不要にすうる
////            .csrf(csrf -> csrf.disable()); //クロスサイトリクエストフォージェリ
////        return http.build();
////    }
////	
//////    /**
//////     * 基本的な設定はここで行う。
//////     */
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        // @formatter:off
//////  
//////        // アクセス権限に関する設定
//////        http
//////            .authorizeHttpRequests(
//////                // /はアクセス制限をかけない
//////                (requests) -> requests.requestMatchers("/").permitAll()
//////                // /adminはADMINロールを持つユーザだけアクセス可能
//////                .requestMatchers("/admin").hasRole("ADMIN")
//////                // /userはUSERロールを持つユーザだけアクセス可能
//////                .requestMatchers("/user").hasRole("USER")
//////                // それ以外のページは認証が必要
//////                .anyRequest().authenticated()
//////            ).formLogin((form) -> form
//////                // ログインを実行するページを指定。
//////                // この設定だと/にPOSTするとログイン処理を行う
//////                .loginProcessingUrl("/")
//////                // ログイン画面の設定
//////                .loginPage("/")
//////                // ログインに失敗した場合の遷移先
//////                .failureUrl("/")
//////                // ユーザIDとパスワードのname設定
//////                .usernameParameter("username")
//////                .passwordParameter("password")
//////                // ログインに成功した場合の遷移先
//////                .defaultSuccessUrl("/common", true)
//////            ).logout((form) -> form
//////                // ログアウト処理を行うページ指定、ここにPOSTするとログアウトする
//////                .logoutUrl("/logout")
//////                // ログアウトした場合の遷移先
//////                .logoutSuccessUrl("/")
//////            );
////// 
//////        // @formatter:on
//////       // return http.build();
//////        return null;
//////    }
////// 
////// 
//////    /**
//////     * パスワードのハッシュ化を行うアルゴリズムを返す
//////     */
//////    @Bean
//////    public PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    } 
////}