package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}