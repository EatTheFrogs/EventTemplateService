package com.eatthefrog.EventTemplateService.model.event;

import com.eatthefrog.EventTemplateService.model.BaseModel;
import lombok.Data;

import java.io.Serial;

@Data
public class DefaultEventField extends BaseModel {

    @Serial
    private static final long serialVersionUID = -6890295343670089002L;

    private String name;
    private String type;
    private String unit;
    private String value;
}