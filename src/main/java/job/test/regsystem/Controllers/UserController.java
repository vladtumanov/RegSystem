package job.test.regsystem.Controllers;

import job.test.regsystem.Entity.User;
import job.test.regsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        Optional<User> userTemp = userService.findById(id);
        if (!userTemp.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User userPatched = userTemp.get();
        userPatched.setRoles(user.getRoles());
        userService.save(userPatched);
        return ResponseEntity.ok().build();
    }
}