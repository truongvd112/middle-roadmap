package com.example.middle_roadmap.utils.designPattern.decorator;

public class Pizza implements Food {
    @Override
    public String description() {
        return "Pizza";
    }

    @Override
    public Long cost() {
        return 100000L;
    }

    public String extendFood(){
        return "Extra cheese";
    }
}
