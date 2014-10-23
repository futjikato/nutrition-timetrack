package de.futjikato.nutrition.timetrek;

import de.futjikato.nutrition.base.AppDispatcher;
import de.futjikato.nutrition.base.Application;
import de.futjikato.nutrition.base.Log;
import de.futjikato.nutrition.base.auth.AuthDispatcher;
import de.futjikato.nutrition.base.config.Config;
import de.futjikato.nutrition.base.redis.ApiRedisListener;
import de.futjikato.nutrition.base.redis.Pool;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import redis.clients.jedis.Jedis;


/**
 * MSP Nutrition
 */
public class Main {

    private static final String ARG_CONFIG = "--config";
    private static final String ARG_CONFIG_DEST = "config";

    private static final String ARG_LOGS = "--logs";
    private static final String ARG_LOGS_DEST = "logs";

    private static String configArg;

    private static String logsArg;

    private static Config config;

    /**
     * Possible startup parameters :
     * --config <dir> Set config directory
     * --logs   <dir> Set the log directory
     *
     * @param args Startup arguments
     */
    public static void main(String[] args) {
        // STEP 1
        try {
            parseArguments(args);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
            System.err.println("Unable to start because of error in startup arguments.");
            return;
        }

        // STEP 2
        initializeConfig();

        // STEP 3
        startApplication();

        // STEP 4
        startJedisListener();
    }

    /**
     * Return the config
     *
     * @return config instance
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * STEP 1
     * Parses program arguments
     *
     * @param args Startup arguments
     *
     * @throws ArgumentParserException
     */
    private static void parseArguments(String[] args) throws ArgumentParserException {
        ArgumentParser argParser = ArgumentParsers.newArgumentParser("nutrition");
        argParser.description("Startup arguments.");

        // config arg
        Argument configArg = argParser.addArgument(ARG_CONFIG);
        configArg.dest(ARG_CONFIG_DEST);
        configArg.required(true);
        configArg.help("Directory of the configs to use.");

        // logs arg
        Argument logsArg = argParser.addArgument(ARG_LOGS);
        logsArg.dest(ARG_LOGS_DEST);
        logsArg.required(true);
        logsArg.help("Directory of the directory to write the log files to.");

        Namespace res = argParser.parseArgs(args);
        Main.configArg = res.getString(ARG_CONFIG_DEST);
        Main.logsArg = res.getString(ARG_LOGS_DEST);
    }

    /**
     * STEP 2
     */
    private static void initializeConfig() {
        // create config
        config = new Config(configArg);

        // set log dir path
        Log.setLogDir(logsArg);
    }

    /**
     * STEP 3
     */
    private static void startApplication() {
        Application app = Application.getInstance();
        app.setDispatcher(new AuthDispatcher(new AppDispatcher()));

        // init hibernate
        Application.getcurrentSession();
    }

    /**
     * STEP 4
     */
    private static void startJedisListener() {
        Jedis jedis = Pool.getInstance().getJedisPool().getResource();
        ApiRedisListener instance = new ApiRedisListener(jedis, "timetrek.request", "timetrek.response");
        instance.start();
    }
}
