
public class Car extends Vehicle {
        public boolean endTrip(Terrain t) {
                return canMove(t);
        }

        public boolean move(Terrain t) {
                return canMove(t);
        }

        public boolean startTrip(Terrain t) {
                return canMove(t);
        }

        public boolean canMove(Terrain t) {
                if (t == Terrain.ROAD || t == Terrain.AIRPORT || t == Terrain.MARINA) {
                        return true;
                } else {
                        return false;
                }
        }
}
