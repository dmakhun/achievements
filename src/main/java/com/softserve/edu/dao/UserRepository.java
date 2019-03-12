package com.softserve.edu.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.softserve.edu.entity.QUser;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User> {

    List<User> findByGroups_Id(Long groupId);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoleName(String roleName);

    default Predicate createManagerPredicate(String column, String pattern, String role) {
        QUser qUser = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        switch (column) {
            case "name":
                booleanBuilder.or(qUser.name.like(pattern));
                break;
            case "surname":
                booleanBuilder.or(qUser.surname.like(pattern));
                break;
            case "username":
                booleanBuilder.or(qUser.username.like(pattern));
                break;
        }
        booleanBuilder.and(qUser.role.name.eq(role));
        return booleanBuilder.getValue();
    }

}
