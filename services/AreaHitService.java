package ru.itmo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.model.Dot;
import ru.itmo.model.User;
import ru.itmo.resources.AreaHitRepository;
import ru.itmo.dto.PointRequest;
import ru.itmo.dto.PointResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AreaHitService {

    private final AreaHitRepository repo;
    private final AreaHitChecker checker;

    public PointResponse savePoint(PointRequest request, User user){
        PointResponse response = checker.getResponse(request);
        Dot dot = new Dot(null, response.getX(), response.getY(), response.getR(), response.getResult(), user);
        repo.save(dot);
        return response;
    }

    public List<PointResponse> getPoints(User user){
        return repo.findAllByUser(user).stream()
                .map(u-> new PointResponse(u.getX(), u.getY(), u.getR(), u.getRes()))
                .collect(Collectors.toList());
    }
    @Transactional
    public List<PointResponse> clearPoints(User user){
        repo.deleteAllByUser(user);
        return new ArrayList<>();
    }

}
