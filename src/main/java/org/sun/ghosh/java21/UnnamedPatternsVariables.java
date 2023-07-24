package org.sun.ghosh.java21;

/**
 * JEP 443: Unnamed Patterns and Variables (Preview)
 * https://openjdk.org/jeps/443
 * <p>
 * Code Changes
 * https://github.com/openjdk/jdk/commit/8aa50288a1a6a853bf4d8d97b1849d5744ed7a32
 * <p>
 * JDK-8307007 Implementation for javax.lang.model for unnamed variables (Preview)
 * https://bugs.openjdk.org/browse/JDK-8307007
 * <p>
 * JDK-8294349 JEP 443: Unnamed Patterns and Variables (Preview)
 * https://bugs.openjdk.org/browse/JDK-8294349
 * <p>
 * OpenJDK JDK 21 Early-Access Builds
 * https://jdk.java.net/21/ - Build 24 (2023/5/25)
 */

public class UnnamedPatternsVariables {

    record GeoLocation(double latitude,
                       double longitude,
                       double altitude) {}

    enum BuildingType { APARTMENT, OFFICE,
                        SCHOOL, FACTORY, SHOP }

    record Building(String name,
                    GeoLocation location,
                    BuildingType type) {}

    record AppPayment<T extends Payment>(T payment) { }

    public static void main(String[] args) {
        Building destination = new Building("Diamond Heights",
                                        new GeoLocation(17.43543, 78.47551, 5),
                                        BuildingType.APARTMENT);
        // Code does not use BuildingType type
        if (destination instanceof Building(String s,
                                            GeoLocation loc,
                                            BuildingType type)) {
            System.out.println(String
                    .format("Your destination, %s, is at Location (%f, %f)",
                            s, loc.latitude, loc.longitude));
        }

        // New Feature code can use BuildingType _ and
        // GeoLocation altitude
        /* The unnamed pattern is denoted by an underscore character _ (U+005F)
           It allows the type and name of a record component to
           be elided in pattern matching
        */
        if (destination instanceof Building(String s,
                                            GeoLocation loc,
                                            BuildingType _)) {
            System.out.println(String
                    .format("Your destination, %s, is at Location (%f, %f)",
                            s, loc.latitude, loc.longitude));
        }

        // Or even better
        if (destination instanceof
                    Building(String name,
                             GeoLocation(double latitude,
                                         double longitude,
                                         _) ,
                             BuildingType _)) {
            System.out.println(String
                    .format("Your destination, %s, is at Location (%f, %f)",
                            name, latitude, longitude));
        }

        // Catch block where the Exception variable is
        // not intended to be used
        String s = "Sunit Java 21";
        try {
            int i = Integer.parseInt(s);
            System.out.println("My number is: " + i);
        } catch (NumberFormatException _) {
            System.out.println("Invalid number: " + s);
        }

        // Unnamed pattern variables are helpful when a
        // switch executes the same action for multiple cases

        AppPayment<? extends Payment> payment =
                new AppPayment<UPIPayment>(new UPIPayment());

        // Existing way
        switch (payment) {
            case AppPayment(UPIPayment upi)   ->
                    System.out.println("₹100 deducted from VPA. Details: " +
                            payment);
            case AppPayment(WalletPayment wallet)  ->
                    System.out.println("₹100 deducted from Wallet. Details: " +
                            payment);
            case AppPayment(CardPayment card) ->
                    System.out.println("₹100 deducted from VISA card. Details: " +
                            payment);
        }

        // New way
        switch (payment) {
            case AppPayment(UPIPayment _)   ->
                    System.out.println("₹ deducted from VPA. Details: " +
                            payment);
            case AppPayment(WalletPayment _)  ->
                    System.out.println("₹ deducted from Wallet. Details: " +
                            payment);
            case AppPayment(CardPayment _) ->
                    System.out.println("₹ deducted from VISA. Details: " +
                            payment);
        }

    }

}

sealed abstract class Payment permits
        UPIPayment,
        WalletPayment,
        CardPayment { }
final class UPIPayment extends Payment { }
final class WalletPayment extends Payment { }
final class CardPayment extends Payment { }