package A1BnB.backend.global.redis.service;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisTestContainerConfig implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:7.0.8-alpine"))
                .withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", String.valueOf(redis.getMappedPort(6379)));
    }

}
