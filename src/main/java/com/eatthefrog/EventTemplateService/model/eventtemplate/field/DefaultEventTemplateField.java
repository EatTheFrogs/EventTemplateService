package com.eatthefrog.EventTemplateService.model.eventtemplate.field;

import com.eatthefrog.EventTemplateService.model.event.field.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("eventtemplate.field.default")
public class DefaultEventTemplateField extends EventTemplateField {

    @Serial
    private static final long serialVersionUID = 4950041889292890771L;

    private String type;
    private String unit;
    private Duration duration;
}