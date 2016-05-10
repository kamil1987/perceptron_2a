/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multileyerperceptron;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamil
 */
public class MultiLeyerPerceptron {

    static int NUMBER_OF_INPUT_NEURONS = 4;// przy zmianie, zmien ilosc wartosci w tablicy inputValues
    static int NUMBER_OF_HIDDEN_NEURONS = 2;
    static int NUMBER_OF_OUTPUT_NEURONS = 4; // nalezy rowniez zmienic desiredValues
    static double LEARNING_RATE = 0.9;
    static double MOMENTUM =  0.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        double[] inputValues = {1, 0, 0, 0};
        double[] desiredValues = {1, 0, 0, 0};
        int ephocCaounter = 0 ;

        Input input = new Input(NUMBER_OF_INPUT_NEURONS);
        HiddenLayer hiddenLayer = new HiddenLayer(NUMBER_OF_HIDDEN_NEURONS, inputValues);
        hiddenLayer.initializeWeights();
        OutputLayer outputLayer = new OutputLayer (NUMBER_OF_OUTPUT_NEURONS, NUMBER_OF_HIDDEN_NEURONS );
        outputLayer.initializeWeights();
        double totalError = 0 ;

        do {
            try {
                input.setInputValues(inputValues);
            } catch (WrongAmountOfInputValuesException e) {
                //dla okienkowego zrobic jakies wyskakujace okienko
                e.printBadInputValuesException();
            }

            hiddenLayer.getOutputFromInputLayer(input.fire());

            hiddenLayer.summarize();
            hiddenLayer.activationFunction();
             
           
            
            outputLayer.getOutputFromHiddenLayer(hiddenLayer.fire(MOMENTUM));
            outputLayer.summarize();
            outputLayer.activationFunction();
            
            
            try {
                outputLayer.setDesiredOutput(desiredValues);
            } catch (WrongAmountOfInputValuesException ex) {
                Logger.getLogger(MultiLeyerPerceptron.class.getName()).log(Level.SEVERE, null, ex);
            }

            outputLayer.countError();
             totalError =  outputLayer.countTotalError();

            
         outputLayer.countErrorWithRespectoToOutput(hiddenLayer.getWeights(), NUMBER_OF_HIDDEN_NEURONS);
           
           
          outputLayer.newWeights(LEARNING_RATE);
              System.out.println("nowe");
              
              
              
              outputLayer.prepareForNewWeightsOfHiddenLayer(inputValues, NUMBER_OF_HIDDEN_NEURONS);
               hiddenLayer.setWeights( outputLayer.newWeightForHiddenLayer(hiddenLayer.getWeights(), LEARNING_RATE ));
            
                 System.out.println("wynik koncowy");
                 outputLayer.printTotalError();
        for (double inputValue : outputLayer.fire(MOMENTUM)) {
            System.out.println("fire " + inputValue);
        }
        
        
            ephocCaounter++;
            System.out.println("eopka: " + ephocCaounter);
        } while (ephocCaounter < 10000  && totalError > 0.01) ;
        
    
    }

}
