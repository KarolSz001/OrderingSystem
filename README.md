"# OrderingSystem" 
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)

## General info
Multi-Module Maven Application with Java Modules , http.HttpClient connection ,still in progress adding new functionality

## Technologies
* Java - version 12;
* maven - version 3.6;
* Multi-Module Maven;
* Hibernate
* JPQL

## Setup
download, compile and run, in module main file to compile main-1.0-SNAPSHOT-jar-with-dependencies.jar

## Code Examples

  public String establishAsyncConnection(Long idNumber) 
  {
  
         String name;
         
         try {
             final String nbpPath = "https://tvjan-tvmaze-v1.p.rapidapi.com/people/" + idNumber;
             System.out.println("\n------------------LOADING--------------------");
             
             CompletableFuture<HttpResponse<String>> response1 = HttpClient
                     .newBuilder()
                     .proxy(ProxySelector.getDefault())
                     .build()
                     .sendAsync(requestGet(nbpPath), HttpResponse.BodyHandlers.ofString());
                     
             Gson gson = new GsonBuilder().setPrettyPrinting().create();
             Actor actor = gson.fromJson(response1.get().body(), Actor.class);
             name = actor.getName();
         } catch (Exception e) {
             throw new MyAppException("Error in HttpConnectionName establishConnection");
         }
         return name;
     }

## Features

To-do list:
-  add test



## Status
Project is: _in_progress_