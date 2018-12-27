package com.softserve.edu.dao;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.softserve.edu.entity.QUser;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>,
        QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    List<User> findByGroups_Id(Long groupId);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoleName(String roleName);

/*    @Query("SELECT u from User u where ")
    List<User> findAllManagers();*/

    @Override
    default void customize(QuerydslBindings bindings, QUser user) {

        bindings.bind(user.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.surname).first(StringExpression::containsIgnoreCase);
        bindings.bind(user.username).first(StringExpression::containsIgnoreCase);
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
