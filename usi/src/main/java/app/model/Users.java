package app.model;

import com.google.common.collect.Maps;
import cx.ath.mancel01.webframework.cache.CacheService;
import cx.ath.mancel01.webframework.data.JPAService;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 *
 * @author mathieu
 */
@Singleton
public class Users {

    private final CacheService cache;

    private final ConcurrentMap<String, User>  users;

    //private static final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    private static final ExecutorService exec = Executors.newFixedThreadPool(1);

    private AtomicBoolean batchRunning = new AtomicBoolean(false);

    @Inject
    public Users(CacheService cache) {
        this.cache = cache;
        this.users = Maps.newConcurrentMap();
        //exec.scheduleAtFixedRate(new JPABatch(users), 0, 1, TimeUnit.SECONDS);
    }

    public boolean add(User u) {
        if (null == cache.get(u.getMail())) {
            cache.add(u.getMail(), u, 3600);
            if (!batchRunning.get()) {
                batchRunning.getAndSet(true);
                exec.submit(new JPABatch(users));
            }
            return null == users.putIfAbsent(u.getMail(), u);
        }
        return false;
    }

    public int getSize() {
        return cache.size();
    }

    private class JPABatch extends Thread {
        private ConcurrentMap<String, User>  users;
        public JPABatch(ConcurrentMap<String, User> users) {
            this.users = users;
        }
        @Override
        public void run() {
            if (users.isEmpty()) {
                batchRunning.getAndSet(false);
                return;
            }
            while(!users.isEmpty()) {
                JPAService.getInstance().startTx();
                EntityManager em = JPAService.currentEm.get();
                Set<Entry<String, User>> entries = users.entrySet();
                int i = 0;
                for (Entry<String, User> entry : entries) {
                    i++;
                    em.persist(entry.getValue());
                    users.remove(entry.getKey());
                    if (i == 500) break;
                }
                JPAService.getInstance().stopTx(false);
            }
            batchRunning.getAndSet(false);
        }
    }
}
