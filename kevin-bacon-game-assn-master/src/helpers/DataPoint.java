package helpers;

import java.util.Objects;

/**
 * Helper class to store movie titles or actor/actress names
 * in the Kevin Bacon game.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class DataPoint
{
    private String name;
    private boolean isActor;

    /**
     * Creates a new data point in the graph.
     * @param name the title of a movie or actor/actress name
     * @param isActor true if the name is for an actor, or false if for a movie
     */
    public DataPoint(String name, boolean isActor)
    {
        this.name = name;
        this.isActor = isActor;
    }

    /**
     * Getter for name.
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for actor flag.
     * @return the actor flag
     */
    public boolean isActor()
    {
        return isActor;
    }

    /**
     * This method ensures that a DataPoint object can be found
     * in the graph using the .equals() method.
     * @param other the other object to compare with
     * @return true if the objects are both DataPoints and have
     * the same name and actor flag.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
        {
            return true;
        }
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        DataPoint dataPoint = (DataPoint) other;
        return isActor == dataPoint.isActor && Objects.equals(name, dataPoint.name);
    }

    /**
     * hashCode() method to match the equals() method above.
     * @return a hash-code for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, isActor);
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "name='" + name + '\'' +
                ", isActor=" + isActor +
                '}';
    }
}
