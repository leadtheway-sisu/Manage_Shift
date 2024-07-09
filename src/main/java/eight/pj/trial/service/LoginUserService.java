package eight.pj.trial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eight.pj.trial.entity.LoginUser;
import eight.pj.trial.entity.LoginUserDto;
import eight.pj.trial.repository.LoginUserRepository;
import jakarta.transaction.Transactional;

@Service
public class LoginUserService implements UserDetailsService {
	@Autowired
	private LoginUserRepository loginUserRepository;  //private final
    @Autowired 
    private PasswordEncoder passwordEncoder;
    

    @Override // UserDetailsServiceインターフェースのメソッドを上書きします
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LoginUser user = loginUserRepository.findByName(name); // ユーザー名でユーザーを検索
        if (user == null) {
            throw new UsernameNotFoundException("User not found"); // ユーザーが見つからない場合、例外をスロー
        }
        return new LoginUserDetails(user); // ユーザーが見つかった場合、LoginUserDetailsを作成し返す
    }
	
    //ここから追加　2024/0625/16:40
    public LoginUser findByName(String name) {
        return loginUserRepository.findByName(name); // ユーザー名でユーザーを検索し返す
    }

    @Transactional // トランザクションを開始します。メソッドが終了したらトランザクションがコミットされます。
    public void save(LoginUserDto userdto) {
        // UserDtoからUser(LoginUser)への変換
        LoginUser user = new LoginUser();
        user.setName(userdto.getName());
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));  // パスワードをハッシュ化してから保存
        user.setEmployeeId(userdto.getEmployeeId());
        user.setRole(userdto.getRole());
        
        // データベースへの保存
        loginUserRepository.save(user); // UserRepositoryを使ってユーザーをデータベースに保存
    }
}
