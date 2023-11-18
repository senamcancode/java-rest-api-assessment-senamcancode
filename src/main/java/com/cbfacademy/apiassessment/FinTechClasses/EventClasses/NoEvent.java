package com.cbfacademy.apiassessment.FinTechClasses.EventClasses;

import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Event;

public class NoEvent extends Event {
    public NoEvent(String eventName) {
        super(eventName);
    }

    @Override
    public String  executeEvent(Company company){
        //this event does nothingreturn false;
        return "NO EVENT OCCURRED (phew!)";
    }
}
