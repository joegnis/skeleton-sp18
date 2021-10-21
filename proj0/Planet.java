import java.util.Arrays;

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double GRAV_CONST = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet other) {
        double dx = xxPos - other.xxPos;
        double dy = yyPos - other.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet other) {
        double distance = calcDistance(other);
        return (GRAV_CONST * mass * other.mass) / (distance * distance);
    }

    public double calcForceExertedByX(Planet other) {
        return (calcForceExertedBy(other) * (other.xxPos - xxPos)) / calcDistance(other);
    }

    public double calcForceExertedByY(Planet other) {
        return (calcForceExertedBy(other) * (other.yyPos - yyPos)) / calcDistance(other);
    }

    public double calcNetForceExertedByX(Planet[] others) {
        return Arrays.stream(others)
                .filter(planet -> xxPos != planet.xxPos || yyPos != planet.yyPos)
                .mapToDouble(this::calcForceExertedByX)
                .sum();
    }

    public double calcNetForceExertedByY(Planet[] others) {
        return Arrays.stream(others)
                .filter(planet -> xxPos != planet.xxPos || yyPos != planet.yyPos)
                .mapToDouble(this::calcForceExertedByY)
                .sum();
    }

    public void update(double dt, double fX, double fY) {
        double accelX = fX / mass;
        double accelY = fY / mass;
        xxVel = xxVel + dt * accelX;
        yyVel = yyVel + dt * accelY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
