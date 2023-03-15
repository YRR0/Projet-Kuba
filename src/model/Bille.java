package model;

import javax.imageio.ImageIO;
import model.mouvement.Direction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Bille implements Cloneable, Serializable{
    private Couleur color;
    public static final int width = 50;
    private transient BufferedImage image;
    public static final int scale = 7;
    private AnimationBille animate = null;
    private int x,y;

    public void collide(Bille b, Direction d){
        switch (d){
            case NORD:
                if (animate.y - Bille.width/2 == b.animate.y + Bille.width)
                    b.createAnimation(b.x, b.y-Bille.width, 0, -1);
            break;
            case SUD:
                if (animate.y + Bille.width/2 == b.animate.y - Bille.width)
                    b.createAnimation(b.x, b.y+Bille.width, 0, 1);
            break;
            case OUEST:
                if (animate.x - Bille.width/2 == b.animate.x + Bille.width)
                    b.createAnimation(b.x-Bille.width, b.y, -1, 0);
            break;
            case EST:
                if (animate.x + Bille.width/2 == b.animate.x - Bille.width)
                    b.createAnimation(b.x+Bille.width, b.y, 1, 0);
            break;
        }
    }

    public int offset(int x){
        return x+Bille.width/2;
    }

    public void createAnimation(int d_x, int d_y, int dx, int dy){
        animate = new AnimationBille(x, y, d_x, d_y, dx, dy);
    }

    public AnimationBille getAnimation(){
        return animate;
    }

    public boolean is_animate(){
        return animate != null;
    }

    public static class AnimationBille{
        private int x;
        private int y;
        private int d_x;
        private int d_y;
        private int dx;
        private int dy;

        public AnimationBille(int x, int y, int d_x, int d_y, int dx, int dy){
            this.x = x;this.y=y;this.d_x = d_x;this.d_y=d_y;this.dx=dx;this.dy=dy;
        }

        public void move(){
            x+=dx;
            y+=dy;
        }

        public boolean is_moving(){
            return x==d_x && y==d_y;
        }
    }

    public Bille(Couleur c, int x, int y){
        this.x = offset(x);
        this.y = offset(y);
        color = c;
        String imageDesc = switch (color) {
            case NOIR -> "black";
            case BLANC -> "white";
            case ROUGE -> "red";
        };
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/" + imageDesc + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Couleur getColor() { return color; }

    public BufferedImage image() {
        return image;
    }

    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "N";
    }

    @Override
    public Object clone(){
        return new Bille(color, x, y);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(color); 
        ImageIO.write(image, "png", out); 
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = (Couleur) in.readObject();
        image = ImageIO.read(in);
    }

}
