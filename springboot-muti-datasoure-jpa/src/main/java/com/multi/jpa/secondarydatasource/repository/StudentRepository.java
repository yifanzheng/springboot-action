package com.multi.jpa.secondarydatasource.repository;

import com.multi.jpa.secondarydatasource.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import sun.swing.StringUIClientPropertyKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * StudentRepository
 *
 * @author kevin
 **/
@Repository
public class StudentRepository {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Student> getStudents() {

        String sql = "SELECT * FROM student";

        List<Student> studentList = jdbcTemplate.query(sql, new StudentRowMapper());
        return studentList;
    }

    public int updateStudent(Student student) {
        String sql = "UPDATE student SET chinese=?,english=?,math=? WHERE id=?";
        int count = jdbcTemplate.update(sql, student.getChinese(), student.getEnglish(), student.getMath(), student.getId());
        return count;
    }

    public int deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id=?";
        int count = jdbcTemplate.update(sql, id);
        return count;
    }

    class StudentRowMapper implements RowMapper<Student> {

        @Nullable
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setChinese(resultSet.getDouble("chinese"));
            student.setEnglish(resultSet.getDouble("english"));
            student.setMath(resultSet.getDouble("math"));
            student.setGender(resultSet.getString("gender"));
            return student;
        }
    }
}
