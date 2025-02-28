package com.example.middle_roadmap.utils.designPattern.adapter;

public class Main {
    public static void main(String[] args) {
        JsonService jsonService = new JsonService();
        JsonToXMLAdapter jsonToXMLAdapter = new JsonToXMLAdapter(jsonService);
        System.out.println(jsonToXMLAdapter.getXmlData());
    }
}
