package com.eatthefrog.EventTemplateService.repository;

import com.eatthefrog.EventTemplateService.model.eventtemplate.EventTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface EventTemplateRepo extends MongoRepository<EventTemplate, String> {

    public Collection<EventTemplate> findAllByUserUuid(String userUuid);

    public void deleteByGoalId(String goalId);

    public void deleteByUserUuid(String userUuid);
}
