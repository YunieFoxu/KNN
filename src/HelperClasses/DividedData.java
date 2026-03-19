package HelperClasses;

import java.util.ArrayList;
import java.util.List;

public class DividedData{
    public List<Observation>trainingSet;
    public List<Observation> testSet;
    public DividedData(List<Observation> trainingSet, List<Observation> testSet){
        this.trainingSet = trainingSet;
        this.testSet = testSet;
    }
}