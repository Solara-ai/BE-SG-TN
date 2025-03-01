package org.se06203.sgtmbackend.service;
import org.se06203.sgtmbackend.config.exception.NotFoundException;
import org.se06203.sgtmbackend.config.exception.RegisterEmailExistException;
import org.se06203.sgtmbackend.config.security.JwtService;
import org.se06203.sgtmbackend.config.security.SpringSecurityUser;
import org.se06203.sgtmbackend.dto.request.EmailRequest;
import org.se06203.sgtmbackend.dto.request.RegisterRequest;
import org.se06203.sgtmbackend.dto.response.AuthenticateResponse;
import org.se06203.sgtmbackend.persistence.entity.UserRoles;
import org.se06203.sgtmbackend.persistence.entity.Users;
import org.se06203.sgtmbackend.persistence.repository.UsersRepository;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticateService extends BaseHandler {

    private final UsersRepository usersRepository;


    @Autowired
    public AuthenticateService(JwtService jwtService, UsersRepository userRepository, OTPService otpService, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        super(jwtService, userRepository, passwordEncoder);
        this.usersRepository = usersRepository;
    }


    public AuthenticateResponse authenticate(EmailRequest request) {

        SpringSecurityUser springSecurityUser = getAuthenticatedUser(request.getEmail(), request.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                springSecurityUser,
                request.getPassword(),
                springSecurityUser.getAuthorities()
        );

        try {
            //        todo BONUS_PRICE from admin
            return super.setAuthenticationContextAndGenerateToken(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new NotFoundException("Cannot found account");
        }
    }

    @Transactional
    public AuthenticateResponse register(RegisterRequest rq) {
//        if (!otpService.isValidOtpTransaction(rq.getTransId(), rq.getEmail())) {
//            throw new OtpInvalidException("register");
//        }

        usersRepository.findByEmailAndRole(rq.getEmail(), Constants.role.USER)
                .ifPresentOrElse(
                        user -> {
                            throw new RegisterEmailExistException();
                        },
                        () -> {
                            var savedUser = usersRepository.save(Users.builder()
                                    .userName(rq.getUserName())
                                    .email(rq.getEmail())
                                    .password(passwordEncoder.encode(rq.getPassword()))
                                    .role(rq.getRoles())
                                    .build());
                        }
                );
        //        todo BONUS_PRICE from admin
        return this.authenticate(EmailRequest.builder()
                .email(rq.getEmail())
                .password(rq.getPassword())
                .build());
    }

}
