Redis Script Manager for Java with [Jedis](https://github.com/xetorthio/jedis) [![Build Status](https://travis-ci.org/moznion/redis-script-manager-jedis.svg?branch=master)](https://travis-ci.org/moznion/redis-script-manager-jedis) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.moznion/redis-script-manager-jedis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.moznion/redis-script-manager-jedis)
==

Simple manager for scripting of Redis with [Jedis](https://github.com/xetorthio/jedis).  
This library is Java port of [p5-Redis-Script](https://github.com/shogo82148/p5-Redis-Script).

Usage
--

```java
final Jedis jedis = new Jedis("127.0.0.1", 6379);
final JedisScriptManager scriptManager = new JedisScriptManager(jedis, "redis.call('SET', KEYS[1], ARGV[1])");
scriptManager.eval(new String[] { "sample_key" }, new String[] { "42" });
```

See Also
--

- [https://github.com/moznion/redis-script-manager-core](https://github.com/moznion/redis-script-manager-core)
- [https://github.com/moznion/redis-script-manager-lettuce](https://github.com/moznion/redis-script-manager-lettuce)
- [https://github.com/shogo82148/p5-Redis-Script](https://github.com/shogo82148/p5-Redis-Script)

Author
--

moznion (<moznion@moznion.net>)

License
--

MIT

