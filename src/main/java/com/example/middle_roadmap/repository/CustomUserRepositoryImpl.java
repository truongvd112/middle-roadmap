package com.example.middle_roadmap.repository;

import com.example.middle_roadmap.dto.user.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<UserDto> findAllByRoleName(String roleName) {
        return entityManager.createNativeQuery("""
            SELECT u.*, 
               d.id as device_id, d.type as device_type, d.name as device_name, d.quantity as device_quantity, d.price as device_price
               , r.id as role_id, r.name as role_name
            FROM users u
            JOIN device d ON u.id = d.user_id
            JOIN roles r ON u.role_id = r.id
            """)
//                .setParameter("roleName", roleName)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new UserDtoResultTransformer())
                .getResultList();
    }
}
