package com.cw.crm.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.cw.crm.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.cw.crm.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Participant.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Action.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.SupportCoordinator.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Waiver.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.EmployeeType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.EmployeeSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ContactStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ContactSubStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Priority.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.MCO.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.Physician.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.EnrollmentAgency.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ContactHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ParticipantNotes.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ContactType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ExtendedUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ReferralType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ReferralSource.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ActionType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ActionSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ActionStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.ContactSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cw.crm.domain.AltContact.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
