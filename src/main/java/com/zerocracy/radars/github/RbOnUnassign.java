/**
 * Copyright (c) 2016-2017 Zerocracy
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

package com.zerocracy.radars.github;

import com.jcabi.github.Github;
import com.jcabi.github.Issue;
import com.zerocracy.jstk.Farm;
import com.zerocracy.jstk.Project;
import com.zerocracy.pm.ClaimOut;
import com.zerocracy.pm.in.Orders;
import java.io.IOException;
import javax.json.JsonObject;
import org.cactoos.text.FormattedText;

/**
 * Cancel order on unassignment of issue.
 *
 * @author Carlos Miranda (miranda.cma@gmail.com)
 * @version $Id$
 * @since 0.16.1
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
public final class RbOnUnassign implements Rebound {

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public String react(final Farm farm, final Github github,
        final JsonObject event) throws IOException {
        final Issue.Smart issue = new Issue.Smart(
            new IssueOfEvent(github, event)
        );
        final String login = new GhIssueEvent(event).assignee();
        final String job = new Job(issue).toString();
        final Project project = new GhProject(farm, issue.repo());
        if (new Orders(project).bootstrap().assigned(job)) {
            new ClaimOut()
                .type("Cancel order")
                .token(new TokenOfIssue(issue))
                .param("job", new Job(issue))
                .param("reason", "GitHub issue was unassigned")
                .postTo(project);
        }
        return new FormattedText(
            "Issue #%d was resigned (was assigned to %s) via Github",
            issue.number(), login
        ).asString();
    }
}
