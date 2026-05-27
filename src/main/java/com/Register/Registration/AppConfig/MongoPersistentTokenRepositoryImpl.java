package com.Register.Registration.AppConfig;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.Register.Registration.Repository.MongoTokenRepository;
import com.Register.Registration.User.MongoPersistentRememberMeToken;

import java.util.Date;
import java.util.List;

@Component
public class MongoPersistentTokenRepositoryImpl implements PersistentTokenRepository {

    private final MongoTokenRepository mongoTokenRepository;

    public MongoPersistentTokenRepositoryImpl(MongoTokenRepository mongoTokenRepository) {
        this.mongoTokenRepository = mongoTokenRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        MongoPersistentRememberMeToken mongoToken = new MongoPersistentRememberMeToken(
                token.getUsername(),
                token.getSeries(),
                token.getTokenValue(),
                token.getDate()
        );
        mongoTokenRepository.save(mongoToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        mongoTokenRepository.findById(series).ifPresent(token -> {
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            mongoTokenRepository.save(token);
        });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return mongoTokenRepository.findById(seriesId)
                .map(token -> new PersistentRememberMeToken(
                        token.getUsername(),
                        token.getSeries(),
                        token.getTokenValue(),
                        token.getDate()
                ))
                .orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        List<MongoPersistentRememberMeToken> tokens = mongoTokenRepository.findByUsername(username);
        if (!tokens.isEmpty()) {
            mongoTokenRepository.deleteAll(tokens);
        }
    }
}