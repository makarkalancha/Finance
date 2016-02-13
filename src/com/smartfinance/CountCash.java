package com.smartfinance;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CountCash extends Application{
	public static void main(String [] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Cash count");
		Group root = new Group();
		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setFullScreen(true);
	}
	
	

}
