package com.gitcolab.dao;

import com.gitcolab.entity.Project;
import com.gitcolab.utilities.ProjectRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JDBCProjectDAO implements ProjectDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional get(long id) {
        Project project=jdbcTemplate.queryForObject("select * from Project p where p.id=?",new Object[]{id}, new ProjectRowMapper());
        return Optional.of(project);
    }

    @Override
    public int save(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("INSERT INTO Project(`name`,`description`,`userId`,`timestamp`,`gitHubRepoName`,`atlassianProjectId`) " +
                                "values(:name,:description,:userId,:timestamp,:gitHubRepoName,:atlassianProjectId)"
                        ,namedParameters);
    }

    @Override
    public int update(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("UPDATE Project SET `id` = :id,`name` = :name,`description` = :description,`userId` = :userId,`timestamp` = :timestamp, `gitHubRepoName` = :gitHubRepoName, `atlassianProjectId` = :atlassianProjectId WHERE `id` = :id"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {
    }
}
