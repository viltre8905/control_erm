package com.verynet.gcint.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by day on 11/04/2017.
 */
public class CustomUserDetailsService extends JdbcDaoImpl {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    @Value("select * from auth_user where user_name = ?")
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
    }

    @Override
    @Value("select user_name as username, value as authorities from auth_user, auth_role, user_role where auth_user.id = user_role.user_id AND user_role.role_id = auth_role.id AND user_name=?")
    public void setAuthoritiesByUsernameQuery(String queryString) {
        super.setAuthoritiesByUsernameQuery(queryString);
    }


    @Override
    public List<UserDetails> loadUsersByUsername(String username) {
        return this.getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[]{username}, new RowMapper() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("user_name");
                String password = rs.getString("password");
                boolean enabled = rs.getBoolean("enabled");
                boolean accountNonLocked = rs.getBoolean("accountNonLocked");
                return new User(username, password, enabled, true, true, accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }

    @Override
    public UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        if (!super.isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }
        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true, userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
    }
}
