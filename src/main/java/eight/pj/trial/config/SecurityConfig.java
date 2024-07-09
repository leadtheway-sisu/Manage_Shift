package eight.pj.trial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(login->{
        		login.loginPage("/login")
        		.defaultSuccessUrl("/index",true)
        		.usernameParameter("name")
        		.passwordParameter("password")
        		.permitAll();  // ログインページは誰でもアクセスできる
        	})
        	.authorizeHttpRequests(auth->{
        		auth.requestMatchers("/","/register","/error","/img/**","/css/**","/js/**").permitAll()	// 誰でもアクセスできる(※)
        			.requestMatchers("/index","/user","/css/**","/js/**","/img/**").hasAnyAuthority("USER","ADMIN") // USER専用ページ
        			.requestMatchers("/admin", "/css/**","/js/**").hasAuthority("ADMIN") //.hasRole("ADMIN") //hasAuthority or hasRole 
        			.requestMatchers("/submitSuccess","/css/**").hasAnyAuthority("USER","ADMIN")
        			.anyRequest().authenticated();				// 認証済みの全員がアクセスできる
        		    
        	}).logout(logout->logout
        			.logoutUrl("/logout")	// ログアウトページ
        			.permitAll());

        return http.build();
    }
	
    @Bean 
    public PasswordEncoder passwordEncoder() {  // パスワードエンコーダー（パスワードのハッシュ化）を提供するメソッド
        return new BCryptPasswordEncoder();  // パスワードをBCrypt方式でハッシュ化するエンコーダーを返します
    }
    
}
