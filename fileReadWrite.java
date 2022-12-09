import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class fileReadWrite {
    int n;

    HashMap<Integer, ArrayList<Integer>> topologyMap;

    public fileReadWrite() {
        topologyMap = new HashMap<>();
    }
    
    // Write data into output file
    public void writeDataOutput(String data, int nodeId) {
        String fileName = "./outputs/output_"+nodeId+".txt";
        
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(data);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Write data into input file
    public void writeDataInput(String data, int nodeId) {
        String fileName = "./inputs/input_"+nodeId+".txt";

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(data);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Read data from file
    public int readData(int nodeId, int linesRead, ArrayList<Integer> neigbhorsList) {
        n = 0;
        String fileName = "./outputs/output_"+nodeId+".txt";
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            
            for(int i = 0; i<linesRead;i++){
                bufferedReader.readLine();
            }

            String line = bufferedReader.readLine();
            n =0;

            while(line != null) {
                n++;
                for(int neigbhour: neigbhorsList) {
                    // System.out.println("write to inout file");
                    writeDataInput(line, neigbhour);
                }

                line = bufferedReader.readLine();
            } 

            bufferedReader.close();

        } catch(FileNotFoundException fileNotFoundException) {

        } catch (IOException e) {
           
        }
        return n;
    }

    // read topology file
    public HashMap<Integer, ArrayList<Integer>> readTopology() {

        String fileName = "topology.txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = bufferedReader.readLine();

            while(line != null) {
                ArrayList<Integer>  neigbList = new ArrayList<>();

                String[] data = line.split(" ");
                int nodeId = Integer.parseInt(data[0]);
                int neigbhourNodeId = Integer.parseInt(data[1]);

                if(topologyMap.containsKey(nodeId)) {
                    neigbList = topologyMap.get(nodeId);
                } else {
                    neigbList.clear();
                }
                neigbList.add(neigbhourNodeId);
                topologyMap.put(nodeId, neigbList);

                line = bufferedReader.readLine();

            }

        } catch (Exception e) {
            
        }
        return topologyMap;
    }

    
}
