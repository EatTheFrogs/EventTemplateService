package com.eatthefrog.EventTemplateService.model.goal;

import com.eatthefrog.EventTemplateService.model.BaseModel;
import com.eatthefrog.EventTemplateService.model.event.Event;
import com.eatthefrog.EventTemplateService.model.eventtemplate.EventTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "goals")
public class Goal extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8357126405162601677L;

    @Id
    private String id;
    private ZonedDateTime createdDate;
    @NotNull
    private String userUuid;
    private String name;
    private String description;
    private List<Event> completedEvents = new ArrayList<Event>();
    private List<EventTemplate> eventTemplates = new ArrayList<EventTemplate>();
}
