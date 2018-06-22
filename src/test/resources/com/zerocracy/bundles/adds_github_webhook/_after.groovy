/**
 * Copyright (c) 2016-2018 Zerocracy
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
package com.zerocracy.bundles.adds_github_webhook

import com.jcabi.github.Coordinates
import com.jcabi.github.Github
import com.jcabi.github.Repo
import com.jcabi.xml.XML
import com.zerocracy.Farm
import com.zerocracy.Project
import com.zerocracy.entry.ExtGithub

// @todo #1048:30min Implement logic for adding webhook in add_github_webhook
//  stakeholder (see #1048 for details). After it is implemented uncomment this
//  test and make sure it passes. In production we need to use url:
//  http://www.rehttp.net/p/https://www.0crat.com/ghook, and in testing we use
//  current url of our instance + "/ghook".
//  Add another test that will check that in case
//  we don't have enough permissions we will inform the user about it.
def exec(Project project, XML xml) {
  Farm farm = binding.variables.farm
  Github github = new ExtGithub(farm).value()
  new Repo.Smart(github.repos().get(new Coordinates.Simple('test/test')))
//  MatcherAssert.assertThat(repo.hooks().iterate(), Matchers.hasSize(1))
//  Hook.Smart hook = new Hook.Smart(repo.hooks().get(0))
//  MatcherAssert.assertThat(hook.name(), Matchers.is('0crat'))
//  JsonObject json = hook.json()
//  MatcherAssert.assertThat(json.getBoolean('active'), Matchers.is(true))
//  MatcherAssert.assertThat(
//    json.getJsonArray('events').toListString(),
//    Matchers.containsInAnyOrder('*')
//  )
//  MatcherAssert.assertThat(
//    json.getJsonObject('config').getString('content_type'),
//    Matchers.is('form')
//  )
//  MatcherAssert.assertThat(
//    json.getJsonObject('config').getString('url'),
//    Matchers.endsWith("/ghook")
//  )
}
