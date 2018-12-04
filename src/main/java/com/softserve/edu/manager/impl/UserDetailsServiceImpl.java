package com.softserve.edu.manager.impl;

import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.manager.UserManager;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    /**
     * check user data
     *
     * @return org.springframework.security.core.userdetails.User Object
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        com.softserve.edu.entity.User user = userManager.findByUsername(username);
        Set<GrantedAuthority> authorities = new HashSet<>();
        AccessRole accessRole = user.getAccessRole();
        authorities.add(new SimpleGrantedAuthority(accessRole.getName()));
        return new User(user.getUsername(), user.getPassword(), true, true,
                true, true, authorities);
    }

}
