package com.example.javawebtry.async;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@Named
@RequestScoped
public class Calculation {
    @Inject
    CalculationService calculationService;
    private CompletableFuture<String> result = null;

    public void startCalculation() {
        System.out.println("------- async: startCalculation was called");
        result = CompletableFuture.supplyAsync(() -> calculationService.performLongCalculation());
        System.out.println("------- async: supplyAsync was made");
    }

    public boolean getIsCalculationDone() throws Exception{
        System.out.println("------- async: isCalculationDone was called");
        if(result != null) {
            System.out.println("result: " + result.get());
        }
        return result != null && result.isDone();
    }
    public String getTaskResult() throws Exception {
        System.out.println("------- async: getTaskResult was called");
        return result.get();
    }

}
