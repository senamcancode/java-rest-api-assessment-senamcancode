package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class SocialMediaViral extends Event {
    public SocialMediaViral(String eventName) {
        super(eventName);
    }

    @Override
    public void executeEvent(Company company){
        company.increaseCustomerBase(1500);
        company.increaseRevenue(2);

    }
}