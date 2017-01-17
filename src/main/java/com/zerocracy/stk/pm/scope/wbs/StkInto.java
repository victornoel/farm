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
package com.zerocracy.stk.pm.scope.wbs;

import com.zerocracy.jstk.Project;
import com.zerocracy.jstk.Stakeholder;
import com.zerocracy.pm.Job;
import com.zerocracy.pm.Person;
import com.zerocracy.pm.scope.Wbs;
import java.io.IOException;

/**
 * New task goes into scope.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class StkInto implements Stakeholder {

    /**
     * Project.
     */
    private final Project project;

    /**
     * Tube.
     */
    private final Person person;

    /**
     * Job.
     */
    private final Job job;

    /**
     * Ctor.
     * @param pkt Project
     * @param tbe Tube
     * @param jbb Job
     */
    public StkInto(final Project pkt, final Person tbe, final Job jbb) {
        this.project = pkt;
        this.person = tbe;
        this.job = jbb;
    }

    @Override
    public void work() throws IOException {
        new Wbs(this.project).add(this.job);
        this.person.say(
            String.format(
                "Done, job `%s` is in scope of \"%s\"",
                this.job.name(), this.project
            )
        );
    }
}
