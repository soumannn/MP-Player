package business_logic;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Playlist {
    private String name;
    private ArrayList<Track> tracks= new ArrayList<Track>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addTrack(String path) {
        File filePath = new File(path);

        if(filePath.isDirectory()) {
            File[] filesList = filePath.listFiles();
            Mp3File track;
            
            for (int i = 0; i < filesList.length; i++) {
                try {
                    track = new Mp3File(filesList[i]);
                    tracks.add(new Track(track, filesList[i].toString()));
                } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                    System.out.println("File " + filesList[i].getName() + " is invalid!");
                }
            }
        } else if(filePath.isFile()) {
            try {
                Mp3File track = new Mp3File(filePath);
                tracks.add(new Track(track, path));
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                System.out.println("File " + filePath.toString() + " is invalid!");
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
