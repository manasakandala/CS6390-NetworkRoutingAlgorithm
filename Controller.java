import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Controller {

    fileReadWrite fileReadWrite;
    private int timeToLive;
    Instant currentTime;
    HashMap<Integer, ArrayList<Integer>> topologyMap;
    boolean timeLeft;
    int[] linesRead;

    public Controller() {
        fileReadWrite = new fileReadWrite();
        topologyMap = new HashMap<>();
        timeLeft = true;
        linesRead = new int[10];
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }



    public void startController(String input) {
        setTimeToLive(Integer.parseInt(input));
        topologyMap = fileReadWrite.readTopology();
        for(HashMap.Entry<Integer,ArrayList<Integer>> node : topologyMap.entrySet()) {
            System.out.println(node.getKey()+" : "+node.getValue());
        }

        currentTime = Instant.now();

        try {
            
            while(timeLeft) {
                //Read data from all node's output file in order
                for(int key: topologyMap.keySet()) { 
                    int noOfLines = fileReadWrite.readData(key, linesRead[key], topologyMap.get(key));
                    linesRead[key]+=noOfLines;
                }

                // Check if time to live is there
                if(currentTime.until(Instant.now(), ChronoUnit.SECONDS) > timeToLive) {
                    System.out.println("Dying at: "+currentTime.until(Instant.now(), ChronoUnit.SECONDS));
                    timeLeft = false;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        Controller c = new Controller();
        System.out.println("Controller started");
        c.startController(args[0]);
        System.out.println("finished everyting");
        System.exit(0);    
    }

    
}


