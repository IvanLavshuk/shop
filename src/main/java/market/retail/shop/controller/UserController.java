package market.retail.shop.controller;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateUserDto;
import market.retail.shop.dto.UserDto;
import market.retail.shop.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return  userService.findAll();
    }

    @GetMapping
    public UserDto findByEmail(String email){
        return userService.findByEmail(email);
    }

    @GetMapping
    public UserDto findById(Integer id){
        return userService.findById(id);
    }

    @PostMapping
    public UserDto create(CreateUserDto createUserDto){
        return userService.create(createUserDto);
    }

    @PostMapping(path = "{id}")
    public  UserDto update(@PathVariable(name = "id") Integer id, CreateUserDto createUserDto){
       return userService.update(id,createUserDto);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable(name = "id") Integer id){
        userService.deleteById(id);
    }


}
