package net.sevenstars.middleearth.config;

/*
 * Copyright (c) 2021 magistermaks
 * Slightly modified by Kaupenjoe 2021
 * Slightly modified by the MiddleEarth Mod Team 2024
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.fabricmc.loader.api.FabricLoader;
import net.sevenstars.middleearth.MiddleEarth;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleConfig {

    private final HashMap<String, String> config = new HashMap<>();
    private final ConfigRequest request;
    private boolean broken = false;
    private static final String FILE_TYPE = ".toml";

    public interface DefaultConfig {
        String get( String namespace );

        static String empty( String namespace ) {
            return "";
        }
    }

    public static class ConfigRequest {

        private final File file;
        private final String filename;
        private DefaultConfig provider;

        private ConfigRequest(File file, String filename ) {
            this.file = file;
            this.filename = filename;
            this.provider = DefaultConfig::empty;
        }

        /**
         * Sets the default config provider, used to generate the
         * config if it's missing.
         *
         * @param provider default config provider
         * @return current config request object
         * @see DefaultConfig
         */
        public ConfigRequest provider( DefaultConfig provider ) {
            this.provider = provider;
            return this;
        }

        /**
         * Loads the config from the filesystem.
         *
         * @return config object
         * @see SimpleConfig
         */
        public SimpleConfig request() {
            return new SimpleConfig( this );
        }

        private String getConfig() {
            return provider.get( filename ) + "\n";
        }

    }

    /**
     * Creates new config request object, ideally `namespace`
     * should be the name of the mod id of the requesting mod
     *
     * @param filename - name of the config file
     * @return new config request object
     */
    public static ConfigRequest of( String filename ) {
        Path path = FabricLoader.getInstance().getConfigDir();
        return new ConfigRequest( path.resolve( filename + FILE_TYPE).toFile(), filename );
    }

    private void createConfig() throws IOException {

        // try creating missing files
        request.file.getParentFile().mkdirs();
        Files.createFile( request.file.toPath() );

        // write default config data
        PrintWriter writer = new PrintWriter(request.file, "UTF-8");
        writer.write( request.getConfig() );
        writer.close();

    }

    private void loadConfig() throws IOException {
        Scanner reader = new Scanner( request.file );
        for( int line = 1; reader.hasNextLine(); line ++ ) {
            parseConfigEntry( reader.nextLine(), line );
        }
    }

    private void parseConfigEntry( String entry, int line ) {
        if( !entry.isEmpty() && !entry.contains(ModConfigProvider.COMMENT_PREFIX)) {
            String[] parts = entry.split("=", 2);
            if( parts.length == 2 ) {
                // Recognizes comments after a value
                String temp = (parts[1].split(ModConfigProvider.COMMENT_PREFIX))[0];
                config.put( parts[0].replace(" ", ""), temp );
            }else{
                throw new RuntimeException("Syntax error in config file on line " + line + "!");
            }
        }
    }

    private SimpleConfig(ConfigRequest request ) {
        this.request = request;
        String identifier = "Config '" + request.filename + "'";

        if( !request.file.exists() ) {
            MiddleEarth.LOGGER.logInfoMsg( identifier + " is missing, generating default one..." );

            try {
                createConfig();
            } catch (IOException e) {
                MiddleEarth.LOGGER.logError( identifier + " failed to generate!", e);
                broken = true;
            }
        }

        if( !broken ) {
            try {
                loadConfig();
            } catch (Exception e) {
                MiddleEarth.LOGGER.logError( identifier + " failed to load!", e);
                broken = true;
            }
        }

    }

    /**
     * Queries a value from config, returns `null` if the
     * key does not exist.
     *
     * @return  value corresponding to the given key
     * @see     SimpleConfig#getOrDefault
     */
    @Deprecated
    public String get( String key ) {
        return config.get( key );
    }

    /**
     * Returns string value from config corresponding to the given
     * key, or the default string if the key is missing.
     *
     * @return  value corresponding to the given key, or the default value
     */
    public String getOrDefault( String key, String def ) {
        String val = get(key);
        return val == null ? def : val;
    }

    /**
     * Returns integer value from config corresponding to the given
     * key, or the default integer if the key is missing or invalid.
     *
     * @return  value corresponding to the given key, or the default value
     */
    public int getOrDefault( String key, int def ) {
        try {
            return Integer.parseInt( get(key) );
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Returns boolean value from config corresponding to the given
     * key, or the default boolean if the key is missing.
     *
     * @return  value corresponding to the given key, or the default value
     */
    public boolean getOrDefault( String key, boolean def ) {
        String val = get(key);
        if( val != null ) {
            return val.equalsIgnoreCase("true");
        }

        return def;
    }

    /**
     * Returns double value from config corresponding to the given
     * key, or the default string if the key is missing or invalid.
     *
     * @return  value corresponding to the given key, or the default value
     */
    public double getOrDefault( String key, double def ) {
        try {
            return Double.parseDouble( get(key) );
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * If any error occurred during loading or reading from the config
     * a 'broken' flag is set, indicating that the config's state
     * is undefined and should be discarded using `delete()`
     *
     * @return the 'broken' flag of the configuration
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * deletes the config file from the filesystem
     *
     * @return true if the operation was successful
     */
    public boolean delete() {
        MiddleEarth.LOGGER.logWarn( "Config '" + request.filename + "' was removed from existence! Restart the game to regenerate it." );
        return request.file.delete();
    }

}