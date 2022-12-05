public class Routing {

    long currentTime, helloTimeStamp, routingTimeStamp, dataTimeStamp, deadTimeStamp;
    boolean timeLeft;
    final long helloTime = 5000;
    final int routingTime = 10000;
    final int dataTime = 15000;
    final int deadTime = 30000;

    fileReadWrite readWrite;

    public Routing() {
        currentTime = System.currentTimeMillis();
        helloTimeStamp = currentTime;
        routingTimeStamp = currentTime;
        dataTimeStamp = currentTime;
        deadTimeStamp = currentTime;
        timeLeft = true;
        readWrite = new fileReadWrite();
    }

    public void helloProtocol(int nodeId) {
        String data = "hello "+nodeId;
        readWrite.writeDataOutput(data, nodeId);
    }

    public void sendRoutingProtocol() {
        // System.out.println("In Send Routing Protocol");
    }

    public void sendDataProtocol() {
        // System.out.println("In Send Data Protocol");
    }

    public void readData() {
        // System.out.println("In Read Data");
    }

    public void deadNeigbhourCheck() {
        // System.out.println("In Dead Neigbhour Check");
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

            if(System.currentTimeMillis() - deadTimeStamp > deadTime) {
                deadNeigbhourCheck();
                deadTimeStamp = System.currentTimeMillis();
            }
            
            readData();
            
            if(System.currentTimeMillis() - currentTime > (timeToLive*1000)) {
                timeLeft = false;
            }
        }
    }
}
