package com.codenvy.factory.generator;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Generates che-web-site fragment
 * @author Florent Benoit
 */
public class CLI {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Generator generator = new Generator();
        new JCommander(generator, args);
        generator.start();
    }
}
