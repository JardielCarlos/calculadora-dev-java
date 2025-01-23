package com.calculadora.udemy.calculadora.math;

public class SimpleMath {
  public Double sum(Double numberOne, Double numberTwo){
    return numberOne + numberTwo;
  }

  public Double subtracao(Double numberOne, Double numberTwo){
    return numberOne - numberTwo;
  }

  public Double multiplicacao(Double numberOne, Double numberTwo){
    return numberOne * numberTwo;
  }

  public Double divisao(Double numberOne, Double numberTwo){
    return numberOne / numberTwo;
  }

  public Double media(Double numberOne, Double numberTwo) {
    return (numberOne + numberTwo) / 2;
  }

  public Double raizQuadrada(Double number) {
    return Math.sqrt(number);
  }
}
