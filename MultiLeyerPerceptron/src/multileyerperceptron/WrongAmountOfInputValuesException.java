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
public class WrongAmountOfInputValuesException extends Exception {
    int amount;
    
    public WrongAmountOfInputValuesException(int amount){
        this.amount = amount;
    }
    
    String printBadInputValuesException(){
        return "Złapałem wyjątek! liczba wprowadzonych wartosci nie jest rowna ilosci neuronów" ;
    }
}
