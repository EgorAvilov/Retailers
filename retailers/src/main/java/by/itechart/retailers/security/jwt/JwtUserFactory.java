package by.itechart.retailers.security.jwt;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Role;
import by.itechart.retailers.entity.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(UserDto userDto) {
        return new JwtUser(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                mapToGrantedAuthorities(userDto.getUserRole()),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getUserStatus(),
                getCustomerStatus(userDto)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList());
    }

    private static Status getCustomerStatus(UserDto userDto) {
        if (userDto.getUserRole().contains(Role.SYSTEM_ADMIN)) {
            return Status.ACTIVE;
        } else {
            return userDto.getCustomer() .getCustomerStatus();
        }
    }
}
