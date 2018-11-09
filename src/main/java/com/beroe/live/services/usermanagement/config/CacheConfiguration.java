package com.beroe.live.services.usermanagement.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
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
            cm.createCache("oAuth2Authentication", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.UserProfile.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.UserProfile.class.getName() + ".userStates", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.UserState.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.SystemConfiguration.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.SystemConfiguration.class.getName() + ".configurationApplicabilities", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.ConfigurationApplicability.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.Company.class.getName() + ".companyDomains", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.CompanyDomain.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.Invitation.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.SupplierInvitationDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.Company.class.getName() + ".userProfiles", jcacheConfiguration);
            cm.createCache(com.beroe.live.services.usermanagement.domain.RestrictedDomains.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
