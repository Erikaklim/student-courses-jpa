package com.example.javawebtry.async;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculationService {

    public String performLongCalculation(){
        try{
            Thread.sleep(5000);
            System.out.println("Long calculation was successful");
            return "Long calculation was successful";

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
