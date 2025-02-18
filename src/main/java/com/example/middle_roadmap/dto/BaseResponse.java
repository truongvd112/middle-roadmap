package com.example.middle_roadmap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BaseResponse {
    @JsonProperty(value = "is_error")
    private boolean isError;
    @JsonProperty("message")
    String message;
    @JsonProperty("error")
    private Map<String, Object> errMap;

    public static BaseResponse simpleSuccess(String message) {
        return BaseResponse.builder().isError(false).message(message).build();
    }
}
