package pt.iade.ManageeMHome.controllers;

import javafx.fxml.FXML;
import pt.iade.ManageeMHome.Main;

public class AddParentController {

	@FXML
	public void addButtonOnClick() {

		Main.plusStage.close();
	}
	@FXML
	public void cancelButtonOnClick() {
	
		Main.plusStage.close();
	
	}	
}
