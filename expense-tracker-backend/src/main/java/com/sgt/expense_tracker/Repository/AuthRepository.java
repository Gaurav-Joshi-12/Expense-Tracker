package com.sgt.expense_tracker.Repository;

import com.sgt.expense_tracker.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AuthRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String name, String username, String email, String password, String mobileNo){
        String query="INSERT INTO users(name,user_name,email,password,mobile_no) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, name, username, email, password, mobileNo);
    }

    public User findByEmail(String email){
        String query = "Select user_id,name,user_name,mobile_no,email,password,active_yn FROM users where email = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query,(resultSet ,  rowNum) ->{
                User user1 = new User();
                user1.setUserId(resultSet.getInt("user_id"));
                user1.setEmail(resultSet.getString("email"));
                user1.setUsername(resultSet.getString("user_name"));
                user1.setName(resultSet.getString("name"));
                user1.setMobileNo(resultSet.getString("mobile_no"));
                user1.setActiveYn(resultSet.getInt("active_yn"));
                user1.setPassword(resultSet.getString("password"));
                return user1;
            },email);
        }  catch ( EmptyResultDataAccessException e) {
            return null;
        }

        return user;
    }

    public User findByUsername(String username){
        String query = "Select user_id,name,user_name,mobile_no,email,password,active_yn FROM users where user_name = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query,(resultSet ,  rowNum) ->{
                User user1 = new User();
                user1.setUserId(resultSet.getInt("user_id"));
                user1.setEmail(resultSet.getString("email"));
                user1.setUsername(resultSet.getString("user_name"));
                user1.setName(resultSet.getString("name"));
                user1.setMobileNo(resultSet.getString("mobile_no"));
                user1.setActiveYn(resultSet.getInt("active_yn"));
                user1.setPassword(resultSet.getString("password"));
                return user1;
            },username);
        } catch ( EmptyResultDataAccessException e) {
            return null;
        }

        return user;
    }

    public User userLogin(String username,String password){
        String query = "Select user_id,name,user_name,mobile_no,email,password,active_yn FROM users where user_name = ? and password = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(query,(resultSet ,  rowNum) ->{
                User user1 = new User();
                user1.setUserId(resultSet.getInt("user_id"));
                user1.setEmail(resultSet.getString("email"));
                user1.setUsername(resultSet.getString("user_name"));
                user1.setName(resultSet.getString("name"));
                user1.setMobileNo(resultSet.getString("mobile_no"));
                user1.setActiveYn(resultSet.getInt("active_yn"));
                user1.setPassword(resultSet.getString("password"));
                return user1;
            },username,password);
        } catch ( EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    public void saveResetToken( String token , LocalDateTime tokenTime, int userId){
        String query = "INSERT INTO auth_tokens(token,user_id,expiry) VALUES (?, ?, ?)";
        jdbcTemplate.update(query,token,userId,tokenTime);
    }

    public Integer validateToken(String token){
        String sql = "select user_id from auth_tokens where token = ? and expiry > CURRENT_TIMESTAMP and used_yn= 0 ";
        try{
            return jdbcTemplate.queryForObject(sql,Integer.class,token);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public void updatePassword(int userId, String password){
        String sql = "UPDATE users set password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql,password,userId);
    }

    public void markTokenUsed(String token){
        String sql = "UPDATE auth_tokens set used_yn = 1  WHERE token = ?";
        jdbcTemplate.update(sql,token);
    }

    public void markTokenUsedForUser(int userId){
        String sql = "UPDATE auth_tokens set used_yn = 1  WHERE user_id = ?";
        jdbcTemplate.update(sql,userId);
    }

    public List<String> getAllUserEmails(){
        String sql = "SELECT email FROM users WHERE active_yn = 1";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("Select user_id,name,user_name,mobile_no,email,password, active_yn from users where active_yn = 1;",(resultSet,rowNum) -> {
            User user1 = new User();
            user1.setUserId(resultSet.getInt("user_id"));
            user1.setEmail(resultSet.getString("email"));
            user1.setUsername(resultSet.getString("user_name"));
            user1.setName(resultSet.getString("name"));
            user1.setMobileNo(resultSet.getString("mobile_no"));
            user1.setActiveYn(resultSet.getInt("active_yn"));
            user1.setPassword(resultSet.getString("password"));
            return user1;
        });
    }
}
