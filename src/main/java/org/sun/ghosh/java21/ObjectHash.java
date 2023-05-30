/**
 * https://inside.java/2023/05/28/sip078/
 * https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/Objects.html
 * https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/System.html#identityHashCode(java.lang.Object)
 * GitHub Pull
 * https://github.com/openjdk/jdk/commit/cbe8395ace3230dc599c7f082e3524a861b2da8e
 */

package org.sun.ghosh.java21;

import java.util.Objects;

import java.lang.StringTemplate;

/*
STR is statically imported implicitly into every Java compilation unit.
Unlike STR, RAW must be statically imported explicitly.
*/
import static java.lang.StringTemplate.RAW;

public class ObjectHash {

    record Building(String name,
                    String type,
                    int floors) {

        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass())
                return false;
            Building building = (Building) object;
            return java.util.Objects.equals(name, building.name) &&
                    java.util.Objects.equals(type, building.type);
        }

        public int hashCode() {
            return Objects.hash(name, type);
        }
    }

    public static void main(String[] args) {
        Building myBuilding = new Building("Diamond", "Apartments", 5);

        // RAW is a standard template processor that
        // produces an unprocessed StringTemplate object
        StringTemplate st = RAW. """
                My Building
                   String - \{myBuilding.toString()}
                   Overidden Hash Code - \{myBuilding.hashCode()}
                   System Hash Code - \{Objects.toIdentityString(myBuilding)}
                """;

        String buildingInfo;
        buildingInfo = STR.process(st);
        System.out.println(buildingInfo);

        Building myNeighbouringBuilding = new Building("Golden", "Apartments", 5);

        String buildingFormat = """
                My Building
                   String - %s
                   Overidden Hash Code - %s
                   System Hash Code - %s
                """;
        System.out.println(String.format(buildingFormat,
                myNeighbouringBuilding.toString(),
                myNeighbouringBuilding.hashCode(),
                Objects.toIdentityString(myNeighbouringBuilding)));

        Building myAnotherNeighbourBuilding = new Building("Golden", "Apartments", 5);

        System.out.println(String.format(buildingFormat,
                myAnotherNeighbourBuilding.toString(),
                myAnotherNeighbourBuilding.hashCode(),
                Objects.toIdentityString(myAnotherNeighbourBuilding)));
    }


}