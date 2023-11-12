package com.cbfacademy.apiassessment.Controller;


import com.cbfacademy.apiassessment.ExceptionClasses.InsufficientFundsException;
import com.cbfacademy.apiassessment.ExceptionClasses.InvalidActionException;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/game")
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping("/start")
    public ResponseEntity<Object> startNewGame() {
        gameService.newGame();

        return ResponseEntity.ok("New game started and data written to file");
    }


    //@PutMapping("/company-name)
    //public ResponseEntity<String> addCompanyName(){
    //  gameService.addName();
    // }

    @PostMapping("/add-employee")
    //need to chang the next 2 methods so that the numberofemployees information is not coming directly form the client
    public ResponseEntity<String> addEmployee(@RequestParam int numberOfEmployees, String gameId) {
        try {
            gameService.actionsManager(gameId);

            int initEmployees = gameService.getEmployees(gameId);

            gameService.addEmployee(gameId, numberOfEmployees);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesAdded = newEmployees - initEmployees;

            return ResponseEntity.ok(employeesAdded + " Employee(s) successfully added. You now have a total of " + newEmployees + " employees");

        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Adding employee(s) error: " + e.getMessage());
        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Actions error: " + e.getMessage());
        }

    }

    @PutMapping("/remove-employee")
    public ResponseEntity<String> removeEmployee(@RequestParam int numberOfEmployees, String gameId) {
        try {
            gameService.actionsManager(gameId);
            int initEmployees = gameService.getEmployees(gameId);

            gameService.removeEmployee(gameId, numberOfEmployees);

            int newEmployees = gameService.getEmployees(gameId);

            int employeesRemoved = initEmployees - newEmployees;

            return ResponseEntity.ok(employeesRemoved + " Employee(s) successfully removed. You now have a total of " + newEmployees + " employees");

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Removing employee(s) error: " + e.getMessage());
        }
    }

        @PostMapping("/crowd-fund")
    public ResponseEntity<String> crowdFund(@RequestParam String gameId) throws InvalidActionException {
        try {
            gameService.actionsManager(gameId);
            gameService.crowdFund(gameId);

            String formattedRevenue = gameService.getFormattedRevenue(gameId);
            return ResponseEntity.ok("Crowd fund was successful. You now have £ " + formattedRevenue);

        } catch (InvalidActionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Crowd funding error: " + e.getMessage());
        }

    }

    @PostMapping("/invest/{action}")
    public ResponseEntity<String> invest(@PathVariable String action, @RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            if("sniper".equals(action)){
                gameService.sniperInvest(gameId);
                //need to find a way to tell the user that they have lost or gained money
                return ResponseEntity.ok("Sniper investment successfully made");
            }

            if ("passive".equals(action)){
                gameService.passiveInvest(gameId);
                return ResponseEntity.ok("Passive investment successfully made");
            }

        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Investing error: " + e.getMessage());
        }

        return null;
    }


    @PostMapping("/add-department")
    public ResponseEntity<String> addDepartment(@RequestParam String gameId) throws InvalidActionException {
        try {
            gameService.actionsManager(gameId);
            gameService.addDepartment(gameId);
            int numberOfDepartments = gameService.getDepartments(gameId);
            return ResponseEntity.ok("Department added. You now have " +  numberOfDepartments + " departments");
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Add department error: " + e.getMessage());
        }
    }

    @PostMapping("/research-and-dev")
    public ResponseEntity<String> researchAndDev(@RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            gameService.researchAndDev(gameId);

            int productXP = gameService.getProductXP(gameId);
            return ResponseEntity.ok("Research and development success, 2 XP added to the product. You now have a product XP of " + productXP);
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Research and development error: " + e.getMessage());
        }
    }

    @PostMapping("/marketing")
    public ResponseEntity<String> marketing(@RequestParam String gameId) throws InvalidActionException{
        try {
            gameService.actionsManager(gameId);
            gameService.market(gameId);

            int customerBase = gameService.getCustomerBase(gameId);
            return ResponseEntity.ok("Marketing was successful. You now have a customer base of " + customerBase);
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Marketing error: " + e.getMessage());
        }
    }

    @GetMapping("/get-company/{gameId}")
    public ResponseEntity<Company> companyInfo(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getCompany(gameId));
    }

    @GetMapping("/get-game/{gameId}")
    public ResponseEntity<Game> gameInfo(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @GetMapping("/get-turn/{gameId}")
    public ResponseEntity<String> getCurrentTurn(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok("You are currently in turn " + gameService.getCurrentTurn(gameId) + " of 20");
    }

    @GetMapping("/get-actions-num/{gameId}")
    public ResponseEntity<Integer> getActionsRemaining(@PathVariable("gameId") String gameId){
        return ResponseEntity.ok(gameService.getNumberOfRemainingActions(gameId));
    }

    @GetMapping("/advance-turn/{gameId}")
    public ResponseEntity<String> advanceTurn(@PathVariable("gameId") String gameId) throws InvalidActionException {
        try {
            gameService.advanceTurn(gameId);
            return ResponseEntity.ok("You have advanced to the next turn");
        } catch (InvalidActionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Game Over: " + e.getMessage());

        }
    }

    @DeleteMapping("/delete-game")
    public ResponseEntity<String>deleteGame(@RequestParam String gameId){
        gameService.deleteGame(gameId);
        return ResponseEntity.ok("You successfully deleted the game");
    }


}

//you need to be able to show the event that occured - how would I do this? can I have gameRepository methods in the game controller section?




