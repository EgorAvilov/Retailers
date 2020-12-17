package by.itechart.retailers.converter;

import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserDto entityToDto(User user) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }

    public User dtoToEntity(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }

    public List<UserDto> entityToDto(List<User> users) {
        return users.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
    }

    public List<User> dtoToEntity(List<UserDto> userDtos) {
        return userDtos.stream()
                       .map(this::dtoToEntity)
                       .collect(Collectors.toList());
    }
}
