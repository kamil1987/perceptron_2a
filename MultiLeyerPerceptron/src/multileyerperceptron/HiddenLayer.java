/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multileyerperceptron;

/**
 *
 * @author Kamil
 */
public class HiddenLayer {

    private double[][] hiddenNeurons;
    private double[][] weights;
    private double sums[];
    private double outputOfHiddenLayer[];
    private int numberOfHiddenNeurons;

    public HiddenLayer(int numberOfHiddenNeurons, double[] inputs) {
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
        this.hiddenNeurons = new double[numberOfHiddenNeurons][inputs.length];
        this.weights = new double[numberOfHiddenNeurons][inputs.length];
        this.sums = new double[numberOfHiddenNeurons];
        this.outputOfHiddenLayer = new double[numberOfHiddenNeurons];
    }

    public void initializeWeights() {
        for (int i = 0; i < numberOfHiddenNeurons; i++) {
            for (int j = 0; j < this.weights[0].length; j++) {
                this.weights[i][j] = -1.0 + Math.random() * 1.0;
            }
        }

    }

    public void getOutputFromInputLayer(double[] inputs) {
        for (int n = 0; n < numberOfHiddenNeurons; n++) {
            for (int i = 0; i < inputs.length; i++) {
                this.hiddenNeurons[n][i] = inputs[i];
            }
        }

    }

    public void summarize() {
        for (int i = 0; i < sums.length; i++) {
            sums[i] = 0 ;
        }
        for (int i = 0; i < numberOfHiddenNeurons; i++) {
            for (int j = 0; j < hiddenNeurons[0].length; j++) {
                sums[i] += hiddenNeurons[i][j] * weights[i][j];
            }
        }
//        double suma = weights[0] * 0 + weights[1] * zeroClass[i].x + weights[2] * zeroClass[i].y;
//        double error = 0.0;

    }

    public void activationFunction() {
        for (int i = 0; i < numberOfHiddenNeurons; i++) {
            outputOfHiddenLayer[i] = 1 / (1 + Math.pow(Math.E, -sums[i]));
        }
    }
    
    public double[] fire(double momentum){
        if (momentum != 0 ){
            for (int i = 0; i < outputOfHiddenLayer.length; i++) {
            this.outputOfHiddenLayer[i] *= momentum;
            }
        }
        return this.outputOfHiddenLayer;
    }
    
   

    public void printHiddenLayer() {
        for (int n = 0; n < numberOfHiddenNeurons; n++) {
            for (int i = 0; i < weights[0].length; i++) {
                System.out.println( "waga: " + this.weights[n][i]);
            }
            System.out.println("kolejny neuron");
        }
    }
    
    public double [][] getWeights(){
        return this.weights;
    }
    
    public void setWeights(double [][] newWeights){
        this.weights = newWeights ;
    }
    
    public void eo1net (double [] eTotalOut, double [] derivativeOfLogisticFunction){
        double [] result = new double [eTotalOut.length] ;
        double[] [] errorHidden = new double [this.weights.length] [this.weights[0].length];
        double  eTotalHidden = 0;
        
        for (int i = 0; i < eTotalOut.length; i++) {
            result[i] = eTotalOut[i] * derivativeOfLogisticFunction[i];
        }
        
        for (int i = 0; i < this.weights.length; i++) {
            for (int j = 0; j < this.weights[0].length; j++) {
                errorHidden [i][j] =result[j] * this.weights[i][j];
            }
        }
    }
    
    private void countEtotalHidden( double [] result, double[] [] errorHidden, double  eTotalHidden){
        for (int i = 0; i < errorHidden.length; i++) {
            for (int j = 0; j < errorHidden[0].length; j++) {
                eTotalHidden += errorHidden[i][j];
            }
            
        }
    }

    public void printSums() {
        for (double sum : sums) {
            System.out.println(sum);
        }
    }
    
    public int [] getNumberOfWeightsFromHidden(){
        int [] number = {this.weights.length , this.weights[0].length};
        return number;
    }

}
