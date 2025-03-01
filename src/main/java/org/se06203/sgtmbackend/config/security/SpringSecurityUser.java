package org.se06203.sgtmbackend.config.security;

import lombok.Getter;
import lombok.Setter;
import org.se06203.sgtmbackend.persistence.entity.Users;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
public class SpringSecurityUser implements UserDetails, CredentialsContainer {
    private final String  id;
    private final String userName;
    private String password;
    private final Constants.role role;
    private final Set<GrantedAuthority> authorities;
    private final String phoneNumber;
    private final String email;
    private final ZoneId zoneId;
    private final String firstName;
    private final String lastName;

    public SpringSecurityUser(String id, //NOSONAR
                              String userName,
                              String password,
                              Constants.role role,
                              Collection<? extends GrantedAuthority> authorities,
                              String phoneNumber,
                              String email,
                              ZoneId zoneId,
                              String firstName,
                              String lastName
    ) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.zoneId = zoneId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    private SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>((g1, g2) -> {
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        });
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    public static SpringSecurityUser fromUser(Users user, List<String> authorities, Constants.role role) {
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new SpringSecurityUser(user.getId(),
                user.getUserName(),
                user.getPassword(),
                role,
                grantedAuthorities,
                user.getPhone(),
                user.getEmail(),
                ZoneId.systemDefault(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static SpringSecurityUser fromUser(Users user, ZoneId zoneId, List<String> authorities, Constants.role role) {
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new SpringSecurityUser(user.getId(),
                user.getUserName(),
                user.getPassword(),
                role,
                grantedAuthorities,
                user.getPhone(),
                user.getEmail(),
                zoneId,
                user.getFirstName(),
                user.getLastName()
        );
    }
}
