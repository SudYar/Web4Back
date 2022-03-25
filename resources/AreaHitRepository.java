package ru.itmo.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.model.Dot;
import ru.itmo.model.User;

import java.util.List;

public interface AreaHitRepository extends JpaRepository<Dot, Long> {
    List<Dot> findAllByUser(User user);

    //void deleteByUser(User user);

    void deleteAllByUser(User user);
}
