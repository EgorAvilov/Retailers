package by.itechart.retailers.security;


import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.security.jwt.JwtUser;
import by.itechart.retailers.security.jwt.JwtUserFactory;
import by.itechart.retailers.service.impl.LocationServiceImpl;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByEmail(username);
        if (user == null) {
            logger.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        logger.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}