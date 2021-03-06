/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.appengine.task.internal

/**
 * KickStart command line parameters builder.
 *
 * @author Benjamin Muschko
 */
@Singleton
class KickStartParamsBuilder {
    static final String MAIN_CLASS = 'com.google.appengine.tools.development.DevAppServerMain'
    static final String ADDRESS = '--address'
    static final String PORT = '--port'
    static final String DISABLE_UPDATE_CHECK = '--disable_update_check'
    static final String JVM_FLAG = '--jvm_flag'
    static final String REMOTE_SHUTDOWN_FLAG = "--allow_remote_shutdown"
    static final String GENERATED_DIR = '--generated_dir'

    /**
     * Builds command line parameters.
     *
     * @param kickStartParams KickStart parameters
     * @return Command line parameters
     */
    List<String> buildCommandLineParams(KickStartParams kickStartParams) {
        List<String> params = [MAIN_CLASS, "$PORT=$kickStartParams.httpPort"]

        if (kickStartParams.httpAddress) {
            params << "$ADDRESS=${kickStartParams.httpAddress}"
        }

        if(kickStartParams.disableUpdateCheck) {
            params << DISABLE_UPDATE_CHECK
        }
        
        if(kickStartParams.generatedDir) {
            params << "$GENERATED_DIR=${kickStartParams.generatedDir}"
        }

        params << REMOTE_SHUTDOWN_FLAG

        if(kickStartParams.jvmFlags) {
            kickStartParams.jvmFlags.each { jvmFlag ->
                params << "$JVM_FLAG=$jvmFlag"
            }
        }

        params << kickStartParams.explodedWarDirectory.canonicalPath
        params
    }
}
