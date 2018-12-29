package com.softserve.edu.dao;

import static com.softserve.edu.util.Constants.ROLE_MANAGER;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.softserve.edu.entity.QUser;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    List<User> findByGroups_Id(Long groupId);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoleName(String roleName);

    @Query(value = "SELECT * from users where ?2 like ?1 and role_id = ?3", nativeQuery = true)
    List<User> findAllManagers(String pattern, String parameter, Long roleId);

    @Override
    default void customize(QuerydslBindings bindings, QUser user) {

        bindings.bind(user.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.surname).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.username).first(StringExpression::containsIgnoreCase);
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    default Predicate createPredicate(String column, String pattern) {
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
        booleanBuilder.and(qUser.role.name.eq(ROLE_MANAGER));
        return booleanBuilder.getValue();
    }

}
