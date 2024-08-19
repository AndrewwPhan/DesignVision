package com.cv.designvision.utils;

public class ColourNamer {

    private static final String DEFAULT_COLOURS_CSV =
            "src/main/resources/configFiles/colourNames.csv";

    private final IColourMap colours;
    private int actualValue;

    public ColourNamer(){
        this.colours = new ColourMap(DEFAULT_COLOURS_CSV);
    }

    public ColourNamer(IColourMap colours) {
        this.colours = colours;
    }

    /**
     * Gets closest colour name in map
     * @param colour Colour code to name.
     * @return Name of closest colour.
     */
    public String getName(int colour) {
        return colours.getColours().get(closestKey(colour));
    }

    /**
     * Algorithm to find the closest key in map (un-optimised)
     * @param target Colour as integer to match
     * @return Key of closest colour
     */
    private int closestKey(int target) {
        actualValue = target;
        if (colours.getColours().containsKey(target))
            return target;

        int minDistance = Integer.MAX_VALUE;
        int closestKey = 0;
        for(int key : colours.getColours().keySet()){
            int distance = DistanceEuclidean(target, key);
            if (distance < minDistance) {
                minDistance = distance;
                closestKey = key;
                if (distance == 0) break;
            }
        }
        actualValue = closestKey;
        return closestKey;
    }

    /**
     * Manhattan Distance between two colours.
     * @param a First colour.
     * @param b Second colour.
     * @return Difference between colour a and b.
     */
    private static int DistanceManhattan(int a, int b) {
        int sum = 0;
        for (int i = 0; i < 3*8; i+=8) {
            sum += Math.abs((a >> i & 0xFF) - (b >> i & 0xFF));
        }
        return sum;
    }

    /**
     * Euclidean Distance between two colours.
     * @param a First colour.
     * @param b Second colour.
     * @return Difference between colour a and b.
     */
    private static int DistanceEuclidean(int a, int b) {
        double sum = 0;
        for (int i = 0; i < 3*8; i+=8) {
            sum += Math.pow((a >> i & 0xFF) - (b >> i & 0xFF), 2);
        }
        return (int)Math.sqrt(sum);
    }

    public int getValue() {
        return actualValue;
    }
}
