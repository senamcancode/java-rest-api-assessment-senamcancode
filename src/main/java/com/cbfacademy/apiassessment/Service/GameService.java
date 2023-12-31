package com.cbfacademy.apiassessment.Service;

import com.cbfacademy.apiassessment.Database.Database;
import com.cbfacademy.apiassessment.FinTechClasses.Company;
import com.cbfacademy.apiassessment.FinTechClasses.Game;
import com.cbfacademy.apiassessment.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    public List<Game> getAllGames() throws FileNotFoundException {
        return gameRepository.getAllGames();
    }

    public void newGame() throws FileNotFoundException {
        Game game = new Game();

        Database gameData = new Database();
        gameData.addGame(game);

        gameRepository.saveGameData(gameData);


    }

    public void appendNewGame() throws FileNotFoundException {
        gameRepository.appendGameData();
    }

    public void nameCompany(String gameId, String companyName) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().setCompanyName(companyName);
        gameRepository.updateGameDataById(gameId, game);

    }

    public String addEmployee(String gameId, int numberOfEmployees) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        //this line is constantly repeated can I refactor?
        assert game != null;


        String actionMessage = game.getCompany().addEmployee(numberOfEmployees);


        gameRepository.updateGameDataById(gameId, game);

        return actionMessage;
    }

    public String removeEmployee(String gameId, int numberOfEmployees) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String actionMessage = game.getCompany().removeEmployee(numberOfEmployees);


        gameRepository.updateGameDataById(gameId, game);

        return actionMessage;
    }

    public void crowdFund(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().crowdFund();


        gameRepository.updateGameDataById(gameId, game);

    }

    public String sniperInvest(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String resultMessage = game.getCompany().sniperInvestment();


        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }

    public String passiveInvest(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String resultMessage = game.getCompany().passiveInvestment();


        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }

    public String addDepartment(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String resultMessage = game.getCompany().addDepartment();


        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }

    public String researchAndDev(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String resultMessage = game.getCompany().researchAndDev();


        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }

    public String market(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        String resultMessage = game.getCompany().marketing();


        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }


    //getters
    public int getEmployees(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        return game.getCompany().getEmployees();
    }

    public int getDepartments(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCompany().getDepartments();
    }

    //may have to put the formatted revenue in the game class and return it to here
    public String getFormattedRevenue(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        double revenue = game.getCompany().getRevenue();

        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(revenue);

    }

    public int getProductXP(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.getCompany().getProductXP();
    }

    public int getCustomerBase(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        return game.getCompany().getCustomerBase();
    }

    public Company getCompany(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.getCompany();
    }

    public Game getGame(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        return game;
    }

    public int getCurrentTurn(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.getCurrentTurn();
    }

    public int getMaxTurns(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.getMaxTurnsPerGame();
    }

    public int getNumberOfRemainingActions(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;
        return game.actionsRemaining();
    }

    public boolean checkGameIsOver(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        if (game.checkGameIsOver()) {
            game.setGameIsOverToTrue();
            return true;
        }
        gameRepository.updateGameDataById(gameId, game);

        return false;
    }

    public boolean checkGameIsCompleted(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        if (game.checkGameIsCompleted()) {
            game.setGameIsCompletedToTrue();
            return true;
        }
        gameRepository.updateGameDataById(gameId, game);
        return false;
    }

    public boolean advanceTurn(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;

        game.getCompany().customerRevenueBoost();

        game.advanceTurn();


        gameRepository.updateGameDataById(gameId, game);
        return false;

    }

    public String triggerRandomEvent(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game != null;

        String resultMessage = game.triggerRandomEvent();

        gameRepository.updateGameDataById(gameId, game);

        return resultMessage;
    }


    public void deleteGame(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        gameRepository.deleteGameById(gameId, game);

        gameRepository.updateGameDataById(gameId, game);
    }

    public void motherLode(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().motherLode();

        gameRepository.updateGameDataById(gameId, game);
    }

    public void moneyMoneyMoney(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game != null;
        game.getCompany().moneyMoneyMoney();

        gameRepository.updateGameDataById(gameId, game);
    }


    public boolean validActionCheck(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);

        assert game!= null;
        boolean result = game.invalidAction();

        return result;

    }

    public void actionCountIncrement(String gameId) throws FileNotFoundException {
        Game game = GameRepository.retrieveGame(gameId);
        assert game!= null;

        game.actionIncrement();

        gameRepository.updateGameDataById(gameId, game);

    }

}



