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
public class OutputLayer {
    private double[][] outputNeurons;
    private double[][] weights;
    private double sums[];
    private double outputOfOutputLayer[];
    private double outputError[];
    private double desiredOutput[];
    private int numberOfOutputNeurons;
    
    private double  [][] eTotalWeight;
    private double [] outputFromHiddenLayer;
    private double [] eTotalOut;
    private double [] derivativeOfLogisticFunction;
    private double totalError ;
    
   
    //backpropagation dla wag input-hidden
    private double [] errorRespectToSum;
    private double [][] errorOutHidden;
     private double [] eTotalOutHidden;
     private double [] outH1NetH1;
     private double [][] eTotalW;
    
    public OutputLayer(int numberOfOutputNeurons, int numberOfInputs) {
        this.numberOfOutputNeurons = numberOfOutputNeurons;
        this.outputNeurons = new double[numberOfOutputNeurons][numberOfInputs];
        this.weights = new double[numberOfOutputNeurons][numberOfInputs];
        this.sums = new double[numberOfOutputNeurons];
        this.outputOfOutputLayer = new double[numberOfOutputNeurons];
        this.outputError = new double [numberOfOutputNeurons] ;
        this.desiredOutput = new double [numberOfOutputNeurons] ;
    }
    
    public OutputLayer(int numberOfOutputNeurons){
        
    }
    
    public void initializeWeights() {
        for (int i = 0; i < numberOfOutputNeurons; i++) {
            for (int j = 0; j < this.weights[0].length; j++) {
                this.weights[i][j] = -1.0 + Math.random() * 1.0;
            }
        }

    }
    
    public void getOutputFromHiddenLayer(double[] inputs) {
        this.outputFromHiddenLayer = inputs;
        for (int n = 0; n < numberOfOutputNeurons; n++) {
            for (int i = 0; i < inputs.length; i++) {
                this.outputNeurons[n][i] = inputs[i];
            }
        }

    }
    
    public void summarize() {
        for (int i = 0; i < sums.length; i++) {
            sums[i] = 0 ;
        }
        for (int i = 0; i < numberOfOutputNeurons; i++) {
            for (int j = 0; j < outputNeurons[0].length; j++) {
                sums[i] += outputNeurons[i][j] * weights[i][j];
            }
        }
//        double suma = weights[0] * 0 + weights[1] * zeroClass[i].x + weights[2] * zeroClass[i].y;
//        double error = 0.0;

    }
    
    public void activationFunction() {
        for (int i = 0; i < numberOfOutputNeurons; i++) {
            outputOfOutputLayer[i] = 1 / (1 + Math.pow(Math.E, -sums[i]));
        }
    }
    
    public double[] fire(double momentum){
        if (momentum != 0 ){
            for (int i = 0; i < outputOfOutputLayer.length; i++) {
            this.outputOfOutputLayer[i] *= momentum;
            }
        }
        return this.outputOfOutputLayer;
    }
    
    public void setDesiredOutput(double [] desiredOutput)throws WrongAmountOfInputValuesException {
        

        if (desiredOutput.length != this.desiredOutput.length) {
            throw new WrongAmountOfInputValuesException(desiredOutput.length);
        }else{
            this.desiredOutput = new double[desiredOutput.length];
            
            System.arraycopy(desiredOutput, 0, this.desiredOutput, 0, desiredOutput.length);
        }
    }
    
    public void countError(){
      
        for (int i = 0; i < outputOfOutputLayer.length; i++) {
            this.outputError[i] = 0.5 * Math.pow(this.desiredOutput[i] - outputOfOutputLayer[i], 2);
        }
    }
    
    public double countTotalError(){
        this.totalError = 0 ;
        
        for (int i = 0; i < outputError.length; i++) {
            this.totalError += outputError[i] ; 
        }
        //System.out.println(totalError);
        return this.totalError;
    }
    
    public void countErrorWithRespectoToOutput(double [][] weights, int numberOfHiddenNeurons ){
         eTotalOut = new double [this.numberOfOutputNeurons] ;
         derivativeOfLogisticFunction = new double [this.numberOfOutputNeurons] ;
          //double [] partialDerivativeWeight = new double [weights.length] ;
          double [][] partialDerivativeWeight = new double [outputOfOutputLayer.length ] [numberOfHiddenNeurons];
          eTotalWeight = new double [outputOfOutputLayer.length ] [numberOfHiddenNeurons];
         
       errorTotalOut(eTotalOut);
       derivativeOfLogisticFunction(derivativeOfLogisticFunction);
       partialDerivativeWeight(weights, numberOfHiddenNeurons,partialDerivativeWeight   );
       prepareForNewWeights(eTotalOut,derivativeOfLogisticFunction, partialDerivativeWeight , eTotalWeight) ;
    }
    
    
    private void errorTotalOut(double [] eTotalOut){
       
        // e respect to out
        for (int i = 0; i < outputOfOutputLayer.length; i++) {
            eTotalOut[i] = outputOfOutputLayer[i] - this.desiredOutput[i] ;
        }
    }
    
    private void derivativeOfLogisticFunction(double [] derivativeOfLogisticFunction){
        
        for (int i = 0; i < outputOfOutputLayer.length; i++) {
            
            derivativeOfLogisticFunction[i] = 1 / ( 1 +  Math.pow(Math.E, -this.sums[i]) ) ;
            derivativeOfLogisticFunction[i] = derivativeOfLogisticFunction[i] * (1 - derivativeOfLogisticFunction[i] );
        }
    }
    
    private void partialDerivativeWeight(double [][] weights, int numberOfHiddenNeurons, double [][] partialDerivativeWeight){
       
        for (int i = 0; i < this.outputOfOutputLayer.length ; i++) {
            for (int j = 0; j < numberOfHiddenNeurons; j++) {
                partialDerivativeWeight[i][j] =   this.outputFromHiddenLayer[j] * Math.pow(this.weights[i][j], 1-1) ;
                
            }
        }
            
           
    }
    
    
    private void prepareForNewWeights(double [] eTotalOut, double [] derivativeOfLogisticFunction,double [][] partialDerivativeWeight, double [][] eTotalWeight  ){
        for (int i = 0; i < eTotalWeight.length; i++) {
            for (int j = 0; j < eTotalWeight[0].length; j++) {
                eTotalWeight[i][j] = eTotalOut [i] * derivativeOfLogisticFunction[i] * partialDerivativeWeight[i][j];
            }
        }
    }
    
    public double [][] newWeights( double learningRate){
        for (int i = 0; i < this.weights.length; i++) {
            for (int j = 0; j < this.weights[0].length; j++) {
               this.weights[i][j] =  this.weights[i][j] - learningRate * eTotalWeight[i][j] ;
                //System.out.println(this.weights[i][j]);
            }
        }
        return this.weights;
    }
    
    public double [] getEtotalOut(){
        return this.eTotalOut;
    }
    
    public double [] getDerivativeOfLogisticFunction(){
        return this.derivativeOfLogisticFunction;
    }
    
    public double [] [] getWeightsOutputLayer(){
        return this.weights;
    }
    
    public void printWeights(){
        for (int i = 0; i < this.weights.length; i++) {
            for (int j = 0; j <  this.weights[0].length; j++) {
                System.out.println("nowa waga: " + this.weights[i][j]);
            }
        }
    }
    
    
    public void printTotalError(){
        System.out.println(this.totalError);
    }
    
    
    
    public void printErrors(){
        for (int i = 0; i < outputError.length; i++) {
            System.out.println(outputError[i]);
        }
    }

    public void printHiddenLayer() {
        for (int n = 0; n < numberOfOutputNeurons; n++) {
            for (int i = 0; i < weights[0].length; i++) {
                System.out.println(this.weights[n][i]);
            }
            System.out.println("kolejny neuron");
        }
    }

    public void printSums() {
        for (double sum : sums) {
            System.out.println(sum);
        }
    }
    
    
    
    // zmiana wag input-hidden
    private void errorRespectToNet(){
        this.errorRespectToSum = new double [this.numberOfOutputNeurons];
        
        for (int i = 0; i < errorRespectToSum.length; i++) {
            errorRespectToSum[i] = this.eTotalOut[i] * this.derivativeOfLogisticFunction[i];
        }
    }
    
    private void errorOutHidden(){
         this.errorOutHidden = new double [this.weights.length]  [this.weights[0].length];
       
        for (int i = 0; i < this.weights.length; i++) {
            
            for (int j = 0; j < this.weights[0].length; j++) {
                this.errorOutHidden[i][j] = this.errorRespectToSum[i] * this.weights[i][j];
                
            }
        }
    }
    
    private void eTotalOutHidden(int numberOfHiddenNeurons){
        this.eTotalOutHidden =  new double [numberOfHiddenNeurons] ;
        for (int i = 0; i < this.errorOutHidden[0].length; i++) {
            for (int j = 0; j < this.errorOutHidden.length; j++) {
                eTotalOutHidden[i] += errorOutHidden[j][i];
            }
        }
    }
    
    
    private void outH1netH1(){
        this.outH1NetH1 = new double [outputFromHiddenLayer.length];
        for (int i = 0; i < outputFromHiddenLayer.length; i++) {
           outH1NetH1[i] =  outputFromHiddenLayer[i] * (1 - outputFromHiddenLayer[i]);
        }
        
    }
    
    private void eTotalW(double [] input, int numberOfHiddenNeurons){
        
        this.eTotalW  =new double [numberOfHiddenNeurons][input.length];
        
        
        for (int i = 0; i < eTotalW.length; i++) {
            for (int j = 0; j < eTotalW[0].length; j++) {
                this.eTotalW[i][j] = this.eTotalOutHidden[i] * outH1NetH1[i] * input[j];
            }
               
        }
        
    }
    
    public void prepareForNewWeightsOfHiddenLayer(double [] input,int numberOfHiddenNeurons){
        errorRespectToNet();
        errorOutHidden();
        eTotalOutHidden(numberOfHiddenNeurons);
        outH1netH1();
        eTotalW(input, numberOfHiddenNeurons);
    }
    
    public double [][] newWeightForHiddenLayer(double [][] weights, double learningRate){
        double [][] newWeightsForHidden = new double [weights.length] [weights[0].length];
        
        for (int i = 0; i < newWeightsForHidden.length; i++) {
            for (int j = 0; j < newWeightsForHidden[0].length; j++) {
                newWeightsForHidden[i][j] =  weights[i][j] -  learningRate * this.eTotalW[i][j];
            }
        }
        
        return newWeightsForHidden;
    }
    
}
