/**
 * Copyright (c) 2016 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.stk.pm.hr.roles;

import com.jcabi.github.Github;
import com.jcabi.http.Request;
import com.jcabi.http.response.RestResponse;
import com.zerocracy.jstk.Project;
import com.zerocracy.jstk.Stakeholder;
import com.zerocracy.pm.Person;
import com.zerocracy.pm.hr.Roles;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Assign role to a person.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class StkAssign implements Stakeholder {

    /**
     * Github client.
     */
    private final Github github;

    /**
     * Project.
     */
    private final Project project;

    /**
     * Tube.
     */
    private final Person person;

    /**
     * Role.
     */
    private final String role;

    /**
     * Who to assign it to.
     */
    private final String target;

    /**
     * Ctor.
     * @param ghub Github client
     * @param pkt Project
     * @param tbe Tube
     * @param rle Role to assign
     * @param who Who to assign to
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public StkAssign(final Github ghub, final Project pkt, final Person tbe,
        final String rle, final String who) {
        this.github = ghub;
        this.project = pkt;
        this.person = tbe;
        this.role = rle;
        this.target = who;
    }

    @Override
    public void work() throws IOException {
        this.github.entry().uri()
            .path("/user/following")
            .path(this.target)
            .back()
            .method(Request.PUT)
            .fetch()
            .as(RestResponse.class)
            .assertStatus(HttpURLConnection.HTTP_NO_CONTENT);
        new Roles(this.project).assign(this.target, this.role);
        this.person.say(
            String.format(
                "Role \"%s\" assigned to \"%s\" in \"%s\"",
                this.role,
                this.target,
                this.project
            )
        );
    }
}
