package helha.java24groupe08.client.models;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SeatReservationManager {
    private static SeatReservationManager instance;

    private final Map<String, Set<String>> reservations;
    private final Set<SeatReservationObserver> observers;

    private SeatReservationManager() {
        reservations = new ConcurrentHashMap<>();
        observers = new CopyOnWriteArraySet<>();
    }

    public static synchronized SeatReservationManager getInstance() {
        if (instance == null) {
            instance = new SeatReservationManager();
        }
        return instance;
    }

    public synchronized boolean reserveSeat(String sessionKey, String seat) {
        Set<String> sessionReservations = reservations.computeIfAbsent(sessionKey, k -> ConcurrentHashMap.newKeySet());
        if (sessionReservations.contains(seat)) {
            return false; // Seat already reserved
        }
        sessionReservations.add(seat);
        notifyObservers();
        return true;
    }



    public synchronized Set<String> getReservedSeats(String sessionKey) {
        return reservations.getOrDefault(sessionKey, ConcurrentHashMap.newKeySet());
    }

    public void addObserver(SeatReservationObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (SeatReservationObserver observer : observers) {
            observer.updateReservations();
        }
    }
}
