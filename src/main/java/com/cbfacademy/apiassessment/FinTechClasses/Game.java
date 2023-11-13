package com.cbfacademy.apiassessment.FinTechClasses;

import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.EventClasses.*;
import com.google.gson.annotations.Expose;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private String gameId;
    //public final LocalDateTime creationDateTime;
    private String month = "Jan";
    private int currentTurn = 1;
    private final int maxTurnsPerGame = 20;
    private boolean isGameCompleted = false;
    private boolean isGameOver = false;
    private int currentNumberOfActions = 0;
    private int actionsPerTurn = 3;
    private Company company = new Company();

    //NB - you can exclude this variable from the json using the key word transient

    //change to a calendar object to get the date

    private final String[] arrayOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public List<Event> listOfEvents = new ArrayList<>();



    public Game(){
        this.gameId = UUID.randomUUID().toString();
        //this.creationDateTime = LocalDateTime.now();
        listOfEvents.add(new NoEvent("No Event"));
        listOfEvents.add(new CybersecurityLeak("Cybersecurity Leak"));
        listOfEvents.add(new EconomicBoom("Economic Boom"));
        listOfEvents.add(new EconomicDownturn("Economic Downturn"));
        listOfEvents.add(new SocialMediaViral("Social media viral event"));
    }

    //how to re-structure this so that the randomEvent is shown to the user
    public String triggerRandomEvent(){
        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(listOfEvents.size());
        Event randomEvent = listOfEvents.get(randomIndex);
        randomEvent.executeEvent(company);
        return randomEvent.getEventName();
        //this will return the name of the triggered Event
    }

    public void resetActionsTaken(){
        currentNumberOfActions = 0;
    }

    public void addToCurrentNumberOfActions(){
        currentNumberOfActions ++;
    }

    public List<Event> getEvents() {
        return listOfEvents;
    }

    public String getGameId() {
        return gameId;
    }

//    public LocalDateTime getCreationDateTime(){
//        return creationDateTime;
//    }
    public Company getCompany() {
        return company;
    }

    public void setMonth(){
        int index = (currentTurn - 1) % arrayOfMonths.length;
        this.month = arrayOfMonths[index];
    }


    public String getMonth() {
        return month;
    }


    public int getCurrentTurn() {
        return currentTurn;
    }

    public void advanceTurn(){
        if(currentTurn < 19){
            triggerRandomEvent();
        }
        currentTurn++;

        setMonth();
        resetCurrentNumberOfActions();
        company.resetCrowdFundCount();
        company.resetInvestCount();

        isGameOver();
        checkGameIsCompleted();

    }

    public boolean checkGameIsCompleted(){
        if(company.getRevenue() == 5000000 &&
                company.getDepartments() == 3
                && company.getEmployees() == 30
                && company.getCustomerBase() == 10000
                && company.getProductXP() > 30
        ){
            return isGameCompleted = true;
        }

        return isGameCompleted;
    }

    public int getCurrentNumberOfActions(){
        return currentNumberOfActions;
    }
    public int actionsRemaining(){
        return actionsPerTurn - currentNumberOfActions;
    }
    public void resetCurrentNumberOfActions(){

        currentNumberOfActions = 0;
    }

    public void actionsManager() throws InvalidActionException {

        if(actionsPerTurn >= currentNumberOfActions){
            currentNumberOfActions++;
        } else {
            throw new InvalidActionException("Invalid action - You can only make 3 actions per turn. Advance turn to get access to more actions");
        }
    }

    public boolean isGameCompleted() {

        return isGameCompleted;
    }

    public boolean isGameOver(){
        if(currentTurn == maxTurnsPerGame){
           isGameOver = true;
        }
        return isGameOver;
    }



    //we need a method to limit the actions that the user can take
}
