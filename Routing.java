import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Routing {

    long currentTime, helloTimeStamp, routingTimeStamp, dataTimeStamp;
    long latestHelloTimeStamp[];
    boolean timeLeft;
    final long helloTime = 5000; 
    final long routingTime = 10000;
    final long dataTime = 15000;
    final long deadTime = 300000;
    ArrayList<Integer> neigbhorMap; //list of neigbours for the particular node
    fileReadWrite readWrite;
    int linesReadFromNeigh;

    public Routing() {
        currentTime = System.currentTimeMillis();
        helloTimeStamp = currentTime;
        routingTimeStamp = currentTime;
        dataTimeStamp = currentTime;
        timeLeft = true;
        readWrite = new fileReadWrite();
        neigbhorMap = new ArrayList<>();
        latestHelloTimeStamp = new long[10];
        linesReadFromNeigh = 0;
    }

    public void helloProtocol(int nodeId) {
        System.out.println("In Hello Protocol");
        String data = "hello "+nodeId +" "+ System.currentTimeMillis();
        readWrite.writeDataOutput(data, nodeId);
    }

    public void sendRoutingProtocol() {
        System.out.println("In Send Routing Protocol");
    }

    public void sendDataProtocol() {
        System.out.println("In Send Data Protocol");
    }

    public void deadNeigbhourCheck() {
        System.out.println("In Dead Neigbhour Check");
    }

    public void startNetworkRouting (int nodeId, int timeToLive, int destNodeId, String message) {
        System.out.println("start Network");

        while(timeLeft) {

            if(System.currentTimeMillis() - helloTimeStamp > helloTime) {
                helloProtocol(nodeId);
                helloTimeStamp = System.currentTimeMillis();
            }

            if(System.currentTimeMillis() - routingTimeStamp > routingTime) {
                sendRoutingProtocol();
                routingTimeStamp = System.currentTimeMillis();
            }

            if(System.currentTimeMillis() - dataTimeStamp > dataTime) {
                sendDataProtocol();
                dataTimeStamp = System.currentTimeMillis();
            }

            // long cur_time = System.currentTimeMillis();
            for(int neigbhour: neigbhorMap) {
                // System.out.println("\n\nNeighbour: "+neigbhour);
                // System.out.println(cur_time+" current");
                // System.out.println(latestHelloTimeStamp[neigbhour]+ " saved");

                if(System.currentTimeMillis() - latestHelloTimeStamp[neigbhour] > deadTime) {
                    System.out.println(nodeId+" came in ");
                    // neigbhorMap.remove(neigbhour);
                    // //TODO: Create InTree
                    // String data = "Deleted node "+neigbhour;
                    // System.out.println("Data:"+data);
                    // readWrite.writeDataOutput(data, nodeId);
                }
            }
            readInputFile(nodeId);
            
            if(System.currentTimeMillis() - currentTime > (timeToLive*1000)) {
                System.out.println(nodeId+"'s neigbors:"+neigbhorMap);
                timeLeft = false;
            }
        }
    }

    public void readInputFile(int nodeId) {
        
        String fileName = "./inputs/input_"+nodeId+".txt";
        
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            int count=0;
            String line = bufferedReader.readLine();

            if(line!=null) {
                
                for(int i = 0; i<linesReadFromNeigh; i++){
                    line = bufferedReader.readLine();
                }
                
            }
            
            while(line != null) {
                count++;
                String[] tokens = line.split(" ");
                int neigbhourId = Integer.parseInt(tokens[1]);

                //Hello Message
                if (tokens[0].equals("hello")) {
                    // long curTime = System.currentTimeMillis();
                    latestHelloTimeStamp[neigbhourId]= System.currentTimeMillis();
                    // System.out.println( nodeId+" "+curTime);

                    if(!neigbhorMap.contains(neigbhourId)) {
                        neigbhorMap.add(neigbhourId);
                        String data = "Added node:"+neigbhourId;
                        readWrite.writeDataOutput(data, nodeId);
                    }
                }

                line = bufferedReader.readLine();
            }
            linesReadFromNeigh+=count;
            bufferedReader.close();
            
        } catch(Exception e) {

        }
        

    }
}