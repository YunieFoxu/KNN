import HelperClasses.Observation;
import HelperClasses.Tuple;

import java.util.*;

public class KNN {
    List<Observation> data;
    int k;
    public KNN(List<Observation> data, int n){
        this.data = data;
        this.k = n;
    }
    public String runTest(Observation observation){
        List<Tuple<Observation, Double>> distanceList = new ArrayList<>();
        Comparator<Tuple<Observation, Double>> myComparator = Comparator.comparingDouble(t -> t.param2);
        Map<String, Tuple<Integer, Double>> countMap = new HashMap<>();
        List<Map.Entry<String, Tuple<Integer, Double>>> typesInOrder = List.of();

        //create a list of tuples consisting of an observation,
        // and it's distance from the observation provided by the user
        for (Observation obs : this.data){
            if (!countMap.containsKey(obs.decisiveArgument)) countMap.put(obs.decisiveArgument, new Tuple<>(0, 0.0));
            distanceList.add(new Tuple<>(obs, CalculateDistanceBetweenVectors(obs.arguments, observation.arguments)));
        }
        distanceList.sort(myComparator);

        for (int i=0;i<distanceList.size();i++){
            countMap.put(
                distanceList.get(i).param1.decisiveArgument,
                new Tuple<>(
                    countMap.get(distanceList.get(i).param1.decisiveArgument).param1+1,
                    countMap.get(distanceList.get(i).param1.decisiveArgument).param2+distanceList.get(i).param2
                )
            );
            typesInOrder = countMap.entrySet()
                    .stream()
                    .sorted((e1, e2) -> {
                        if (e1.getValue().param1-e2.getValue().param1!=0) return e2.getValue().param1-e1.getValue().param1;
                        else return Double.compare(e1.getValue().param2, e2.getValue().param2);
                    })
                    .toList();
            //provided one of the classes dominates return the name and we have k closest records. If not increase n by 1
            if (i>=k){
                if (typesInOrder.stream().limit(k).map(Map.Entry::getKey).distinct().count()==1)
                    return typesInOrder.get(0).getKey();
                else if (typesInOrder.get(0).getValue().param1!=typesInOrder.get(1).getValue().param1 || //ilość taka sama w 1 i 2 z listy
                        (typesInOrder.get(0).getValue().param2!=typesInOrder.get(1).getValue().param2)) //odległość wektorów taka sama w 1 i 2 z listy
                    return typesInOrder.get(0).getKey();
            }

        }

        //return the first type if other methods did not help
        return typesInOrder.get(0).getKey();
    }

    public double CalculateDistanceBetweenVectors(double[] vec1, double[] vec2){
        double distance = 0;
        if (vec1.length!=vec2.length)
            throw new RuntimeException("Vectors are of different lenghts");
        for (int i=0;i<vec1.length;i++){
            distance+=Math.pow(vec1[i]-vec2[i],2);
        }
        return Math.sqrt(distance);
    }
}
