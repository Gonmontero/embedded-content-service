package com.embed.dao;

import com.embed.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDAO extends JpaRepository<Provider, Long> {

    Provider findByName(String name);

}
