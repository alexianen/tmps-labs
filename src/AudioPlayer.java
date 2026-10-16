import java.util.List;

public class AudioPlayer {
    private AudioSource source;
    private AudioQuality quality;
    private List<Media> playlist;   // the numbered list
    private int current = 0;        // which item we're on

    public AudioPlayer(AudioSource source, AudioQuality quality, List<Media> playlist) {
        this.source = source;
        this.quality = quality;
        this.playlist = playlist;
    }

    public void playMedia() {
        source.play(playlist.get(current), quality);
    }

    public void pauseMedia() {
        source.pause();
    }

    public void goForward() {
        if (current < playlist.size() - 1) {
            current++;
            playMedia();
        }
    }

    public void goBackward() {
        if (current > 0) {
            current--;
            playMedia();
        }
    }
}