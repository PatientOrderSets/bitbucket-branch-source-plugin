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
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jenkins.authentication.tokens.api.AuthenticationTokenSource;

/** Source for username/password authenticators. */
@Extension
public class BitbucketUsernamePasswordAuthenticatorSource extends AuthenticationTokenSource<BitbucketUsernamePasswordAuthenticator, StandardUsernamePasswordCredentials> {
    
    public BitbucketUsernamePasswordAuthenticatorSource() {
        super(BitbucketUsernamePasswordAuthenticator.class, StandardUsernamePasswordCredentials.class);
    }

    @NonNull
    @Override
    public BitbucketUsernamePasswordAuthenticator convert(@NonNull StandardUsernamePasswordCredentials credentials) {
        if (BitbucketAccessTokenAuthenticator.isAccessToken(credentials)) {
            return new BitbucketAccessTokenAuthenticator(credentials);
        }

        if (BitbucketAPITokenAuthenticator.isAPIToken(credentials)) {
            return new BitbucketAPITokenAuthenticator(credentials);
        }

        return new BitbucketUsernamePasswordAuthenticator(credentials);
    }
}
