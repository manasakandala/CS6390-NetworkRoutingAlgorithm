import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class fileReadWrite {

    FileWriter fileWriter;
    FileReader fileReader;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    int n;

    HashMap<Integer, ArrayList<Integer>> topologyMap;

    public fileReadWrite() {
        topologyMap = new HashMap<>();
    }
    
    // Write data into output file
    public void writeDataOutput(String data, int nodeId) {
        String fileName = "./outputs/output_"+nodeId+".txt";
        try{
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.newLine();

        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Write data into input file
    public void writeDataInput(String data, int nodeId) {
        String fileName = "./inputs/input_"+nodeId+".txt";

        try{
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Read data from file
    public int readData(int nodeId, int linesRead, ArrayList<Integer> neigbhorsList) {
        n = 0;
        String fileName = "./outputs/output_"+nodeId+".txt";
        try{
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            
            for(int i = 0; i<linesRead;i++){
                String line = bufferedReader.readLine();
                // System.out.println("Ignored " + i+" "+line);
            }

            String line = bufferedReader.readLine();
            // System.out.println(line);
            n =0;

            while(line != null) {
                System.out.println("Line: "+line);
                n++;
                for(int neigbhour: neigbhorsList) {
                    // System.out.println("write to inout file");
                    writeDataInput(line, neigbhour);
                }

                
                // System.out.println("after write temp: "+n);
                line = bufferedReader.readLine();
            } 

        } catch(FileNotFoundException fileNotFoundException) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(n>0)
            System.out.println("final temp: "+n);
        return n;
    }

    // read topology file
    public HashMap<Integer, ArrayList<Integer>> readTopology() {

        String fileName = "topology.txt";
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
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
            e.printStackTrace();
        }
        return topologyMap;
    }
}
