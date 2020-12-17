package by.itechart.retailers.security.jwt;

import by.itechart.retailers.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String email;
    private final String password;
    private final boolean isActive;
    private final boolean isCustomerActive;

    public JwtUser(Long id,
                   String firstName,
                   String lastName,
                   Collection<? extends GrantedAuthority> authorities,
                   String email,
                   String password,
                   Status isActive,
                   Status isCustomerActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.email = email;
        this.password = password;
        this.isActive = isActive.equals(Status.ACTIVE);
        this.isCustomerActive = isCustomerActive.equals(Status.ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isCustomerActive;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
