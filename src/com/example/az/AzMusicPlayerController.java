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
 * �~���[�W�b�N�v���[���[�̃R���g���[���[�N���X�ł�
 * 
 * @author Azumi Hirata
 *
 */
public class AzMusicPlayerController implements Initializable {
    
    /**
     * �Ȗ����ꗗ�\������ListView
     */
    @FXML
    private ListView<String> listView;
    
    /**
     * �~���[�W�b�N�t�@�C�������s���邽�߂�MediaView
     */
    @FXML
    private MediaView mediaView;
    
    /**
     * Play�{�^��
     */
    @FXML
    private ToggleButton btnPlay;
    
    /**
     * �^�C�g���\�����x��
     */
    @FXML
    private Label lblMusicTitle;
    
    /**
     * Play�{�^�����������ꂽ���̏����ł�
     * 
     * @param event�@�C�x���g
     */
    @FXML
    public void btnPlay_OnAction(ActionEvent event) {
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        if (btnPlay.isSelected()) { // Play�{�^������������Ă���i�Đ��O�j�ꍇ
            // �Đ��I����Ƀ{�^�����߂�悤�ɂ���
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    btnPlay.setSelected(false);
                }
            });
            mediaPlayer.play();
        } else { // Play�{�^������������Ă��Ȃ��i�Đ����j�̏ꍇ
            mediaPlayer.pause();
        }
    }
    
    /**
     * ���փ{�^�����������ꂽ���̏����ł�
     * 
     * @param event �C�x���g
     */
    @FXML
    public void btnNext_OnAction(ActionEvent event) {
        // ListView�̉��̍s��I������
        int itemCount = listView.getItems().size();
        int index = listView.getSelectionModel().getSelectedIndex();
        int newIndex = Integer.min(itemCount, index + 1);
        listView.getSelectionModel().select(newIndex);
        
        // �I������Ă���Ȗ����擾����
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // �Ȗ����烁�f�B�A�t�@�C����ǂݍ���
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // �Đ����E�ꎞ��~�E���̑��ŏꍇ����
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
     * �O�փ{�^�����������ꂽ���̏����ł�
     * 
     * @param event �C�x���g
     */
    @FXML
    public void btnBack_OnAction(ActionEvent event) {
        // ListView�̏�̍s��I������
        int index = listView.getSelectionModel().getSelectedIndex();
        int newIndex = Integer.max(0, index - 1);
        listView.getSelectionModel().select(newIndex);
        
        // �I������Ă���Ȗ����擾����
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // �Ȗ����烁�f�B�A�t�@�C����ǂݍ���
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // �Đ����E�ꎞ��~�E���̑��ŏꍇ����
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
     * ListView���N���b�N�����Ƃ��ɌĂяo�����
     * 
     * @param event
     */
    @FXML
    public void listView_OnMouseClicked(MouseEvent event) {
        // �I������Ă���Ȗ����擾����
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        
        // �Ȗ����烁�f�B�A�t�@�C����ǂݍ���
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        
        // �Đ����E�ꎞ��~�E���̑��ŏꍇ����
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
     * ListView�ŃL�[���������Ƃ��ɌĂяo�����
     * ���L�[������������Ƃ��̕������Ăяo����邽�߁A�I�����ڂ��E���̂ɂ͕s�K
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyPressed(KeyEvent event) {
        // �g�p���Ȃ�
    }
    
    /**
     * ListView�ŃL�[�𗣂����Ƃ������Ăяo�����
     * ���L�[��b�����������Ăяo����邽�߁A�I�����ڂ͂���ŏE��
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyReleased(KeyEvent event) {
        // �㉺���L�[�̂ݏ�������
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            // �I������Ă���Ȗ����擾����
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            lblMusicTitle.setText(selectedItem);
            
            // �Ȗ����烁�f�B�A�t�@�C����ǂݍ���
            Media media = new Media(getMusicFile(selectedItem).toUri().toString());
            MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
            
            // �Đ����E�ꎞ��~�E���̑��ŏꍇ����
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
     * ListView�ŃL�[���^�C�v�����Ƃ��ɌĂяo�����
     * ���L�[�ɂ͔������Ȃ�
     * 
     * @param event
     */
    @FXML
    public void listView_OnKeyTyped(KeyEvent event) {
        // �g�p���Ȃ�
    }
    
    /* (�� Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // �~���[�W�b�N�t�@�C���� ArrayList ���擾����
        List<Path> musicFileList = getMusicFileList();
        
        // �~���[�W�b�N�t�@�C�����Ȃ���ΏI��
        if (musicFileList == null) {
            System.out.println("�t�@�C����������܂���");
            Platform.exit();
        }
        
        // �~���[�W�b�N�t�@�C���̃^�C�g����ListView�ɒǉ�����
        for (Path path : musicFileList) {
            listView.getItems().add(path.getFileName().toString());
        }
        
        // �ŏ��̃~���[�W�b�N�t�@�C����I������
        listView.getSelectionModel().selectFirst();
        
        // MediaPlayer��MediaView���ŏ��̋Ȃŏ��������Ă���
        // MediaPlayer�͎n�߂���NOT NULL
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        lblMusicTitle.setText(selectedItem);
        Media media = new Media(getMusicFile(selectedItem).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
    
    private Path getMusicFile(String name) {
        // �~���[�W�b�N�t�@�C���� ArrayList ����^�C�g�����L�[�Ɍ�������i�K�����݂���j
        for (Path path : getMusicFileList()) {
            if (name.equals(path.getFileName().toString())) {
                return path;
            }
        }
        System.out.println("�t�@�C����������܂���");
        Platform.exit();
        return null;
    }
    
    /**
     * iTunes�̃~���[�W�b�N�t�@�C���� ArrayList ��Ԃ��܂��iWindows�j
     * 
     * @return �~���[�W�b�N�t�@�C���� ArrayList
     */
    private List<Path> getMusicFileList() {
        try {
            // iTunes�̃~���[�W�b�N�t�@�C��(.m4a)�̃��X�g
            List<Path> musicFileList = new ArrayList<>();

            // iTunes Media�t�H���_
            // Windows�̏ꍇ�͂���
            Path start = getMediaDirectory();
            
            // �t�@�C���̒T��
            // �T�u�t�H���_���܂߂ă~���[�W�b�N�t�@�C���������o��
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
     * ���s���� OS ���� iTunes Media �̃��[�g�f�B���N�g������肵�܂�
     * 
     * @return iTunes Media �̃p�X
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
