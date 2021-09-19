import controller.GameController;
import view.GameStatsView;

import java.io.IOException;
import java.util.Scanner;

public class SoccerAnalyzerApp {
    public static void main(String args[]) throws IOException {

        GameController gameController = new GameController();
        GameStatsView gsv = new GameStatsView();
        Scanner sc = new Scanner(System.in);
        String timeStamp ="";
        String filePath = "src/resource/gameData.csv";
        gameController.readCsv(filePath);

        System.out.print("Enter time: ");
        timeStamp = sc.nextLine();
        System.out.println(gsv.statsView(timeStamp));
    }
}
