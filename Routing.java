import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Routing {

    long currentTime, helloTimeStamp, routingTimeStamp, dataTimeStamp;
    long latestHelloTimeStamp[];
    boolean timeLeft;
    final long helloTime = 5000;
    final int routingTime = 10000;
    final int dataTime = 15000;
    final int deadTime = 30000;
    FileReader fileReader;
    BufferedReader bufferedReader;
    ArrayList<Integer> neigbhorMap; //list of neigbours for the particular node
    fileReadWrite readWrite;
    int linesRead[];

    public Routing() {
        currentTime = System.currentTimeMillis();
        helloTimeStamp = currentTime;
        routingTimeStamp = currentTime;
        dataTimeStamp = currentTime;
        timeLeft = true;
        readWrite = new fileReadWrite();
        neigbhorMap = new ArrayList<>();
        latestHelloTimeStamp = new long[10];
        linesRead = new int[10];
    }

    public void helloProtocol(int nodeId) {
        String data = "hello "+nodeId +" "+ System.currentTimeMillis();
        // System.out.println("Data: "+data);
        readWrite.writeDataOutput(data, nodeId);
    }

    public void sendRoutingProtocol() {
        // System.out.println("In Send Routing Protocol");
    }

    public void sendDataProtocol() {
        // System.out.println("In Send Data Protocol");
    }

    public void deadNeigbhourCheck() {
        // System.out.println("In Dead Neigbhour Check");
    }

    public void startNetworkRouting (int nodeId, int timeToLive, int destNodeId, String message) {
        System.out.println("start Network");
        
        initializeLastHelloTimeStamp();

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

            // for(int neigbhour: neigbhorMap) {
            //     // System.out.println("\n\nNeighbour: "+neigbhour);
            //     // System.out.println("curretn time: "+System.currentTimeMillis());
            //     // System.out.println("last time: "+ (latestHelloTimeStamp[neigbhour]));
            //     if(System.currentTimeMillis() - latestHelloTimeStamp[neigbhour] > deadTime) {
            //         System.out.println("came in ");
            //         neigbhorMap.remove(neigbhour);
            //         //TODO: Create InTree
            //         String data = "Deleted node "+neigbhour;
            //         System.out.println("Data:"+data);
            //         readWrite.writeDataOutput(data, nodeId);
            //     }
            // }
            
            readInputFile(nodeId);
            
            
            if(System.currentTimeMillis() - currentTime > (timeToLive*1000)) {
                System.out.println(nodeId+"'s neigbors:"+neigbhorMap);
                timeLeft = false;
            }
        }
    }

    public void initializeLastHelloTimeStamp() {

    }

    public void readInputFile(int nodeId) {
        
        String fileName = "./inputs/input_"+nodeId+".txt";
        
        try{
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String str = bufferedReader.readLine();
            if(str!=null) {
                String[] tokens = str.split(" ");
                int neig = Integer.parseInt(tokens[1]);
                // if(neig == 1)
                //     System.out.println("Lines read for "+neig+" : "+linesRead[neig]);
                for(int i = 0; i<linesRead[neig];i++){
                    String line = bufferedReader.readLine();
                }
                
                str = bufferedReader.readLine();
            }
            while (str != null) {
                String[] tokens = str.split(" ");
                int neigbhourId = Integer.parseInt(tokens[1]);
                linesRead[neigbhourId]++;

                //Hello Message
                if (tokens[0].equals("hello")) {
                    long newTimestamp = System.currentTimeMillis();                   
                    latestHelloTimeStamp[neigbhourId]=newTimestamp;
                    
                    if(!neigbhorMap.contains(neigbhourId)) {
                        neigbhorMap.add(neigbhourId);
                        String temp3 = "Added node:"+neigbhourId;
                        readWrite.writeDataOutput(temp3, nodeId);
                    }
                }

                str = bufferedReader.readLine();
            }
            bufferedReader.close();
            
        } catch(Exception e) {

        }
        

    }
}