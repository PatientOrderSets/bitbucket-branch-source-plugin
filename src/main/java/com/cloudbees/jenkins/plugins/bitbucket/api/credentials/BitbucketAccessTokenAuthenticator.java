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
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.CredentialsDescriptor;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.util.Secret;

/**
 * Authenticator that uses an access token.
 * The token is provided as the password in UsernamePasswordCredentials.
 * 
 * <p>This authenticator is only instantiated by {@link BitbucketUsernamePasswordAuthenticatorSource}
 * when the username starts with {@link #API_TOKEN_PREFIX}. The validation that the prefix is present
 * is performed in {@link BitbucketUsernamePasswordAuthenticatorSource#convert(StandardUsernamePasswordCredentials)}.
 * 
 * <p>Username format: "{@value #API_TOKEN_PREFIX}actual_username" where actual_username is the Bitbucket username.
 * The prefix is stripped before passing credentials to the parent class.
 */
public class BitbucketAccessTokenAuthenticator extends BitbucketUsernamePasswordAuthenticator {
    /**
     * The prefix that indicates an access token credential (password field contains the token).
     * When this prefix is present in the username, {@link BitbucketUsernamePasswordAuthenticatorSource}
     * will route the credential to this authenticator instead of {@link BitbucketUsernamePasswordAuthenticator}.
     * 
     * <p>Username format: "{@value API_TOKEN_PREFIX}actual_username" where actual_username is the Bitbucket username.
     */
    public static final String API_TOKEN_PREFIX = "API_TOKEN/";

    /**
     * Constructor.
     * 
     * <p>This constructor is only called by {@link BitbucketUsernamePasswordAuthenticatorSource}
     * after it has verified that the username starts with {@link #API_TOKEN_PREFIX}.
     * 
     * @param credentials the username/password credentials where username starts with {@link #API_TOKEN_PREFIX}
     */
    public BitbucketAccessTokenAuthenticator(StandardUsernamePasswordCredentials credentials) {
        super(new CleanCredentials(credentials));
    }

    /**
     * Wrapper that strips the {@link #API_TOKEN_PREFIX} from the username before passing to the parent class.
     * This allows the parent class to work with the actual Bitbucket username without knowing about the prefix.
     */
    private static class CleanCredentials implements StandardUsernamePasswordCredentials {
        private final StandardUsernamePasswordCredentials credentials;

        public CleanCredentials(StandardUsernamePasswordCredentials credentials) {
            this.credentials = credentials;
        }

        @Override
        public String getUsername() {
            // Safe to call substring without null check since BitbucketUsernamePasswordAuthenticatorSource
            // validates the prefix is present before creating this authenticator
            return credentials.getUsername().substring(API_TOKEN_PREFIX.length());
        }

        @Override
        public Secret getPassword() {
            return credentials.getPassword();
        }

        @Override
        public String getDescription() {
            return credentials.getDescription();
        }

        @Override
        public CredentialsScope getScope() {
            return credentials.getScope();
        }

        @Override
        public String getId() {
            return credentials.getId();
        }

        @Override
        public CredentialsDescriptor getDescriptor() {
            return credentials.getDescriptor();
        }
    }
}
