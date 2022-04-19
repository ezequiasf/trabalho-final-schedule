package com.dbccompany.kafkareceita.repository;

import com.dbccompany.kafkareceita.entity.RecipeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends MongoRepository<RecipeEntity, String> {
}
