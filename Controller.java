import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    fileReadWrite fileReadWrite;
    private int timeToLive;
    long currentTime;
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

        currentTime = System.currentTimeMillis();

        try {
            
            while(timeLeft) {
                //Read data from all node's output file in order
                for(int key: topologyMap.keySet()) { 
                    // System.out.println(key+" "+ topologyMap.get(key) +" "+ linesRead[key]);
                    // System.out.println("No of Lines done: "+ linesRead[key]);
                    int noOfLines = fileReadWrite.readData(key, linesRead[key], topologyMap.get(key));
                    // System.out.println("no of lines added " + noOfLines);
                    linesRead[key]+=noOfLines;
                }

                // Check if time to live is there
                if(System.currentTimeMillis()-currentTime > timeToLive*1000)
                    timeLeft = false;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        Controller c = new Controller();
        System.out.println("Controller started");
        c.startController(args[0]);
        System.exit(0);    
    }

    
}


