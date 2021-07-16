package br.com.meli.bootcamp.wave2.quality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TimeConfig {
    @Profile("!test")
    @Bean
    public Clock createClock(){
        return Clock.systemDefaultZone();
    }

    @Profile("test")
    @Primary
    @Bean
    public Clock createFixedClock(){
        return Clock.fixed(Instant.parse("2011-12-03T10:15:30Z"), ZoneId.systemDefault());
    }
}
