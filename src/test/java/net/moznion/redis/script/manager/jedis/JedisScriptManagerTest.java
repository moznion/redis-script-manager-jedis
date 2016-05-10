package net.moznion.redis.script.manager.jedis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisScriptManagerTest {
    @Test
    public void jedisTest() {
        final Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.scriptFlush();

        final JedisScriptManager scriptManager =
                new JedisScriptManager(jedis, "redis.call('SET', KEYS[1], ARGV[1])");
        final String key = "script_manager_test";

        assertThat(scriptManager.isNoScript).isTrue();
        scriptManager.eval(new String[] { key }, new String[] { "42" });
        assertThat(jedis.get(key)).isEqualTo("42");
        scriptManager.eval(new String[] { key }, new String[] { "43" });
        assertThat(jedis.get(key)).isEqualTo("43");
        assertThat(scriptManager.isNoScript).isFalse();
    }

    @Test
    public void jedisTestWithoutValue() {
        final Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.scriptFlush();

        final JedisScriptManager scriptManager =
                new JedisScriptManager(jedis, "redis.call('SET', KEYS[1], 666)");
        final String key = "script_manager_test";

        scriptManager.eval(new String[] { key });
        assertThat(jedis.get(key)).isEqualTo("666");
    }
}
