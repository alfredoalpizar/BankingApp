package alpizar.alfredo.capgeminicasestudy.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import alpizar.alfredo.capgeminicasestudy.model.UserApp;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserApp> {

    public static final String BASE_SQL //
            = "Select u.id, u.first_name, u.last_name, u.address, u.username, u.password From user_t u ";

    @Override
    public UserApp mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String Address = rs.getString("address");
        String userName = rs.getString("username");
        String encrytedPassword = rs.getString("password");

        return new UserApp(userId, userName, encrytedPassword, firstName, lastName, Address);
    }

}