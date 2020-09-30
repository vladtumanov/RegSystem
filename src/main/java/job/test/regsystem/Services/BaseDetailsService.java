package job.test.regsystem.Services;

import job.test.regsystem.Entity.User;
import job.test.regsystem.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class BaseDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BaseDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        User userTemp = user.get();
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();

        if (userTemp.isAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (userTemp.isOperator())
            authorities.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
        if (userTemp.isClient())
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));

        return new org.springframework.security.core.userdetails.
                User(userTemp.getLogin(), userTemp.getPassword(), authorities);
    }
}
