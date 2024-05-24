package helha.java24groupe08.client.models;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance;
    private ConcurrentHashMap<Integer, Session> sessions;
    private ExecutorService executor;

    private SessionManager() {
        sessions = new ConcurrentHashMap<>();
        executor = Executors.newCachedThreadPool();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }


    public void takeSeat(int sessionId, String seatNumber) {
        executor.submit(() -> {
            Session session = sessions.get(sessionId);
            if (session != null) {
                session.takeSeat(seatNumber);
            }
        });
    }


}
