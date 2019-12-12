package pt.iade.ManageeMHome.controllers;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import pt.iade.ManageeMHome.models.Kid;
import pt.iade.ManageeMHome.models.Task;
import pt.iade.ManageeMHome.models.DAO.JDBC;
import pt.iade.ManageeMHome.models.DAO.PersonDAO;
import pt.iade.ManageeMHome.models.DAO.TaskDAO;



public class NotificationsController {


	@FXML
	private TableView<Task> notificationTV;

	@FXML
	private TableColumn<String, Task> taskColumn;

	@FXML
	private TableColumn<String, Kid> kidColumnN;

	@FXML
	private TableColumn<Boolean, Button> noColumn;

	@FXML
	private TableColumn<Boolean, Button> yesColumn;

	private ObservableList<Task> completedTasks= FXCollections.observableArrayList();

	private void findCompletedTasks() {
		int parent = 0;
		String sql ="Select User.name as Filho, Task.name as Task_Name from User, Task, Kids_Task, Family_Relation"
				+ " where Family_Relation.kid = Kids_Task.kid and Family_Relation.parent = ? and"
				+ " Task.id_Task = Kids_Task.Task AND Kids_Task.completed = true and User.id_User = Kids_Task.kid ";
			try (PreparedStatement stat = JDBC.getCon().prepareStatement(sql)){
			parent = PersonDAO.getLoggedParent().getId();
			stat.setInt(1, parent);
			
			stat.executeQuery();
			ResultSet rs = stat.executeQuery();	
			while(rs.next()) {
				completedTasks.add(new Task(rs.getString("Task_Name"),
						0, 
						null,
						false,
						new Kid(rs.getString("Filho"), 0, 0, 0, false)
						)
						);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	@FXML
	private void initialize() {

		findCompletedTasks();
		taskColumn.setCellValueFactory(new PropertyValueFactory<String, Task>("name"));
		kidColumnN.setCellValueFactory(new PropertyValueFactory<String, Kid>("kidName"));
		FXCollections.observableArrayList();
		notificationTV.setItems(completedTasks);
		noColumn.setCellFactory((tableCol)-> {
			return new TableCell<Boolean, Button> ()  {
				@Override
				protected void updateItem(Button b1, boolean empty) {
					super.updateItem(b1, empty);
					if(!empty){
						Button button = new Button("NO");
						button.setOnAction((event) -> {
							notificationTV.getSelectionModel().select(getTableRow().getIndex());
							Task  selectedItem 	=	notificationTV.getSelectionModel().getSelectedItem();
//							for(Kid kid :  selectedItem.getKidArrayList()) {
//								kid.setPoints(kid.getPoints()+selectedItem.getPoints());
//							}
							notificationTV.getItems().remove(selectedItem);
							selectedItem.setComplete(false);
						});
						setGraphic(button);
					} else  {
						setGraphic(null);

					}}

			}; 
		});
		yesColumn.setCellFactory((tableCol)-> {
			return new TableCell<Boolean, Button> ()  {
				@Override
				protected void updateItem(Button b1, boolean empty) {
					super.updateItem(b1, empty);
					if(!empty){
						Button button = new Button("YES");
						button.setOnAction((event) -> {

						});
						setGraphic(button);
					} else  {
						setGraphic(null);

					}
				}   
			}; 
		});

	}
}


