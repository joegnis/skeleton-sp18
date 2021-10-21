import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class NBody {

    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int numPlanets;
        try {
            numPlanets = in.readInt();
        } catch (InputMismatchException exception) {
            throw new RuntimeException("Invalid file: Number of planets not found");
        } catch (NoSuchElementException exception) {
            throw new RuntimeException("Invalid file: empty file");
        }

        try {
            in.readDouble();
        } catch (InputMismatchException exception) {
            throw new RuntimeException("Invalid file: radius of universe not found. Found " + in.readString());
        } catch (NoSuchElementException exception) {
            throw new RuntimeException("Invalid file: radius of universe not found. File ends.");
        }

        Planet[] planets = new Planet[numPlanets];
        for (int curPlanet = 0; curPlanet < 5; curPlanet++) {
            double xPos, yPos, xVel, yVel, mass;
            String imgName;
            try {
                xPos = in.readDouble();
                yPos = in.readDouble();
                xVel = in.readDouble();
                yVel = in.readDouble();
                mass = in.readDouble();
                imgName = in.readString();
            } catch (InputMismatchException exception) {
                throw new RuntimeException("Invalid data of No." + (curPlanet + 1) + " planet.");
            } catch (NoSuchElementException exception) {
                throw new RuntimeException("Incomplete data of No." + (curPlanet + 1) + " planet.");
            }

            planets[curPlanet] = new Planet(xPos, yPos, xVel, yVel, mass, imgName);
        }

        return planets;
    }

    public static double readRadius(String path) {
        In in = new In(path);
        try {
            in.readInt();
        } catch (InputMismatchException exception) {
            throw new RuntimeException("Invalid file: Number of planets not found");
        } catch (NoSuchElementException exception) {
            throw new RuntimeException("Invalid file: empty file");
        }

        double radius;
        try {
            radius = in.readDouble();
        } catch (InputMismatchException exception) {
            throw new RuntimeException("Invalid file: radius of universe not found. Found " + in.readString());
        } catch (NoSuchElementException exception) {
            throw new RuntimeException("Invalid file: radius of universe not found. File ends.");
        }

        return radius;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException("Invalid number of arguments");
        }

        double T, dt;
        String filename;
        try {
            T = Double.parseDouble(args[0]);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("Invalid first argument T");
        }

        try {
            dt = Double.parseDouble(args[1]);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("Invalid second argument dt");
        }

        filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        StdDraw.setScale(-256, 256);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();
    }
}
