package com.eatthefrog.EventTemplateService.controller;

import com.eatthefrog.EventTemplateService.model.eventtemplate.EventTemplate;
import com.eatthefrog.EventTemplateService.model.goal.Goal;
import com.eatthefrog.EventTemplateService.service.EventTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EventTemplatesController {

    private final EventTemplateService eventTemplateService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class OperationFailedException extends RuntimeException {
        public OperationFailedException(String message) {
            super(message);
        }
    }

    // Internal endpoints

    @PreAuthorize("hasAuthority('SCOPE_api')")
    @DeleteMapping("/delete/goal/{goalId}")
    public ResponseEntity deleteTemplatesForGoal(@PathVariable String goalId) {
        eventTemplateService.deleteTemplatesForGoalId(goalId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_api')")
    @DeleteMapping("/delete/user/{userUuid}")
    public ResponseEntity deleteAllTemplatesForUser(@PathVariable String userUuid) {
        eventTemplateService.deleteAllTemplatesForUser(userUuid);
        return ResponseEntity.ok().build();
    }

    // External endpoints

    @PreAuthorize("#eventTemplate.getUserUuid() == authentication.token.claims['uid']")
    @PostMapping("/create")
    public Collection<Goal> createEventTemplate(@RequestBody EventTemplate eventTemplate) throws Exception {
        return eventTemplateService.createEventTemplate(eventTemplate);
    }

    @PreAuthorize("#eventTemplate.getUserUuid() == authentication.token.claims['uid']")
    @PatchMapping("/update")
    public Collection<Goal> updateEventTemplate(@RequestBody EventTemplate eventTemplate) {
        return eventTemplateService.updateEventTemplate(eventTemplate);
    }

    @PreAuthorize("@eventTemplateService.assertUserOwnsTemplate(#jwt.getClaim('uid').toString(), #templateId)")
    @DeleteMapping("/delete/{templateId}")
    public Collection<Goal> deleteEventTemplate(@AuthenticationPrincipal Jwt jwt, @PathVariable String templateId) throws Exception {
        return eventTemplateService.deleteEventTemplate(templateId, jwt.getClaim("uid").toString());
    }
}
