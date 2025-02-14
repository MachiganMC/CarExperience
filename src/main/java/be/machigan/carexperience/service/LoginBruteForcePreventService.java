package be.machigan.carexperience.service;

import be.machigan.carexperience.exception.TooManyAttemptsException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginBruteForcePreventService {
    @Value("${application.login.max-attempts}")
    private int maxAttempts;
    @Value("${application.login.wait-after-attempts}")
    private long waitAfterAttempts;
    private com.google.common.cache.LoadingCache<String, Integer> attemptsPerIp;

    @PostConstruct
    private void initCache() {
        this.attemptsPerIp = CacheBuilder
                .newBuilder()
                .expireAfterWrite(this.waitAfterAttempts, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String s) {
                        return 1;
                    }
                });
    }

    @SneakyThrows
    public void addAttemptFor(String ipAddress) {
        int attempts = this.attemptsPerIp.get(ipAddress);
        attempts++;
        this.attemptsPerIp.put(ipAddress, attempts);
        if (attempts >= this.maxAttempts)
            throw new TooManyAttemptsException();
    }

    public void clearAttemptsFor(String ipAddress) {
        this.attemptsPerIp.invalidate(ipAddress);
    }
}
