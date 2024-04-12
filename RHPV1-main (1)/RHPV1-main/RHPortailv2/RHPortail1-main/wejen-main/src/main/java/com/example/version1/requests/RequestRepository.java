package com.example.version1.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    // Custom query method to find requests by userId
    List<Request> findByUserId(Long userId);

}
