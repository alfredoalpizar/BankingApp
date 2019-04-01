package alpizar.alfredo.capgeminicasestudy.dao;

import javax.sql.DataSource;

import alpizar.alfredo.capgeminicasestudy.mapper.UserMapper;
import alpizar.alfredo.capgeminicasestudy.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import alpizar.alfredo.capgeminicasestudy.utils.PasswordUtils;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport {

    @Autowired
    public UserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public UserApp findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = UserMapper.BASE_SQL + " where u.username = ? ";

        Object[] params = new Object[] { userName };
        UserMapper mapper = new UserMapper();
        try {
            UserApp userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void registerInsert(UserApp usr){

        String encryptedPW = PasswordUtils.encryptedPassword(usr.getEncryptedPassword());

        String sql = "INSERT INTO user_t(first_name, last_name, address, username, password) VALUES (?,?,?,?,?)";

        Object [] inputs = new Object[] {usr.getFirstName(), usr.getLastName(), usr.getAddress(), usr.getUserName(),encryptedPW};
        System.out.println(usr.toString());
        System.out.println(sql);
        this.getJdbcTemplate().update(sql,inputs);

    }

}