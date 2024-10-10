package com.example.javawebtry;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

//@Named allows you to access the bean by using bean's name with firt letter in lowercase
//@RequestScoped creates a bean instance for a single HTTP request
//@SessionScoped scopes a single bean definition to the lifecycle of HTTPSession (client opens and
//closes web browser)
@Named
@SessionScoped
public class FirstComponent implements Serializable {

    //FIELD INJECTION
//    @Inject
//    private SecondComponent secondComponent;


    //CONSTRUCTOR INJECTION
    private SecondComponent secondComponent;

    @Inject
    private FirstComponent(SecondComponent secondComponent){
        this.secondComponent = secondComponent;
    }

    public String sayHello(){
        return "Hello" + new Date() + " " + toString() + " --------- " + secondComponent.getClass().getName();
    }

    @PostConstruct
    public void init(){
        System.out.println(toString() + "constructed");
    }

    @PreDestroy
    public void aboutToDie(){
        System.out.println(toString() + "ready to die");
    }
}
