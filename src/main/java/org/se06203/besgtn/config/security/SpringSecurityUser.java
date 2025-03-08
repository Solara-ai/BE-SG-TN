package org.se06203.besgtn.config.security;

import java.time.ZoneId;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.persistence.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class SpringSecurityUser implements UserDetails, CredentialsContainer {

    private final String id;
    private final String name;
    private String password;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final String role;
    private final ZoneId zoneId = ZoneId.systemDefault();

    public SpringSecurityUser(String id,
                              String name,
                              String password,
                              String gender,
                              String phoneNumber,
                              String email,
                              String role
    ) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return name;
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

    public static SpringSecurityUser fromUser(Users user) {

        return new SpringSecurityUser(user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.getEmail(),
                Constants.AuthorityEnum.USER.toString()
        );
    }

    public static SpringSecurityUser fromUser(Users user, Constants.AuthorityEnum authority) {

        return new SpringSecurityUser(user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.getEmail(),
                authority.toString()
        );
    }
}
