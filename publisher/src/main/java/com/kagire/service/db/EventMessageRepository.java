package com.kagire.service.db;

import com.kagire.model.EventMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMessageRepository extends CrudRepository<EventMessage, Long> {}
