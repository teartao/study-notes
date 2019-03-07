import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author neotao
 * @Date 2019/2/20
 * @Version V0.0.1
 * @Desc
 */
public class TestService {
    private static JedisPool pool = null;
    private static int counter = 0;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "172.16.1.178", 6379, 3000);
    }

    DistributeLock lock = new DistributeLock(pool);

    int goodsCount = 90;

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
        String indentifier = lock.lockWithTimeout("resource", 5000, 1000);
        ++counter;

        if (goodsCount <= 0) {
            System.out.println("user "+ counter+"卖完了");
        }else{
            System.out.println("user " + counter + "获得了锁 \tleft : " + --goodsCount);
        }
        lock.releaseLock("resource", indentifier);
    }

    public static void main(String[] args) {
        final TestService service = new TestService();
        for (int i = 0; i < 100; i++) {
            // ThreadA threadA = new ThreadA(service);
            // threadA.start();
            new Thread(() -> service.seckill()).start();
        }
    }

}

class ThreadA extends Thread {
    private TestService service;

    public ThreadA(TestService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.seckill();
    }
}

