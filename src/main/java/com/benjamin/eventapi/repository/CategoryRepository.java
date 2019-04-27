package com.benjamin.eventapi.repository;

import com.benjamin.eventapi.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "category", path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
