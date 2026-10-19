package com.example.studentapp.auth.dao;

import com.example.studentapp.auth.entity.RefreshToken;
import com.example.studentapp.auth.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class RefreshTokenDao {

    @PersistenceContext(unitName = "studentPU")
    private EntityManager em;

    @Transactional
    public RefreshToken save(RefreshToken token) {
        em.persist(token);
        return token;
    }

    public Optional<RefreshToken> findByHash(String hash) {
        return em.createQuery(
                "select t from RefreshToken t join fetch t.user where t.tokenHash = :h",
                RefreshToken.class)
                .setParameter("h", hash)
                .getResultStream().findFirst();
    }

    @Transactional
    public void markRevoked(RefreshToken token) {
        token.setRevoked(true);
        em.merge(token);
    }

    @Transactional
    public int deleteExpired(Instant now) {
        return em.createQuery("delete from RefreshToken t where t.expiresAt < :now")
                .setParameter("now", now)
                .executeUpdate();
    }
}