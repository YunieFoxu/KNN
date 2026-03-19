import HelperClasses.DividedData;
import HelperClasses.Observation;

import java.io.*;
import java.util.*;

public class DataParser {
    public Map<String, List<Observation>> data = new HashMap<>();

    public DataParser(String filePath) throws IOException{
        try (
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        )
        {
            //loading data from file
            bufferedReader
                    .lines()
                    .skip(1)
                    .map(line -> line
                        .split(","))
                        .forEach(arr ->{
                            if (!data.containsKey(arr[arr.length-1]))
                                data.put(arr[arr.length-1], new ArrayList<>());
                            data.get(arr[arr.length-1]).add(new Observation(Arrays.copyOf(arr, arr.length-1), arr[arr.length-1]));
                        });
            //shuffles data in their categories
            for (String key : data.keySet()){
                List<Observation> tmp = data.get(key);
                Collections.shuffle(tmp);
                data.put(key, tmp);
            }
        }
    }

    public List<Observation> getData(){
        List<Observation> result = new ArrayList<>(this.data
                .values()
                .stream()
                .flatMap(List::stream)
                .toList());
        Collections.shuffle(result);
        return result;
    }

    public DividedData getDividedDataSets(){
        List<Observation> observations;
        List<Observation> trainingList = new ArrayList<>();
        List<Observation> testList = new ArrayList<>();

        for (String key : data.keySet()){
            observations = data.get(key);
            for (int i=0;i<observations.size();i++){
                if (i<observations.size()/5*3) //proporcja 3 : 2
                    trainingList.add(observations.get(i));
                else
                    testList.add(observations.get(i));
            }
        }

        Collections.shuffle(trainingList);
        Collections.shuffle(testList);
        return new DividedData(trainingList, testList);
    }
}
