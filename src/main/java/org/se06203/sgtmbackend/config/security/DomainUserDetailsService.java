package org.se06203.sgtmbackend.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.sgtmbackend.persistence.repository.UserRolesRepository;
import org.se06203.sgtmbackend.persistence.repository.UsersRepository;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Override
    @Transactional(readOnly = true)
    public SpringSecurityUser loadUserByUsername(final String userName) {
        log.debug("Authenticating {}", userName);

        return userRepository
                .findByUserName(userName)
                .map(user -> {
                    var userAuthorities = userRolesRepository.findAllByUserId(Constants.role.USER,user.getId());
                    return SpringSecurityUser.fromUser(user, userAuthorities.stream()
                            .map(authority -> authority.getRole().name())
                            .toList(), Constants.role.USER);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User with userName " + userName + " was not found in the database"));
    }
}
