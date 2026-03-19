import HelperClasses.DividedData;
import HelperClasses.Observation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        String option;
        KNN knn;
        int k;
        int correct = 0;
        int counter = 0;
        String testResult;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            DataParser dataParser = null;
            while (true){
                System.out.println("Please provide a path to the data file:");
                String src = bufferedReader.readLine();
                try {
                    dataParser = new DataParser(src);
                    break;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("Provided wrong path to file");
                }
            }
            System.out.println("What do u want to do?");
            System.out.println("1: Split file into training and test sets and run the model");
            System.out.println("2: Load the entire file for training and classify ur own vector");
            option = bufferedReader.readLine().trim();
            while (!option.equals("1") && !option.equals("2")){
                System.out.println("No option exists. Please provide valid option: ");
                option = bufferedReader.readLine().trim();
            }
            System.out.println("Provide the value for the k parameter");
            String valueString = bufferedReader.readLine();
            while (true){
                try{
                    k = Integer.parseInt(valueString);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Provide an integer number");
                }
            }
            switch (option){
                case "1":
                    DividedData dividedData = dataParser.getDividedDataSets();
                    knn = new KNN(dividedData.trainingSet, k);
                    System.out.println("Tests");
                    for (Observation observation : dividedData.testSet){
                        testResult = knn.runTest(observation);
                        System.out.println("Expected: " + observation.decisiveArgument + " Got: " + testResult);
                        if (testResult.equals(observation.decisiveArgument)){
                            correct++;
                        }
                        counter++;
                    }
                    break;
                case "2":
                    knn = new KNN(dataParser.getData(), k);
                    String[] vec;
                    Observation observation;
                    String validationString;
                    while (true){
                        System.out.println("Provide a vector(format: value1, value2, value3):");
                        vec = bufferedReader.readLine().split(", ");
                        try {
                            observation = new Observation(vec);
                            testResult = knn.runTest(observation);
                            System.out.println("Predicted decisive argument: " + testResult);
                            System.out.println("Correct?(Y for correct)");
                            validationString = bufferedReader.readLine();
                            if (validationString.equals("Y")){
                                correct++;
                            }
                            counter++;
                        }
                        catch (RuntimeException e){
                            System.out.println("Not a valid vector");
                        }
                        System.out.println("Do u want to input another?(Y for yes)");
                        validationString = bufferedReader.readLine();
                        if (!Objects.equals(validationString, "Y")) break;
                    }
                break;
                default:
                    return;
            }
            if (counter!=0){
                System.out.format("Total accuracy of model:  %.2f", 100.0*correct/counter);
                System.out.print("%");
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
