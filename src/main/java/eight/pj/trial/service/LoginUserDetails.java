package eight.pj.trial.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import eight.pj.trial.entity.LoginUser;

import java.util.Collection;
import java.util.Collections;

public class LoginUserDetails implements UserDetails { // UserDetailsインターフェースを実装。Spring Securityでユーザー情報を扱うためのクラスで

    private LoginUser loginuser;
    private final Collection<GrantedAuthority> authorities;
    
 // コンストラクタでloginUserオブジェクトを受け取り、このクラスのloginuserにセット
    public LoginUserDetails(LoginUser loginuser) {
        this.loginuser = loginuser;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(loginuser.getRole()));
    }      

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
    }
    
    @Override
    public String getPassword() {
        return loginuser.getPassword();
    }

    @Override
    public String getUsername() {
        return loginuser.getName();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
