package com.app.main;


import com.app.service.ControlService;


public class App {

    public static void main(String[] args) {


        ControlService controlService =  new ControlService();
        controlService.controlRun();
    }
}
