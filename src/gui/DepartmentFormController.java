package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;

	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBTSaveAction() {
		System.out.println("Save");
	}
	@FXML
	public void onBTCancelAction() {
		System.out.println("Cancel");
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private void initealizeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

}
