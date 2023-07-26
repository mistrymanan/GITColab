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
                .update("INSERT INTO Project(`userId`,`repositoryName`,`repositoryOwner`,`atlassianProjectId`,`jiraBoardName`,`timestamp`) " +
                                "values(:userId,:repositoryName,:repositoryOwner,:atlassianProjectId,:jiraBoardName,:timestamp)"
                        ,namedParameters);
    }

    @Override
    public int update(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("UPDATE Project SET `userId`=:userId,`repositoryName`=:repositoryName,`repositoryOwner`=:repositoryOwner,`atlassianProjectId`=:atlassianProjectId, `jiraBoardName`=:jiraBoardName, `timestamp`=:timestamp WHERE `id` = :id"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName, String repositoryOwner) {
        Project project=jdbcTemplate.queryForObject("select * from Project p where p.repositoryName=? and p.repositoryOwner=?",new Object[]{repositoryName,repositoryOwner}, new ProjectRowMapper());
        return Optional.of(project);
    }
}
