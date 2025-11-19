package com.joneskurian.api.store.repositories;

import com.joneskurian.api.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}