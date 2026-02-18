/*
 * The MIT License
 *
 * Copyright (c) 2018, CloudBees, Inc.
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

package com.cloudbees.jenkins.plugins.bitbucket.api.credentials;

import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;

/**
 * Authenticator that uses an API token with HTTP Basic authentication.
 * The username format is "{@value #API_TOKEN_PREFIX}actual_username".
 * The token is provided as the password and uses standard Basic Auth.
 */
public class BitbucketAPITokenAuthenticator extends BitbucketUsernamePasswordAuthenticator {
    /** Prefix for API token credentials */
    public static final String API_TOKEN_PREFIX = "API_TOKEN/";

    /**
     * Tests if the credentials have an API token prefix.
     * 
     * @param credentials the credentials to test
     * @return true if username starts with API_TOKEN_PREFIX, false otherwise
     */
    public static boolean isAPIToken(StandardUsernamePasswordCredentials credentials) {
        String username = credentials.getUsername();
        return username != null && username.startsWith(API_TOKEN_PREFIX);
    }

    /**
     * Constructor that strips the token prefix from the username.
     * 
     * @param credentials the username/password credentials where username starts with API_TOKEN_PREFIX
     */
    public BitbucketAPITokenAuthenticator(StandardUsernamePasswordCredentials credentials) {
        super(new PrefixStrippingCredentials(credentials, API_TOKEN_PREFIX));
    }
}
