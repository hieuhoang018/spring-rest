package com.hieuhoang.springrest.user;

import com.hieuhoang.springrest.common.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserRepository repo;


    public UserController(UserRepository repo) { this.repo = repo; }


    @GetMapping
    public List<AppUser> list() { return repo.findAll(); }


    @GetMapping("/{id}")
    public AppUser get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("User " + id + " not found"));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppUser create(@Valid @RequestBody AppUser body) {
// For admin use only (controller is ADMIN-protected). Assume body.password is already encoded? Typically not.
// In real apps, prefer a dedicated Admin DTO to create users. Here we block direct creation without password.
        throw new UnsupportedOperationException("Create users via /api/auth/register");
    }


    @PutMapping("/{id}")
    public AppUser update(@PathVariable Long id, @Valid @RequestBody AppUser body) {
        AppUser u = repo.findById(id).orElseThrow(() -> new NotFoundException("User " + id + " not found"));
        u.setName(body.getName());
        u.setEmail(body.getEmail());
// password/roles management omitted for brevity
        return repo.save(u);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("User " + id + " not found");
        repo.deleteById(id);
    }
}
