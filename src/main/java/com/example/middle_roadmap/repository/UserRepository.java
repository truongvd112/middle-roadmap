package com.example.middle_roadmap.repository;
import com.example.middle_roadmap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    List<User> findAll();
    User findByUsername(String username);
    @Modifying
    @Query(value = "UPDATE users " +
                   "SET name = :name," +
                        "phone_number = :phoneNumber, " +
                        "email = :email, " +
                        "role_id = :roleId " +
                   "WHERE id = :id", nativeQuery = true)
    void updateUserByNativeQuery(Long id, String name, String phoneNumber, String email, Long roleId);
}
