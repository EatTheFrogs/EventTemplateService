package com.eatthefrog.EventTemplateService.service;

import com.eatthefrog.EventTemplateService.client.GoalServiceClient;
import com.eatthefrog.EventTemplateService.controller.EventTemplatesController;
import com.eatthefrog.EventTemplateService.model.eventtemplate.EventTemplate;
import com.eatthefrog.EventTemplateService.model.goal.Goal;
import com.eatthefrog.EventTemplateService.repository.EventTemplateRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Log
@Service
@RequiredArgsConstructor
public class EventTemplateService {

    private final EventTemplateRepo eventTemplateRepo;
    private final GoalServiceClient goalServiceClient;
    private final TransactionHandlerService transactionHandlerService;

    // Used in @Preauthorize annotation on controller
    public boolean assertUserOwnsTemplate(String userUuid, String templateId) {
        EventTemplate template = getTemplateById(templateId);
        return StringUtils.equals(userUuid, template.getUserUuid());
    }

    public EventTemplate getTemplateById(String templateId) {
        return eventTemplateRepo.findById(templateId).orElseThrow(() -> new EventTemplatesController.ResourceNotFoundException("Couldn't find template with id "+templateId));
    }

    public Collection<EventTemplate> getEventTemplatesForUser(String userUuid) {
        return eventTemplateRepo.findAllByUserUuid(userUuid);
    }

    public Collection<Goal> createEventTemplate(EventTemplate eventTemplate) throws Exception {
        transactionHandlerService.runInTransaction(() -> createEventTemplateTransactional(eventTemplate));
        return goalServiceClient.getAllGoals(eventTemplate.getUserUuid());
    }

    public Collection<Goal> updateEventTemplate(EventTemplate eventTemplate) {
        eventTemplateRepo.save(eventTemplate);
        return goalServiceClient.getAllGoals(eventTemplate.getUserUuid());
    }

    public Collection<Goal> deleteEventTemplate(String templateId, String userUuid) throws Exception {
        transactionHandlerService.runInTransaction(() -> deleteEventTemplateTransactional(templateId));
        return goalServiceClient.getAllGoals(userUuid);
    }

    public void deleteTemplatesForGoalId(String goalId) {
        eventTemplateRepo.deleteByGoalId(goalId);
    }

    public void deleteAllTemplatesForUser(String userUuid) {
        eventTemplateRepo.deleteByUserUuid(userUuid);
    }

    private void createEventTemplateTransactional(EventTemplate eventTemplate) {
        EventTemplate savedEventTemplate = eventTemplateRepo.save(eventTemplate);
        goalServiceClient.addEventTemplateToGoal(savedEventTemplate);
    }

    public void deleteEventTemplateTransactional(String templateId) {
        EventTemplate template = getTemplateById(templateId);
        goalServiceClient.deleteEventTemplateFromGoal(template.getGoalId(), templateId);
        eventTemplateRepo.deleteById(templateId);
    }
}
