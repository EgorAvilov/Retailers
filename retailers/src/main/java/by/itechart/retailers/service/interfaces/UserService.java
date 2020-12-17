package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Role;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto findById(long userId);

    Page<UserDto> findAll(Pageable pageable);

    UserDto create(UserDto userDto) throws BusinessException;

    UserDto create(CustomerDto customerDto) throws BusinessException;

    UserDto update(UserDto userDto) throws BusinessException;

    UserDto getCurrentUser();

    UserDto findByEmail(String email);

    String generatePassword();

    String encodePassword(String password);

    List<UserDto> updateStatus(List<Long> userIds);

    List<UserDto> findByBirthday(LocalDate date);

    List<UserDto> findAllByRole(Role role);

    boolean emailExists(String email);

    boolean loginExists(String login);

    List<UserDto> findAllById(List<Long> ids);

    UserDto updatePassword(UserDto userDto);
}
