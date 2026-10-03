package javafx;

import business_logic.MP3Player;
import business_logic.PlaylistManager;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsViewController {

    private Main application;
    private Pane root;

    MP3Player player;
    PlaylistManager playlistManager;

    @FXML
    public Button goBack;
    @FXML
    public CheckBox darkTheme;
    @FXML
    public CheckBox brightTheme;
    @FXML
    public TextField musicDirectory;
    @FXML
    public Button saveButton;
    @FXML
    public Button browseButton;


    public SettingsViewController(Main application, MP3Player player, PlaylistManager playlistManager) {
        this.application = application;
        this.player = player;
        this.playlistManager = playlistManager;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SettingsView.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        goBack.getStyleClass().add("goBack");
        saveButton.getStyleClass().add("goBack");

        try {
            File file = new File("src/assets/settings.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("settings");

            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;

            musicDirectory.setText(eElement.getElementsByTagName("filepath").item(0).getTextContent());

            if(eElement.getElementsByTagName("theme").item(0).getTextContent().equals("dark")) {
                darkTheme.setSelected(true);

            } else if(eElement.getElementsByTagName("theme").item(0).getTextContent().equals("bright")) {
                brightTheme.setSelected(true);

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        goBack.addEventHandler(ActionEvent.ACTION,
                event -> application.switchView(Main.Scenes.PlayerScene)
        );

        saveButton.addEventHandler(ActionEvent.ACTION,
                event -> {
                    playlistManager.setFilePath(musicDirectory.getText());

                    try {

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document xmlDoc = docBuilder.newDocument();

                        Element rootElement = xmlDoc.createElement("userSettings");
                        Element mainElement = xmlDoc.createElement("settings");
                        Element theme = xmlDoc.createElement("theme");
                        Element filepath = xmlDoc.createElement("filepath");

                        if (!darkTheme.isSelected() && brightTheme.isSelected())
                            theme.appendChild(xmlDoc.createTextNode("bright"));
                        else if (darkTheme.isSelected() && !brightTheme.isSelected())
                            theme.appendChild(xmlDoc.createTextNode("dark"));
                        filepath.appendChild(xmlDoc.createTextNode(musicDirectory.getText()));

                        xmlDoc.appendChild(rootElement);
                        rootElement.appendChild(mainElement);
                        mainElement.appendChild(theme);
                        mainElement.appendChild(filepath);

                        OutputFormat outFormat = new OutputFormat(xmlDoc);
                        File xmlFile = new File("./src/assets/settings.xml");
                        FileOutputStream outStream = new FileOutputStream(xmlFile);

                        XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
                        serializer.serialize(xmlDoc);

                    } catch (ParserConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        browseButton.addEventHandler(ActionEvent.ACTION,
                    event -> {
                        final DirectoryChooser directoryChooser = new DirectoryChooser();
                        final File selectedDirectory = directoryChooser.showDialog(application.primaryStage);
                        if (selectedDirectory != null) {
                            musicDirectory.setText(selectedDirectory.getAbsolutePath());
                    }
                }
        );
    }

    public Pane getRoot() {
        return root;
    }
}
