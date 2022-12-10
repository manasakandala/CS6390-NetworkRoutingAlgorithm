import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Routing {

    Instant curDateTime, helloDateTime, routingDateTime, dataDateTime;
    Instant lastHelloDateTime[];

    String inTreeRep;
    boolean timeLeft;
    final long helloTime = 5; 
    final long routingTime = 10;
    final long dataTime = 15;
    final long deadTime = 30;
    ArrayList<Integer> neigbhorMap; //list of neigbours for the particular node
    fileReadWrite readWrite;
    int linesRead;

    public Routing() {
        inTreeRep = " ";
        curDateTime = Instant.now();
        helloDateTime = curDateTime;
        routingDateTime = curDateTime;
        dataDateTime = curDateTime;
        lastHelloDateTime = new Instant[10];

        timeLeft = true;
        readWrite = new fileReadWrite();
        neigbhorMap = new ArrayList<>();
        linesRead = 0;
    }

    public void updateInTree(int nodeId) {
        inTreeRep = "intree "+ nodeId;
        for(int neigbhour: neigbhorMap) {
            inTreeRep += ("("+neigbhour+" "+nodeId+")");
        }
        // System.out.println(inTreeRep);
    }


    public void helloProtocol(int nodeId) {
        // System.out.println("In Hello Protocol");
        String data = "hello "+nodeId +" "+ System.currentTimeMillis();
        readWrite.writeDataOutput(data, nodeId);
    }

    public void sendRoutingProtocol(int nodeId) {
        // System.out.println("In Send Routing Protocol");
        String data = "" + inTreeRep;
        System.out.println(data);
        // readWrite.writeDataOutput(data, nodeId);
        
    }

    public void sendDataProtocol(int nodeId, int destNodeId, String message) {
        System.out.println("DestId: "+destNodeId+"\nMessage: "+message);
    }

    public void deadNeigbhourCheck(int nodeId) {
        // System.out.println("In Dead Neigbhour Check");
        try{
            Iterator<Integer> it = neigbhorMap.iterator();
            while(it.hasNext()) {
                int neigbhour = it.next();
                if(lastHelloDateTime[neigbhour].until(Instant.now(), ChronoUnit.SECONDS) > deadTime) {
                    neigbhorMap.remove(Integer.valueOf(neigbhour));
                    updateInTree(nodeId);
                    String data = "Deleted node "+neigbhour;
                    System.out.println(data);
                    readWrite.writeDataOutput(data, nodeId);
                }
            } 
        } catch(ConcurrentModificationException c) {

        }
    }

    public void startNetworkRouting (int nodeId, int timeToLive, int destNodeId, String message) {
        System.out.println("start Network");
        while(timeLeft) {
            
            // System.out.print("Curr Time"+Instant.now()+" ");
            // System.out.println("Time "+helloDateTime.until(Instant.now(), ChronoUnit.SECONDS)+"\n");

            if(helloDateTime.until(Instant.now(), ChronoUnit.SECONDS) > helloTime) {
                helloProtocol(nodeId);
                helloDateTime = Instant.now();
            }

            if(routingDateTime.until(Instant.now(), ChronoUnit.SECONDS) > routingTime) {
                sendRoutingProtocol(nodeId);
                routingDateTime = Instant.now();
            }

            if(dataDateTime.until(Instant.now(), ChronoUnit.SECONDS) > dataTime) {
                if(destNodeId!=-1) {      
                    sendDataProtocol(nodeId, destNodeId, message);
                    dataDateTime = Instant.now();
                }
            }

            deadNeigbhourCheck(nodeId);
            readInputFile(nodeId);
            
            if(curDateTime.until(Instant.now(), ChronoUnit.SECONDS) > (timeToLive)) {
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
                
                for(int i = 0; i<linesRead; i++){
                    line = bufferedReader.readLine();
                }
            }
            
            while(line != null) {
                
                count++;
                String[] tokens = line.split(" ");
                int neigbhourId = Integer.parseInt(tokens[1]);

                //Hello Message
                if (tokens[0].equals("hello")) {
                    lastHelloDateTime[neigbhourId]= Instant.now();
                    System.out.println(lastHelloDateTime[neigbhourId]);

                    if(!neigbhorMap.contains(neigbhourId)) {
                        neigbhorMap.add(neigbhourId);
                        updateInTree(nodeId);
                    }
                }

                line = bufferedReader.readLine();
            }
            
            linesRead+=count;
            bufferedReader.close();
            
        } catch(Exception e) {

        }
        

    }
}