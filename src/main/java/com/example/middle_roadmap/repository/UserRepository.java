package com.example.middle_roadmap.repository;
import com.example.middle_roadmap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findById(Long id);
    List<User> findAll();;
}
