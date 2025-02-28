package com.example.middle_roadmap.utils.designPattern.adapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonToXMLAdapter implements XMLService {

    private final JsonService jsonService;

    @Override
    public String getXmlData() {
        String jsonData = jsonService.getJsonData();
        return "<data>" + jsonData.replace("{\"data\": \"", "").replace("\"}", "") + "</data>";
    }
}
