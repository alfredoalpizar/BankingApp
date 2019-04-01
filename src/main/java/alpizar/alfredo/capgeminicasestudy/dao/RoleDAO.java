package alpizar.alfredo.capgeminicasestudy.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDAO extends JdbcDaoSupport {

    @Autowired
    public RoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long userId) {
        String sql = "Select r.name_role" //
                + " from roles_user ur, roles_app r " //
                + " where ur.id_role = r.id_role and ur.id_user = ? ";

        Object[] params = new Object[] { userId };

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

        return roles;
    }

    public void registerUserRoles(String userName){

        String sqlSelect ="SELECT id FROM user_t WHERE username = ?";
        Long uID = this.getJdbcTemplate().queryForObject(sqlSelect, new Object []{userName}, Long.class);
        System.out.println(uID);

        String sqlInsert = "INSERT INTO roles_user(id_user, id_role) VALUES (?,?)";

        Object [] inputs = new Object[] {uID, 1};
        this.getJdbcTemplate().update(sqlInsert,inputs);


    }

}