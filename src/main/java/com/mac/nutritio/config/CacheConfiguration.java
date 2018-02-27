package com.mac.nutritio.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Person.class.getName() + ".meals", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Person.class.getName() + ".goals", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Goal.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Grocerie.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Grocerie.class.getName() + ".ingredientEntries", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.BlackList.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.BlackList.class.getName() + ".ingredientEntries", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Ingredient.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Stock.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Stock.class.getName() + ".ingredientEntries", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Recipe.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Recipe.class.getName() + ".ingredientEntries", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Recipe.class.getName() + ".meals", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Meal.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.Meal.class.getName() + ".recipes", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.IngredientEntry.class.getName(), jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.IngredientEntry.class.getName() + ".stocks", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.IngredientEntry.class.getName() + ".recipes", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.IngredientEntry.class.getName() + ".groceries", jcacheConfiguration);
            cm.createCache(com.mac.nutritio.domain.IngredientEntry.class.getName() + ".blackLists", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
