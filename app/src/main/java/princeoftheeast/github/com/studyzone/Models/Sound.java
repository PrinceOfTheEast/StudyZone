package princeoftheeast.github.com.studyzone.Models;

/**
 * Created by M Chowdhury on 05/03/2019.
 */

public class Sound {
    private String name;
    private String artist;
    private int sound;

    public Sound(String name, String artist, int sound) {
        this.name = name;
        this.artist = artist;
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getSound() {
        return sound;
    }

}
