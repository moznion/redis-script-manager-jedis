package net.moznion.redis.script.manager.jedis;

import java.util.Arrays;
import java.util.List;

import net.moznion.redis.script.manager.core.ScriptManager;

import redis.clients.jedis.ScriptingCommands;
import redis.clients.jedis.exceptions.JedisDataException;

public class JedisScriptManager extends ScriptManager<String, String> {
    private static final String[] EMPTY_VALUE = {};

    private final String script;
    private final ScriptingCommands commands;
    private final boolean useEvalSHA;

    boolean isNoScript;

    public JedisScriptManager(final ScriptingCommands commands, final String script) {
        this(commands, script, null, true);
    }

    public JedisScriptManager(final ScriptingCommands commands,
                              final String script,
                              final String sha1) {
        this(commands, script, sha1, true);
    }

    public JedisScriptManager(final ScriptingCommands commands,
                              final String script,
                              final boolean useEvalSHA) {
        this(commands, script, null, useEvalSHA);
    }

    public JedisScriptManager(final ScriptingCommands commands,
                              final String script,
                              final String sha1,
                              final boolean useEvalSHA) {
        this.commands = commands;
        this.script = script;
        this.sha1 = sha1;
        this.useEvalSHA = useEvalSHA;
        isNoScript = true;
    }

    @Override
    public Object eval(final String[] keys) {
        return eval(keys, EMPTY_VALUE);
    }

    @Override
    public Object eval(final String[] keys, final String[] values) {
        final List<String> keysList = Arrays.asList(keys);
        final List<String> valuesList = Arrays.asList(values);

        isNoScript = false;

        if (useEvalSHA) {
            final String sha1 = getSHA1(script);
            try {
                return commands.evalsha(sha1, keysList, valuesList);
            } catch (JedisDataException e) {
                if (!e.getMessage().startsWith("NOSCRIPT")) {
                    // Not "NOSCRIPT" error; unexpected
                    throw e;
                }
            }
        }

        // When "NOSCRIPT" error was raised, eval script and register it
        isNoScript = true;
        return commands.eval(script, keysList, valuesList);
    }
}
