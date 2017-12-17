package com.example.az;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

/**
 * ミュージックプレーヤーのコントローラークラスです
 * 
 * @author Azumi Hirata
 *
 */
public class AzMusicPlayerController implements Initializable {
    
    /**
     * 曲名を一覧表示するListView
     */
    @FXML
    private ListView<String> listView;
    
    /**
     * ミュージックファイルを実行するためのMediaView
     */
    @FXML
    private MediaView mediaView;
    
    /**
     * Playボタン
     */
    @FXML
    private ToggleButton btnPlay;
    
    /**
     * タイトル表示ラベル
     */
    @FXML
    private Label lblMusicTitle;
    
    /**
     * Playボタンが押下された時の処理です
     * 
     * @param event　イベント
     */
    @FXML
    public void btnPlay_OnAction(ActionEvent event) {
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        if (btnPlay.isSelected()) { // Playボタンが押下されている（再生前）場合
            // 再生終了後にボタンが戻るようにする
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    btnPlay.setSelected(false);
                }
            });
            mediaPlayer.play();
        } else { // Playボタンが押下されていない（再生中）の場合
            mediaPlayer.pause();
        }
    }
    
    /**
     * 次へボタンが押下された時の処理です
     * 
     * @param event イベント
     */
    @FXML
    public void btnNext_OnAction(ActionEvent event) {
        // ListViewの下の行を選択する
        int itemCount = listView.getItems().size();
        int index = listView.getSelectionModel().getSelectedIndex();
        int newIndex = Integer.min(itemCount, index + 1);
        listView.getSelectionModel().select(newIndex);
        
        // 選択されている曲名を取得する
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // 曲名からメディアファイルを読み込む
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // 再生中・一時停止・その他で場合分け
        if (mediaPlayer.getStatus() == Status.PLAYING) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() == Status.PAUSED) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.pause();
        } else {
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
    
    /**
     * 前へボタンが押下された時の処理です
     * 
     * @param event イベント
     */
    @FXML
    public void btnBack_OnAction(ActionEvent event) {
        // ListViewの上の行を選択する
        int index = listView.getSelectionModel().getSelectedIndex();
        int newIndex = Integer.max(0, index - 1);
        listView.getSelectionModel().select(newIndex);
        
        // 選択されている曲名を取得する
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // 曲名からメディアファイルを読み込む
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // 再生中・一時停止・その他で場合分け
        if (mediaPlayer.getStatus() == Status.PLAYING) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() == Status.PAUSED) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.pause();
        } else {
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
    
    /**
     * ListViewをクリックしたときに呼び出される
     * 
     * @param event
     */
    @FXML
    public void listView_OnMouseClicked(MouseEvent event) {
        // 選択されている曲名を取得する
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // 曲名からメディアファイルを読み込む
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // 再生中・一時停止・その他で場合分け
        if (mediaPlayer.getStatus() == Status.PLAYING) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() == Status.PAUSED) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.pause();
        } else {
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
    
    /**
     * ListViewでキーを押したときに呼び出される
     * 矢印キーを押し続けるとその分だけ呼び出されるため、選択項目を拾うのには不適
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyPressed(KeyEvent event) {
        // 使用しない
    }
    
    /**
     * ListViewでキーを離したときだけ呼び出される
     * 矢印キーを話した時だけ呼び出されるため、選択項目はこれで拾う
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyReleased(KeyEvent event) {
        // 上下矢印キーのみ処理する
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            // 選択されている曲名を取得する
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            lblMusicTitle.setText(selectedItem);
            
            // 曲名からメディアファイルを読み込む
            Media media = new Media(getMusicFile(selectedItem).toUri().toString());
            MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
            
            // 再生中・一時停止・その他で場合分け
            if (mediaPlayer.getStatus() == Status.PLAYING) {
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            } else if (mediaPlayer.getStatus() == Status.PAUSED) {
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.pause();
            } else {
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
            }
        }
    }
    
    /**
     * ListViewでキーをタイプしたときに呼び出される
     * 矢印キーには反応しない
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyTyped(KeyEvent event) {
        // 使用しない
    }
    
    /* (非 Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ミュージックファイルの ArrayList を取得する
        List<Path> musicFileList = getMusicFileList();
        
        // ミュージックファイルがなければ終了
        if (musicFileList == null) {
            System.out.println("ファイルが見つかりません");
            Platform.exit();
        }
        
        // ミュージックファイルのタイトルをListViewに追加する
        for (Path path : musicFileList) {
            listView.getItems().add(path.getFileName().toString());
        }
        
        // 最初のミュージックファイルを選択する
        listView.getSelectionModel().selectFirst();
        
        // MediaPlayerとMediaViewを最初の曲で初期化しておく
        // MediaPlayerは始めからNOT NULL
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
    
    private Path getMusicFile(String name) {
        // ミュージックファイルの ArrayList からタイトルをキーに検索する（必ず存在する）
        for (Path path : getMusicFileList()) {
            if (name.equals(path.getFileName().toString())) {
                return path;
            }
        }
        System.out.println("ファイルが見つかりません");
        Platform.exit();
        return null;
    }
    
    /**
     * iTunesのミュージックファイルの ArrayList を返します（Windows）
     * 
     * @return ミュージックファイルの ArrayList
     */
    private List<Path> getMusicFileList() {
        try {
            // iTunesのミュージックファイル(.m4a)のリスト
            List<Path> musicFileList = new ArrayList<>();

            // iTunes Mediaフォルダ
            // Windowsの場合はここ
            Path start = getMediaDirectory();
            
            // ファイルの探索
            // サブフォルダを含めてミュージックファイルだけ取り出す
            FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".m4a")) {
                        musicFileList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

            };

            Files.walkFileTree(start, visitor);
            
            return musicFileList;
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * 実行中の OS から iTunes Media のルートディレクトリを特定します
     * 
     * @return iTunes Media のパス
     */
    private Path getMediaDirectory() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") >= 0) {
            // Running on Windows
            return Paths.get(System.getProperty("user.home"), "Music", "iTunes", "iTunes Media");
        } else if (osName .indexOf("Mac") >= 0) {
            // Running on macOS
            throw new UnsupportedOperationException();
        } else if (osName.indexOf("Linux") >= 0) {
            // Running on Linux
            throw new UnsupportedOperationException();
        } else {
            // Running on other systems
            throw new UnsupportedOperationException();
        }
    }
}
