package com.jhipster.demo.store.web.rest;

import com.jhipster.demo.store.domain.User;
import com.jhipster.demo.store.repository.UserRepository;
import com.jhipster.demo.store.security.SecurityUtils;
import com.jhipster.demo.store.service.MailService;
import com.jhipster.demo.store.service.UserService;
import com.jhipster.demo.store.service.dto.*;
import com.jhipster.demo.store.web.rest.errors.*;
import com.jhipster.demo.store.web.rest.vm.KeyAndPasswordVM;
import com.jhipster.demo.store.web.rest.vm.ManagedUserVM;
import jakarta.mail.Message;
import jakarta.validation.Valid;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        return userService.registerUser(managedUserVM, managedUserVM.getPassword(), managedUserVM.getPhoneNumber()).then();
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public Mono<Void> activateAccount(@RequestParam(value = "key") String key) {
        return userService
            .activateRegistration(key)
            .switchIfEmpty(Mono.error(new AccountResourceException("No user was found for this activation key")))
            .then();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public Mono<AdminUserDTO> getAccount() {
        return userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .switchIfEmpty(Mono.error(new AccountResourceException("User could not be found")));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public Mono<Void> saveAccount(@Valid @RequestBody AdminUserDTO userDTO) {
        return SecurityUtils
            .getCurrentUserLogin()
            .switchIfEmpty(Mono.error(new AccountResourceException("Current user login not found")))
            .flatMap(userLogin ->
                userRepository
                    .findOneByEmailIgnoreCase(userDTO.getEmail())
                    .filter(existingUser -> !existingUser.getLogin().equalsIgnoreCase(userLogin))
                    .hasElement()
                    .flatMap(emailExists -> {
                        if (emailExists) {
                            throw new EmailAlreadyUsedException();
                        }
                        return userRepository.findOneByLogin(userLogin);
                    })
            )
            .switchIfEmpty(Mono.error(new AccountResourceException("User could not be found")))
            .flatMap(user ->
                userService.updateUser(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getEmail(),
                    userDTO.getLangKey(),
                    userDTO.getImageUrl()
                )
            );
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public Mono<Void> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        return userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }
    @PostMapping(path = "/reset-password")
    public Mono<ResponseEntity<MessageDTO>> changePassword(@RequestBody PasswordDTO passwordDTO) {
        if (isPasswordLengthInvalid(passwordDTO.getPassword())) {
            throw new InvalidPasswordException();
        }
        return userService.resetPassword(passwordDTO.getPhoneNumber(),passwordDTO.getPassword())
            .map(bool -> ResponseEntity.status(bool ? 200 : 400)
                .body(new MessageDTO (bool ? "Password reset successfully" : "error while resetting password",bool ? 200 : 400)));
    }
    @PostMapping(path = "/forgot-password")
    public Mono<ResponseEntity<MessageDTO>> forgotPassword(@RequestBody PhoneNumberDTO phoneNumberDTO) {
        return userService.sendMessage(phoneNumberDTO.getPhoneNumber())
            .thenReturn("Password reset instructions sent to " + phoneNumberDTO.getPhoneNumber())
            .map(message -> ResponseEntity.ok().body(new MessageDTO(message,200)))
            .defaultIfEmpty(ResponseEntity.badRequest().body(new MessageDTO("Failed to send password reset instructions",400)));
    }
    @PostMapping(path = "/validate-code")
    public ResponseEntity<MessageDTO> validateCode(@RequestBody CodeDTO codeDTO) {

        boolean bool = userService.checkCode(codeDTO.getCode(),codeDTO.getPhoneNumber());
        return ResponseEntity.status(bool ? 200 : 400).body(new MessageDTO(bool ? "Code is correct !" : "Invalid code!",bool ? 200 : 400));
    }
    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public Mono<Void> requestPasswordReset(@RequestBody String mail) {
        return userService
            .requestPasswordReset(mail)
            .doOnSuccess(user -> {
                if (Objects.nonNull(user)) {
                    mailService.sendPasswordResetMail(user);
                } else {
                    // Pretend the request has been successful to prevent checking which emails really exist
                    // but log that an invalid attempt has been made
                    log.warn("Password reset requested for non existing mail");
                }
            })
            .then();
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public Mono<Void> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        return userService
            .completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
            .switchIfEmpty(Mono.error(new AccountResourceException("No user was found for this reset key")))
            .then();
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
