package tw.timhsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;

import java.security.Principal;
import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user1 = userService.findByUsername(username);
        if (user1.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        Users user = userService.findUserByUsername(username);
        return new AuthUserDetails(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserProfiles uProfiles = uService.findByName(username);
//        return new User(uProfiles.getName(), uProfiles.getPassword(), Collections.EMPTY_LIST);
//    }
}
