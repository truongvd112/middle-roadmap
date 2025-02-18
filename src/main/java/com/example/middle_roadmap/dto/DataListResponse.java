package com.example.middle_roadmap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DataListResponse extends BaseResponse {
    @JsonProperty("data")
    List dataList;
    @JsonProperty("total")
    private long total;
    @JsonProperty("page_size")
    Integer pageSize;
    @JsonProperty("page_num")
    Integer pageNum;
    @JsonProperty("map")
    Map<String, Object> map;
    @JsonProperty("total_number")
    Long totalNumber;
}
