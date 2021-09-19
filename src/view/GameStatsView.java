package view;

import controller.GameController;

public class GameStatsView {
    GameController gameController = new GameController();

    public String statsView(String timeStamp){
        String stats = "";
        try{
            gameController.setTimeStamp(timeStamp);
            stats = gameController.GameStats().toString();
            }catch (Exception e){
            System.out.println("Invalid time stamp");
        }
        return stats;
    }
}
