package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.interfaces.CredentialsService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static by.itechart.retailers.constant.PasswordConstants.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CustomerConverter customerConverter;
    private final BCryptPasswordEncoder encoder;
    private final CredentialsService credentialsService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           CustomerConverter customerConverter,
                           @Lazy BCryptPasswordEncoder encoder,
                           CredentialsService credentialsService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.customerConverter = customerConverter;
        this.encoder = encoder;
        this.credentialsService = credentialsService;
    }

    @Override
    public List<UserDto> updateStatus(List<Long> userIds) {
        logger.info("Update status {}", userIds.toString());
        List<User> users = userRepository.findAllById(userIds);
        List<User> undeletedUsers = new ArrayList<>(users);
        for (User user : users) {
            if (user.getLocation()
                    .getStatus() != DeletedStatus.DELETED) {
                if (user.getUserStatus()
                        .equals(Status.ACTIVE)) {
                    user.setUserStatus(Status.DISABLED);
                } else {
                    user.setUserStatus(Status.ACTIVE);
                }
                userRepository.save(user);
                undeletedUsers.remove(user);
            }
        }
        return userConverter.entityToDto(undeletedUsers);
    }

    @Override
    @Transactional
    public List<UserDto> findByBirthday(LocalDate date) {
        logger.info("Find by birthday {}", date);
        List<User> users = userRepository.findAllByBirthdayAndUserStatus(date, Status.ACTIVE);
        return userConverter.entityToDto(users);
    }

    @Override
    @Transactional
    public List<UserDto> findAllByRole(Role role) {
        logger.info("Find by role {}", role.toString());
        List<User> users = userRepository.findAllByUserRoleAndUserStatus(role, Status.ACTIVE);
        return userConverter.entityToDto(users);
    }

    @Override
    public boolean emailExists(String email) {
        logger.info("Check for existing email {}", email);
        return userRepository.findAllByEmailIgnoreCase(email)
                             .size() != 0;
    }

    @Override
    public boolean loginExists(String login) {
        logger.info("Check for existing login {}", login);
        return userRepository.findAllByLoginIgnoreCase(login)
                             .size() != 0;
    }

    @Override
    @Transactional
    public List<UserDto> findAllById(List<Long> ids) {
        logger.info("Find all by id ");
        List<User> users = userRepository.findAllById(ids);
        return userConverter.entityToDto(users);
    }

    @Override
    public UserDto updatePassword(UserDto userDto) {
        logger.info("Update user password");
        UserDto currentUserDto = getCurrentUser();
        userDto.setCustomer(currentUserDto.getCustomer());
        Long customerId = currentUserDto.getCustomer()
                                        .getId();
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.findByIdAndCustomer_Id(user.getId(), customerId)
                                         .orElse(new User());
        persistUser.setPassword(encodePassword(user.getPassword()));
        persistUser = userRepository.save(persistUser);
        return userConverter.entityToDto(persistUser);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser() {
        logger.info("Get user");
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByEmail(currentPrincipalName);
    }

    @Override
    @Transactional
    public UserDto findByEmail(String email) {
        logger.info("Find by email {}", email);
        User user = userRepository.findByEmail(email);
        return userConverter.entityToDto(user);
    }

    @Override
    public String generatePassword() {
        logger.info("Generate password");
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] charPassword = new char[8];
        charPassword[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        charPassword[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        charPassword[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        charPassword[3] = numbers.charAt(random.nextInt(numbers.length()));
        for (int i = 4; i < 8; i++) {
            charPassword[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(charPassword);
    }

    @Override
    public String encodePassword(String password) {
        logger.info("Encode password");
        return encoder.encode(password);
    }

    @Override
    public UserDto findById(long userId) {
        logger.info("Find by id {}", userId);
        UserDto userDto = getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        User user = userRepository.findByIdAndCustomer_Id(userId, customerId)
                                  .orElse(new User());
        return userConverter.entityToDto(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = getCurrentUser();
        Page<User> userPage = userRepository.findAllByCustomer_IdAndUserRoleIsNotContaining(pageable, userDto.getCustomer()
                                                                                                             .getId(), Role.ADMIN);
        List<UserDto> userDtos = userConverter.entityToDto(userPage.getContent());
        return new PageImpl<>(userDtos, pageable, userPage.getTotalElements());
    }

    @Override
    public UserDto create(UserDto userDto) throws BusinessException {
        logger.info("Create by admin");
        UserDto currentUserDto = getCurrentUser();
        userDto.setCustomer(currentUserDto.getCustomer());
        User user = userConverter.dtoToEntity(userDto);
        if (emailExists(user.getEmail())) {
            throw new BusinessException("Email should be unique");
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        User persistUser = userRepository.save(user);
        persistUser.setPassword(password);
        userDto = userConverter.entityToDto(persistUser);
        credentialsService.sendCredentials(userDto);
        return userDto;
    }

    @Override
    public UserDto create(CustomerDto customerDto) throws BusinessException {
        logger.info("Create by system admin");
        Customer customer = customerConverter.dtoToEntity(customerDto);
        if (emailExists(customer.getEmail())) {
            logger.error("Not unique email {}", customer.getEmail());
            throw new BusinessException("Email should be unique");
        }
        User user = new User();
        user.setFirstName(customerDto.getName());
        user.setLastName(customerDto.getName());
        user.setEmail(customerDto.getEmail());
        user.setCustomer(customer);
        String password = generatePassword();
        user.setPassword(password);
        new Thread(() -> credentialsService.sendCredentials(userConverter.entityToDto(user))).start();
        user.setPassword(encodePassword(password));
        user.setUserStatus(Status.ACTIVE);
        user.setUserRole(Collections.singletonList(Role.ADMIN));
        userRepository.save(user);
        return userConverter.entityToDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) throws BusinessException {
        logger.info("Update");
        UserDto currentUserDto = getCurrentUser();
        userDto.setCustomer(currentUserDto.getCustomer());
        Long customerId = currentUserDto.getCustomer()
                                        .getId();
        User user = userConverter.dtoToEntity(userDto);
        User persistUser = userRepository.findByIdAndCustomer_Id(user.getId(), customerId)
                                         .orElse(new User());
        if (!user.getEmail()
                 .equals(persistUser.getEmail())) {
            if (emailExists(user.getEmail())) {
                logger.error("Not unique email {}", user.getEmail());
                throw new BusinessException("Email should be unique");
            }
        }
        if (!user.getLogin()
                 .equals(persistUser.getLogin())) {
            if (loginExists(user.getLogin())) {
                logger.error("Not unique login {}", user.getLogin());
                throw new BusinessException("Login should be unique");
            }
        }
        persistUser.setAddress(user.getAddress());
        persistUser.setBirthday(user.getBirthday());
        persistUser.setEmail(user.getEmail());
        persistUser.setLogin(user.getLogin());
        persistUser.setFirstName(user.getFirstName());
        persistUser.setLastName(user.getLastName());
        persistUser.setUserRole(user.getUserRole());
        persistUser.setUserStatus(user.getUserStatus());
        persistUser.setCustomer(user.getCustomer());
        persistUser.setLocation(user.getLocation());
        persistUser.setLogin(user.getLogin());
        persistUser = userRepository.save(persistUser);
        return userConverter.entityToDto(persistUser);
    }
}
