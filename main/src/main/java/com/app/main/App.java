package com.app.main;
import com.app.service.ControlService;

public class App {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append(" ----------------------------------------------------------------------------- \n");
        sb.append(" OrderingSys v1.0 02.12.2019 \n ");
        sb.append(" Karol Szot \n");
        sb.append(" ----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());

        ControlService controlService =  new ControlService();
        controlService.controlRun();
    }
}
