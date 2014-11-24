enum Terrain {
        ROAD,
        AIRPORT,
        WATER,
        MARINA,
        FIELD,
        FOREST,
        MOUNTAIN;
}

public abstract class Vehicle {
        public abstract boolean startTrip(Terrain t);
        public abstract boolean endTrip(Terrain t);
        public abstract boolean move(Terrain t);
}

