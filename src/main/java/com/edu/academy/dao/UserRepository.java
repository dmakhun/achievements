package com.edu.academy.dao;

import com.edu.academy.entity.QUser;
import com.edu.academy.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    List<User> findByGroups_Id(Long groupId);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoleName(String roleName);

    /**
     * Find all regular users
     *
     * @return list of all non-admins, non-managers
     */
    @Query("from User where role.name=com.softserve.edu.util.Constants.ROLE_USER")
    List<User> findAllUsers();

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
