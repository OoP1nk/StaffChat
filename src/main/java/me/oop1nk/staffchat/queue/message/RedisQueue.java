package me.oop1nk.staffchat.queue.message;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import dev.waterdog.waterdogpe.ProxyServer;
import me.oop1nk.staffchat.StaffChat;
import me.oop1nk.staffchat.utils.ConfigKeys;
import me.oop1nk.staffchat.utils.Message;
import me.oop1nk.staffchat.queue.Queue;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;
import java.util.function.Consumer;

public class RedisQueue implements Queue {

    private final Gson gson = new Gson();

    private JedisPool pool;

    @Override
    public void connect() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2);
        config.setMaxIdle(0);

        String host = StaffChat.getInstance().getConfig().getString(ConfigKeys.KEY_REDIS_HOST, "127.0.0.1");
        int port = StaffChat.getInstance().getConfig().getInt(ConfigKeys.KEY_REDIS_PORT, 6379);
        String password = StaffChat.getInstance().getConfig().getString(ConfigKeys.KEY_REDIS_PASSWORD);

        if(password == null || password.isEmpty()) {
            pool = new JedisPool(config, host, port);
        } else {
            pool = new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, password);
        }

        subscribe();
    }

    @Override
    public void publishMessage(Message message) {
        String json = gson.toJson(message);
        ProxyServer.getInstance().getScheduler().scheduleAsync(() -> {
            try(Jedis jedis = pool.getResource()) {
                jedis.publish("staffchat:messages", json);
            } catch(JedisException ex) {
                StaffChat.getInstance().getLogger().error("Error while publishing message:");
                ex.printStackTrace();
            }
        });
    }

    @Override
    public Consumer<Message> getMessageConsumer() {
        return result -> {
            Map<String, String> placeholders = ImmutableMap.<String, String>builder()
                    .put("$player$", result.getPlayerName())
                    .put("$server$", result.getServerName())
                    .put("$message$", result.getMessage())
                    .put("$region$", result.getRegion())
                    .build();
            ProxyServer.getInstance().getPlayers()
                    .values()
                    .stream()
                    .filter(p -> p.hasPermission("staffchat.use"))
                    .forEach(p -> p.sendMessage(StaffChat.formatMessage(placeholders)));
        };
    }

    private void subscribe() {
        StaffChat.getInstance().getProxy().getScheduler().scheduleAsync(() -> {
            while(true) {
                try(Jedis jedis = pool.getResource()) {
                    StaffChat.getInstance().getLogger().info("Task run!");
                    jedis.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            Message pojo = new Gson().fromJson(message, Message.class);
                            getMessageConsumer().accept(pojo);
                        }
                    }, "staffchat:messages");
                } catch(JedisException ex) {
                    StaffChat.getInstance().getLogger().warn("Error while reading message. Did you lose connection to the redis server?");
                    ex.printStackTrace();
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
