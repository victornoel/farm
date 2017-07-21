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
package com.zerocracy.stk.pm.staff.awards

import com.jcabi.xml.XML
import com.zerocracy.farm.Assume
import com.zerocracy.jstk.Project
import com.zerocracy.pm.ClaimIn
import com.zerocracy.pm.ClaimOut
import com.zerocracy.pmo.Awards

def exec(Project project, XML xml) {
  new Assume(project, xml).type('Award points were added')
  ClaimIn claim = new ClaimIn(xml)
  String job = claim.param('job')
  String login = claim.param('login')
  Integer points = Integer.parseInt(claim.param('points'))
  Awards awards = new Awards(project, login).bootstrap()
  new ClaimOut()
    .type('Notify user')
    .token(login)
    .param(
      'message',
      String.format(
        'You got %+d points in `%s`, your total is'
        + ' [%+d](http://www.0crat.com/u/%s)',
        points, job, awards.total(), login
      )
    )
    .postTo(project)
  new ClaimOut()
    .type('Notify job')
    .token(job)
    .param(
      'message',
      String.format(
        '%+d points just awarded to @%s, total is'
        + ' [%+d](http://www.0crat.com/u/%s)',
        points, login, awards.total(), login
      )
    )
    .postTo(project)
}