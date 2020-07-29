package com.songpapeople.hashtagmap.scheduler.config;

import com.songpapeople.hashtagmap.scheduler.domain.KakaoScheduler;
import com.songpapeople.hashtagmap.scheduler.domain.KakaoSchedulerTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;

@EnableScheduling
@Configuration
public class KakaoSchedulerConfig {
    private static final int PERIOD = 30;

    private final KakaoSchedulerTask kakaoSchedulerTask;

    public KakaoSchedulerConfig(KakaoSchedulerTask kakaoSchedulerTask) {
        this.kakaoSchedulerTask = kakaoSchedulerTask;
    }

    @Bean
    public KakaoScheduler kakaoPlaceScheduler() {
        PeriodicTrigger trigger = new PeriodicTrigger(PERIOD, TimeUnit.DAYS);
        return new KakaoScheduler(kakaoSchedulerTask::collectData, trigger);
    }
}