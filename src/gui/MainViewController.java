package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartmentService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml",x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		try {
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			
			//pegar to Pane no content e os converte em Vbox, ate pq dentro do content tem um VBox
			VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			//Pega os filhos do VBox
			Node mainMenu = mainVBox.getChildren().get(0);
			//limpa os filhos do VBox
			mainVBox.getChildren().clear();
			//Adiciona o menu no vbox
			mainVBox.getChildren().add(mainMenu);
			//adiciona todos os menus que estão na nova tela, fazendo abrir dentro da principal
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//excutando a função Consumer
			//implementação feita para carregar todos os dados de departmant sem dois methods
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", null, "Error loading view", AlertType.ERROR);
		}
	}

}
