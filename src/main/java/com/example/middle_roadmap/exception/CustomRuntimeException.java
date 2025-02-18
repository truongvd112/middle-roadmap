package com.example.middle_roadmap.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException {
    @JsonProperty("error_list")
    private List errorList;

    @JsonProperty("error_map")
    private Map<String, Object> errMap;
    public CustomRuntimeException(Throwable e) {
        super(e);
    }

    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Map<String, Object> errMap) {
        super(message);
        this.errMap = Map.copyOf(errMap);
    }
    public CustomRuntimeException(String message, List<?> errorList) {
        super(message);
        this.errorList = List.copyOf(errorList);
    }
}
