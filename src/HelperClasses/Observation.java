package HelperClasses;

import java.util.Arrays;

public class Observation{
    public double[] arguments;
    public String decisiveArgument;

    public Observation(String[] arguments) throws NumberFormatException{
        this.arguments = new double[arguments.length];
        this.decisiveArgument = null;
        for (int i=0;i<arguments.length;i++){
            this.arguments[i] = Double.parseDouble(arguments[i]);
        }
    }
    public Observation(String[] arguments, String decisiveArgument){
        this.arguments = new double[arguments.length];
        this.decisiveArgument = decisiveArgument;
        for (int i=0;i<arguments.length;i++){
            this.arguments[i] = Double.parseDouble(arguments[i]);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.arguments) + " :" + this.decisiveArgument;
    }
}
