package market.retail.shop.service;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateUserDto;
import market.retail.shop.dto.UserDto;
import market.retail.shop.entity.User;
import market.retail.shop.mapper.UserMapper;
import market.retail.shop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().
                stream().
                map(userMapper::toDto).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        return userRepository.findById(id).
                map(userMapper::toDto).
                orElseThrow(() -> new IllegalStateException("User " + id + " is not found"));
    }

    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        return userRepository.
                findByEmail(email)
                .map(userMapper::toDto).
                orElseThrow(() -> new IllegalStateException("User with email " + email + " not found"));

    }

    @Transactional
    public UserDto update(Integer id, CreateUserDto createUserDto) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("User " + id + " is not found"));
        String name = createUserDto.getName();
        user.setName(name);
        String surname = createUserDto.getSurname();
        user.setSurname(surname);
        String email = createUserDto.getEmail();
        user.setEmail(email);
        char[] password = createUserDto.getPassword();
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public User returnEntityById(Integer id) {
        return userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("User " + id + " is not found"));
    }

    @Transactional
    public UserDto create(CreateUserDto createUserDto) {
        Optional<User> optionalUserDto = userRepository.findByEmail(createUserDto.getEmail());
        if (optionalUserDto.isPresent()) {
            throw new IllegalStateException("User with this email already exists");
        }
        User user = userMapper.toEntity(createUserDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<User> optionalUserDto = userRepository.findById(id);
        if (optionalUserDto.isEmpty()) {
            throw new IllegalStateException("User does not exist");
        }
        userRepository.deleteById(id);
    }
}
