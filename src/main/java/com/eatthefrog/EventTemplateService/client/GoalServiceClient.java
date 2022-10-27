package com.eatthefrog.EventTemplateService.client;

import com.eatthefrog.EventTemplateService.controller.EventTemplatesController;
import com.eatthefrog.EventTemplateService.model.eventtemplate.EventTemplate;
import com.eatthefrog.EventTemplateService.model.goal.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoalServiceClient {

    private static final String GET_PATH = "/{goalId}";
    private static final String CREATE_PATH = "/create/template";
    private static final String DELETE_PATH = "/{goalId}/delete/template/{templateId}";

    private final WebClient goalServiceWebClient;

    public Collection<Goal> getAllGoals(String userUuid) {
        System.out.println("Fetching all goals");
        return goalServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(GET_PATH)
                        .build(userUuid))
                .retrieve()
                .bodyToFlux(Goal.class)
                .collect(Collectors.toList())
                .block();
    }

    public void addEventTemplateToGoal(EventTemplate eventTemplate) {
        goalServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(CREATE_PATH)
                        .build())
                .body(Mono.just(eventTemplate), EventTemplate.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(String.class).map(EventTemplatesController.OperationFailedException::new))
                .bodyToMono(Object.class)
                .block();
    }

    public void deleteEventTemplateFromGoal(String goalId, String templateId) {
        goalServiceWebClient.delete()
                .uri(uriBuilder -> uriBuilder.path(DELETE_PATH)
                        .build(goalId, templateId))
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(String.class).map(EventTemplatesController.OperationFailedException::new))
                .bodyToMono(Object.class)
                .block();
    }
}
