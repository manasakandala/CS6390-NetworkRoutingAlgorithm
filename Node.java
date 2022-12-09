public class Node {
    
    private int nodeId, timeToLive, destNodeId;
    private String message;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getDestNodeId() {
        return destNodeId;
    }

    public void setDestNodeId(int destNodeId) {
        this.destNodeId = destNodeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Node() {
        this.message = "";
    }

    public static void main(String args[]) {
        
        Node n = new Node();
        n.setNodeId(Integer.parseInt(args[0]));
        n.setTimeToLive(Integer.parseInt(args[1]));
        n.setDestNodeId(Integer.parseInt(args[2]));

        // System.out.println("NodeId: "+n.getNodeId());
        // System.out.println("DestNodeId: "+n.getDestNodeId());
        if(n.destNodeId != -1) {
            n.setMessage(args[3]);
        }

        Routing r = new Routing();
        r.startNetworkRouting(n.getNodeId(), n.getTimeToLive(), n.getDestNodeId(), n.getMessage());
        System.out.println("Finished my life too!!");
        System.exit(0);  

    }
}
