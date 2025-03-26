package market.retail.shop.mapper;

import market.retail.shop.dto.CreateUserDto;
import market.retail.shop.dto.UserDto;
import market.retail.shop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User toEntity(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setSurname(createUserDto.getSurname());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        return user;
    }
}
