package com.gitcolab.dao;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.utilities.RefreshTokenRowMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JDBCRefreshTokenDAOTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JDBCRefreshTokenDAO refreshTokenDAO;

    public JDBCRefreshTokenDAOTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        long id = 1L;
        RefreshToken refreshToken = new RefreshToken();
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RefreshTokenRowMapper.class)))
                .thenReturn(refreshToken);

        Optional<RefreshToken> result = refreshTokenDAO.get(id);

        assertEquals(Optional.of(refreshToken), result);
        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(RefreshTokenRowMapper.class));
    }

    @Test
    void testSave() {
        RefreshToken refreshToken = new RefreshToken();
        when(namedParameterJdbcTemplate.update(anyString(), anyMap())).thenReturn(1);

        int result = refreshTokenDAO.save(refreshToken);

        assertEquals(1, result);
        verify(namedParameterJdbcTemplate).update(anyString(), anyMap());
    }

    @Test
    void testDelete() {
        long id = 1L;
        doNothing().when(jdbcTemplate).update(anyString(), any(Object[].class));

        refreshTokenDAO.delete(id);

        verify(jdbcTemplate).update(anyString(), any(Object[].class));
    }

    @Test
    void testFindByToken() {
        String token = "token";
        RefreshToken refreshToken = new RefreshToken();
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RefreshTokenRowMapper.class)))
                .thenReturn(refreshToken);

        Optional<RefreshToken> result = refreshTokenDAO.findByToken(token);

        assertEquals(Optional.of(refreshToken), result);
        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(RefreshTokenRowMapper.class));
    }

    @Test
    void testDeleteByUser() {
        User user = new User();
        doNothing().when(jdbcTemplate).update(anyString(), any(Object[].class));

        refreshTokenDAO.deleteByUser(user);

        verify(jdbcTemplate).update(anyString(), any(Object[].class));
    }

    @Test
    void testDelete_RefreshTokenObject() {
        RefreshToken refreshToken = new RefreshToken();
        doNothing().when(refreshTokenDAO).delete(anyLong());

        refreshTokenDAO.delete(refreshToken);

        verify(refreshTokenDAO).delete(anyLong());
    }
}
