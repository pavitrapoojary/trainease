package com.trainease.repository;

import com.trainease.entity.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends MongoRepository<Batch,String> {
}
