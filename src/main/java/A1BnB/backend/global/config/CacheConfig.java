package A1BnB.backend.global.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
public class CacheConfig {

    @Bean("pageableKeyGenerator")
    public KeyGenerator pageableKeyGenerator() {
        return (target, method, params) -> {
            Pageable pageable = (Pageable) params[0];
            return pageable.getPageNumber() + "_" + pageable.getPageSize() + "_" + pageable.getSort().toString();
        };
    }
}