/*
 * Copyright 2010 DTO Labs, Inc. (http://dtolabs.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.test.GrailsUnitTestCase

/*
* ScheduledExecutionServiceTests.java
*
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Jul 29, 2010 4:38:24 PM
* $Id$
*/

public class ScheduledExecutionServiceTests extends GrailsUnitTestCase {
    void setUp() {

    }

    void tearDown() {

    }

    public void testConvertNonWorkflow() {
        definedTest: {
            ScheduledExecution se = new ScheduledExecution(jobName: 'blue',
                project: 'AProject',
                groupPath: 'some/where',
                description: 'a job',
                argString: '-a b -c d',
                adhocExecution: false
            )
            def testService = new ScheduledExecutionService()

            def result = testService.convertNonWorkflow(se)

            assertTrue result
            assertNull se.argString
            assertFalse se.adhocExecution
            assertNull se.adhocRemoteString
            assertNull se.adhocLocalString
            assertNull se.adhocFilepath

            assertNotNull se.workflow
            assertNotNull se.workflow.commands
            assertEquals 1, se.workflow.commands.size()
            def item1 = se.workflow.commands.get(0)
            assertNotNull item1
            assertTrue item1 instanceof CommandExec
            assertEquals 'AProject', item1.project
            assertEquals '-a b -c d', item1.argString
        }
        adhocTest1: {
            ScheduledExecution se = new ScheduledExecution(
                jobName: 'blue',
                project: 'AProject',
                adhocExecution: true,
                adhocRemoteString: 'this is a test',
                groupPath: 'some/where',
                description: 'a job',
                argString: '-a b -c d',

            )
            def testService = new ScheduledExecutionService()

            def result = testService.convertNonWorkflow(se)

            assertTrue result
            assertNull se.argString
            assertFalse se.adhocExecution
            assertNull se.adhocRemoteString
            assertNull se.adhocLocalString
            assertNull se.adhocFilepath

            assertNotNull se.workflow
            assertNotNull se.workflow.commands
            assertEquals 1, se.workflow.commands.size()
            def item1 = se.workflow.commands.get(0)
            assertNotNull item1
            assertTrue item1 instanceof CommandExec
            assertEquals 'AProject', item1.project
            assertEquals '-a b -c d', item1.argString
            assertTrue item1.adhocExecution
            assertEquals "this is a test", item1.adhocRemoteString
            assertNull item1.adhocLocalString
            assertNull item1.adhocFilepath
        }
        adhocTest2: {
            ScheduledExecution se = new ScheduledExecution(
                jobName: 'blue',
                project: 'AProject',
                adhocExecution: true,
                adhocLocalString: 'this is a local test',
                groupPath: 'some/where',
                description: 'a job',
                argString: '-a b -c d',

            )
            def testService = new ScheduledExecutionService()

            def result = testService.convertNonWorkflow(se)

            assertTrue result
            assertNull se.argString
            assertFalse se.adhocExecution
            assertNull se.adhocRemoteString
            assertNull se.adhocLocalString
            assertNull se.adhocFilepath

            assertNotNull se.workflow
            assertNotNull se.workflow.commands
            assertEquals 1, se.workflow.commands.size()
            def item1 = se.workflow.commands.get(0)
            assertNotNull item1
            assertTrue item1 instanceof CommandExec
            assertEquals 'AProject', item1.project
            assertEquals '-a b -c d', item1.argString
            assertTrue item1.adhocExecution
            assertNull item1.adhocRemoteString
            assertEquals "this is a local test", item1.adhocLocalString
            assertNull item1.adhocFilepath
        }
        adhocTest2: {
            ScheduledExecution se = new ScheduledExecution(
                jobName: 'blue',
                project: 'AProject',
                adhocExecution: true,
                adhocFilepath: '/this/is/a/path',
                groupPath: 'some/where',
                description: 'a job',
                argString: '-a b -c d',

            )
            def testService = new ScheduledExecutionService()

            def result = testService.convertNonWorkflow(se)

            assertTrue result
            assertNull se.argString
            assertFalse se.adhocExecution
            assertNull se.adhocRemoteString
            assertNull se.adhocLocalString
            assertNull se.adhocFilepath

            assertNotNull se.workflow
            assertNotNull se.workflow.commands
            assertEquals 1, se.workflow.commands.size()
            def item1 = se.workflow.commands.get(0)
            assertNotNull item1
            assertTrue item1 instanceof CommandExec
            assertEquals 'AProject', item1.project
            assertEquals '-a b -c d', item1.argString
            assertTrue item1.adhocExecution
            assertNull item1.adhocRemoteString
            assertNull item1.adhocLocalString
            assertEquals '/this/is/a/path', item1.adhocFilepath
        }

    }
}