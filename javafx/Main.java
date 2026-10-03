package javafx;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import business_logic.MP3Player;
import business_logic.PlaylistManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
	MP3Player by Alex Müller and Erina Daraz
*/

public class Main extends Application {

	Stage primaryStage;
    Map<Scenes, Pane> scenes = new HashMap<Scenes, Pane>();
	public enum Scenes {PlayerScene,PlaylistScene, SettingsScene};
	String cssPath;

	private MP3Player player;
	private PlaylistManager playlistManager;

    public void init() {

		try {
			File file = new File("./src/assets/settings.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();

			NodeList nList = document.getElementsByTagName("settings");

			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;

			if (eElement.getElementsByTagName("theme").item(0).getTextContent().equals("bright"))
				cssPath = "../assets/appwhite.css";
			else
				cssPath = "../assets/appblack.css";

			this.playlistManager = new PlaylistManager((eElement.getElementsByTagName("filepath").item(0).getTextContent().equals("")) ? "./Music/playlists/" : eElement.getElementsByTagName("filepath").item(0).getTextContent());
		} catch (ParserConfigurationException | IOException | SAXException e) {
			this.playlistManager = new PlaylistManager("./Music/playlists/");
			cssPath = "../assets/appblack.css";
			e.printStackTrace();
		}


		playlistManager.selectPlaylist(0);
		this.player = new MP3Player(playlistManager);
    }

    public void start(Stage primaryStage) {
        try {

			PlayerViewController playerViewController = new PlayerViewController(this, player, playlistManager);
			scenes.put(Scenes.PlayerScene, playerViewController.getRoot());

			PlaylistViewController playlistViewController = new PlaylistViewController(this, player, playlistManager);
			scenes.put(Scenes.PlaylistScene, playlistViewController.getRoot());

			SettingsViewController settingsViewController = new SettingsViewController(this, player, playlistManager);
			scenes.put(Scenes.SettingsScene, settingsViewController.getRoot());

			Pane root = scenes.get(Scenes.PlayerScene);

			Scene scene = new Scene(root, 520, 600);
			scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

			this.primaryStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.setTitle("MP3-Player");
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);
    }

	public void switchView(Scenes scene) {
		if(scenes.containsKey(scene)) {
			primaryStage.getScene().setRoot(scenes.get(scene));
		}
	}
}
