package controller;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;


public class GameController {
    final static int START = 0;
    final static int BREAK = 2700;
    final static int END = 5400;

    final static String shot = "SHOT";
    final static String score = "SCORE";

    public int timeStamp;
    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = toSecond(timeStamp);
    }

    static Map<Integer,String> gameDataMap = new TreeMap<>();

    public void readCsv(String filePath) throws IOException {
        BufferedReader br =  new BufferedReader(new FileReader(filePath));
        br.readLine(); //Skip header
        String line ="";

        while ( (line = br.readLine())!=null ){
            String listEvents[] = line.split(",");
            if(listEvents.length>2) {
                gameDataMap.put(toSecond(listEvents[0]), listEvents[1].trim() + "," + listEvents[2].trim());
            } else{
                gameDataMap.put(toSecond(listEvents[0]), listEvents[1].trim());
            }
        }
    }

    private int toSecond(String timeStamp){
        String[] time = timeStamp.split(":");
        int stampInSeconds = (Integer.parseInt(String.valueOf(time[0]))* 60) + (Integer.parseInt(String.valueOf(time[1])));
        return stampInSeconds;
    }

    private String getPercentage(float team[]){
        float total = team[0] + team[1];
        float percentageOfA = (team[0] / total) * 100;
        float percentageOfB = (team[1] / total) * 100;
        return ("Team A: "+(int)percentageOfA+"%"+ " Team B: " +(int)percentageOfB +"%");
    }

    public String GameStats(){
        String[] totalStats = {getPercentage(possessionCount()),  Arrays.toString(scoreCounter(shot)), Arrays.toString(scoreCounter(score))};
        return Arrays.toString(totalStats);

    }

    public int[] scoreCounter(String attempt){
        int TeamAScore = 0;
        int TeamBScore = 0;

        for(int i = 0; i <= getTimeStamp(); i++) {
            if(gameDataMap.containsKey(i)) {
                String state[] = gameDataMap.get(i).split(",");
                if(state[0].equals(attempt)){
                    System.out.print(attempt+" "+i);
                    if( state[1].equals("A")){  TeamAScore++;
                    } else {  TeamBScore++; }
                }
            }
        }
        return new int[]{TeamAScore,TeamBScore};
    }

    public float[] possessionCount(){
        int TeamAPoss = 0;
        int TeamBPoss = 0;
        int PERIOD = getTimeStamp();
        String isActive ="";

        for(int i=0; i<=PERIOD; i++){
            if(gameDataMap.containsKey(i)){
                if(ActiveTeam(i).equals("A")){
                    isActive = "A";
                }else if(ActiveTeam(i).equals("B")){
                    isActive = "B";
                }
            }
            if( i > 0 ){
                if(isActive.equals("A" )){
                    TeamAPoss++;
                }else if(isActive.equals("B")){
                    TeamBPoss++;
                }
            }
        }
        return new float[] {TeamAPoss,TeamBPoss};
    }

    public String ActiveTeam(int PERIOD){
        String activeTeam = previousActive();
        if(gameDataMap.containsKey(PERIOD)) {
            String team[] = gameDataMap.get(PERIOD).split(",");
            if (team.length == 2) {
                if (team[1].equals("A") == false) {
                    activeTeam = "B";
                } else {
                    activeTeam = "A";
                }
            }
        }
        return activeTeam;
    }

    public String previousActive(){
        String prevActive = "";
        String startTime[] = gameDataMap.get(START).split(",");
        if (startTime[1].equals("A")) {  prevActive = "A";   } else { prevActive = "B";   }
        return prevActive;
    }

}
