package com.kuba.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ArrayList;

public class Son {
    Clip clip;
    float previousVolume=0;
    float currentVolume=0;
    FloatControl fc;
    public boolean mute=false;
    ArrayList<URL> sounds = new ArrayList<URL>();

    public Son() {
        try{
            sounds.add(getClass().getResource("/resources/audio/game.wav"));
            sounds.add(getClass().getResource("/resources/audio/move2.wav"));
            sounds.add(getClass().getResource("/resources/audio/move.wav"));
            sounds.add(getClass().getResource("/resources/audio/error.wav"));
        }
        catch(Exception e){
            System.out.println(" Error on loading sounds ");
            //e.printStackTrace();
        }
    }

    public void setSound(int i) {
        URL soundUrl = sounds.get(i);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            System.out.println(" Error on setting sound ");
            e.printStackTrace();
        }
        
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void playMusic(int i) {
        setSound(i);
        play();
        loop();
    }

    public void playSoundEffect(int i) {
        setSound(i);
        play();
    }

    public void stopMusic() {
        stop();
    }

    public void volumeMute(){
        if(this.clip==null){
            this.setSound(0);
        }
        if(!mute){
            previousVolume=currentVolume;
            currentVolume= 6.0f;
            fc.setValue(currentVolume);
            mute=true;
        }
        else{
            currentVolume=previousVolume;
            fc.setValue(currentVolume);
            mute=false;
        }
    }
    
}
