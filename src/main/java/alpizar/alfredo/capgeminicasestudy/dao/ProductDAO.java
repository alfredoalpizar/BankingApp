package alpizar.alfredo.capgeminicasestudy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import alpizar.alfredo.capgeminicasestudy.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.Map;

@Repository
@Transactional
public class ProductDAO extends JdbcDaoSupport {

    @Autowired
    public ProductDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Account> getAccounts(Long userId) {
        String sql = "SELECT created_at, balance, id_products, id_user\n" +
                "FROM products_user \n" +
                "WHERE id_user=?\n" +
                "ORDER BY balance DESC";
        Object[] params = new Object[] { userId };

        List<Account> accounts =  this.getJdbcTemplate().query(sql, params, new BeanPropertyRowMapper(Account.class));


        return accounts;
    }

    public void openAccount(String userName, Long deposit, Long product){
        String sqlSelect ="SELECT id FROM user_t WHERE username = ?";
        Long uID = this.getJdbcTemplate().queryForObject(sqlSelect, new Object []{userName}, Long.class);


        String sqlInsert = "INSERT INTO products_user(id_user, id_products, balance) VALUES (?,?,?)";


        Object [] inputs = new Object[] {uID, product, deposit };

        this.getJdbcTemplate().update(sqlInsert,inputs);
    }



}
