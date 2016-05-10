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
public class Input {

    //private double[] inputNeurons ;
    private double inputValues[] ;

    public Input(int numberOfInputNeurons) {
        this.inputValues = new double[numberOfInputNeurons];
    }

    public void setInputValues(double[] inputValues) throws WrongAmountOfInputValuesException {
        //inputValues musi posiadac tyle wartosci ile jest neuronow

        if (inputValues.length != this.inputValues.length) {
            throw new WrongAmountOfInputValuesException(inputValues.length);
        }else{
            this.inputValues = new double[inputValues.length];
            
            System.arraycopy(inputValues, 0, this.inputValues, 0, inputValues.length);
        }

    }
    public double[] fire(){
        return this.inputValues;
    }
    
     

//    private void WrongAmountOfInputValuesException(int length) {
//        throw new WrongAmountOfInputValuesException(length); //To change body of generated methods, choose Tools | Templates.
//    }
}
