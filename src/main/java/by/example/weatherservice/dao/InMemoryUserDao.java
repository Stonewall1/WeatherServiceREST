package by.example.weatherservice.dao;

import by.example.weatherservice.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class InMemoryUserDao implements UserDao<User, Long> {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Optional<User> save(User user) {
        user.setId(idGenerator.incrementAndGet());
        user.setToken(UUID.randomUUID().toString());
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public Optional<User> findByToken(String token) {
        return users.stream().filter(user -> user.getToken().equals(token)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
