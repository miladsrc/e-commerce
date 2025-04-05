package com.example.bussinessshope.user.controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

//    private final AdminService adminService;
//
//    @Autowired
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }

    //TODO: this method need to retrieve all users with contacts
//    @GetMapping("/users")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<UserDto> userDtos = adminService.findAllUsers();
//        return ResponseEntity.ok(userDtos);
//    }

//    @PostMapping("/user")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
//        UserDto savedUserDto = adminService.saveUser(userDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
//    }
//
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        adminService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}

