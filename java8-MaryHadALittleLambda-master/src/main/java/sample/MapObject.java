package sample;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class MapObject extends SpriteView {
    public static class Barn extends MapObject {
        // Image by LovelyBlue: http://l0velyblue.deviantart.com/art/barncharset-350737104
        static final Image BARN = loadImage("images/barn.png");
        public Barn(Main.Location loc) {
            super(BARN, loc);
        }
        @Override
        public void visit(Shepherd s) {
            SpriteView tail = s.getAnimals().isEmpty() ?
                s : s.getAnimals().get(s.getAnimals().size() - 1);

            Stream.iterate(tail, SpriteView.Lamb::new)
                .skip(1).limit(7)
                .forEach(s.getAnimals()::add);
        }
    }

    public static class Rainbow extends MapObject {
        static final Image RAINBOW = loadImage("images/rainbow.png");
        public Rainbow(Main.Location loc) {
            super(RAINBOW, loc);
            startAnimation();
        }
        @Override
        public void visit(Shepherd s) {
            s.getAnimals().stream()
                .filter(a -> a.getNumber() % 4 == 1)
                .forEach(a -> a.setColor(null));
            s.getAnimals().stream()
                .filter(a -> a.getNumber() % 4 == 2)
                .forEach(a -> a.setColor(Color.YELLOW));
            s.getAnimals().stream()
                .filter(a -> a.getNumber() % 4 == 3)
                .forEach(a -> a.setColor(Color.CYAN));
            s.getAnimals().stream()
                .filter(a -> a.getNumber() % 4 == 0)
                .forEach(a -> a.setColor(Color.GREEN));
        }
    }

    public static class Church extends MapObject {
        // Image by LovelyBlue: http://l0velyblue.deviantart.com/art/Church-350736943
        static final Image CHURCH = loadImage("images/church.png");
        LongProperty mealsServed = new SimpleLongProperty();
        public Church(Main.Location loc) {
            super(CHURCH, loc);
            Label label = new Label();
            label.textProperty().bind(mealsServed.asString());
            label.setFont(Font.font("Impact", 12 * Main.SCALE));
            label.setTranslateX(-8 * Main.SCALE);
            label.setTranslateY(3 * Main.SCALE);
            getChildren().add(label);
        }
        @Override
        public void visit(Shepherd s) {
            Predicate<SpriteView> pure =
                a -> a.getColor() == null;

            mealsServed.set(mealsServed.get() +
                s.getAnimals().filtered(pure).size()
            );

            s.getAnimals().removeIf(pure);
        }
    }

    public static class ChickenCoop extends MapObject {
        // Image by LovelyBlue: http://l0velyblue.deviantart.com/art/chickencoop-350736803
        static final Image CHICKEN_COOP = loadImage("images/chicken-coop.png");
        public ChickenCoop(Main.Location loc) {
            super(CHICKEN_COOP, loc);
        }
        @Override
        public void visit(Shepherd s) {
            // single map:
//            s.getAnimals().setAll(s.getAnimals()
//                .stream()
//                .map(sv -> new Eggs(sv.getFollowing())
//            ).collect(Collectors.toList()));
            // or a double map:
            s.getAnimals().setAll(s.getAnimals()
                .stream().parallel()
                .map(SpriteView::getFollowing)
                .map(Eggs::new)
                .collect(Collectors.toList())
            );
        }
    }

    public static class Nest extends MapObject {
        // Image derived from Lokilech's Amselnest: http://commons.wikimedia.org/wiki/File:Amselnest_lokilech.jpg
        static final Image NEST = loadImage("images/nest.png");
        public Nest(Main.Location loc) {
            super(NEST, loc);
        }
        @Override
        public void visit(Shepherd s) {
            s.getAnimals().setAll(s.getAnimals()
                .stream().parallel()
                .flatMap(SpriteView.Eggs::hatch)
                .collect(Collectors.toList())
            );
        }
    }

    public static class Fox extends MapObject {
        // Image by PinedaVX: http://www.rpgmakervx.net/index.php?showtopic=9422
        static final Image FOX = loadImage("images/fox.png");
        public  Fox(Main.Location loc) {
            super(FOX, loc);
            startAnimation();
        }
        @Override
        public void visit(Shepherd shepherd) {
            Double mealSize = shepherd.getAnimals()
                .stream()
                .map(SpriteView::getScaleX)
                .reduce(0.0, Double::sum);

            setScaleX(getScaleX() + mealSize * .2);
            setScaleY(getScaleY() + mealSize * .2);
            shepherd.getAnimals().clear();
        }
    }

    public MapObject(Image spriteSheet, Main.Location loc) {
        super(spriteSheet, loc);
        Main.map[loc.getX()][loc.getY()] = this;
    }
    public abstract void visit(Shepherd shepherd);
}
