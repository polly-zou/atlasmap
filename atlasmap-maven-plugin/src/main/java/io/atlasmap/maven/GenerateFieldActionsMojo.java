/*
 * Copyright (C) 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atlasmap.maven;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import io.atlasmap.core.DefaultAtlasFieldActionService;
import io.atlasmap.v2.ActionDetails;

/**
 * The Generate Field Actions Mojo loads the AtlasMap Field Actions available in the classpath
 * and generates the metadata JSON.
 */
@Mojo(name = "generate-field-actions")
public class GenerateFieldActionsMojo extends AbstractAtlasMapMojo {
    /** Default output file prefix. */
    public static final String DEFAULT_OUTPUT_FILE_PREFIX = "atlasmap-field-action";

    /**
     * A list of {@code <groupId>:<artifactId>[:<extension>[:<classifier>]]:<version>} of
     * the artifacts to resolve.
     */
    @Parameter
    private List<String> artifacts;

    /**
     * A list of jar files to search field actions.
     */
    @Parameter
    private List<String> jars;

    /**
     * The directory where field action metadata get generated to.
     * Use this property when you want to output to different directory than inspection outputDir.
     */
    @Parameter
    private File fieldActionOutputDir;

    /**
     * Allows you to configure the plugin with: <code>
     *
     *     <configuration>
     *         <fieldActions>
     *             <fieldAction>
     *                 <artifacts>
     *                     <artifact>io.atlasmap:atlas-java-generateFieldAction-test:1.1</artifact>
     *                 </artifacts>
     *             </fieldAction>
     *             <fieldAction>
     *                 <jars>
     *                     <jar>src/test/resources/my-field-actions.jar</jar>
     *                 </jars>
     *             </fieldAction>
     *         </fieldAxtions>
     *     </configuration>
     *
     * </code>
     */
    @Parameter()
    private List<FieldAction> fieldActions;

    /**
     * The Field Action metadata.
     */
    public static class FieldAction {
        private List<String> artifacts;
        private List<String> jars;

        /**
         * Gets the artifacts.
         * @return artifacts
         */
        public List<String> getArtifacts() {
            return artifacts;
        }

        /**
         * Sets the artifacts.
         * @param artifacts artifacts
         */
        public void setArtifacts(List<String> artifacts) {
            this.artifacts = artifacts;
        }

        /**
         * Get the jars.
         * @return jars
         */
        public List<String> getJars() {
            return this.jars;
        }

        /**
         * Sets the jars.
         * @param jars jars
         */
        public void setJars(List<String> jars) {
            this.jars = jars;
        }

        @Override
        public String toString() {
            return "{artifacts:" + artifacts + ", jars:" + jars + "}";
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (getFieldActionOutputDir() != null) {
            getFieldActionOutputDir().mkdirs();
            this.setOutputDir(getFieldActionOutputDir());
        } else if (getOutputDir() != null) {
            getOutputDir().mkdirs();
        }
        List<URL> urls = new ArrayList<>();
        if (this.artifacts != null) {
            urls.addAll(resolveClasspath(artifacts));
        }
        if (this.jars != null) {
            for (String jar : jars) {
                File jarFile;
                if (jar == null || jar.isEmpty() || !(jarFile = new File(jar)).exists()) {
                    getLog().warn(String.format("Could not load jar file '%s' - ignoring", jar));
                    continue;
                }
                try {
                    urls.add(jarFile.toURI().toURL());
                } catch (Exception e) {
                    getLog().warn(String.format("Could not load jar file '%s' - ignoring", jar), e);
                }
            }
        }
        if (fieldActions != null) {
            for (FieldAction fieldAction : fieldActions) {
                if (fieldAction.artifacts != null) {
                    urls.addAll(resolveClasspath(fieldAction.artifacts));
                }
                if (fieldAction.jars != null) {
                    for (String jar : fieldAction.jars) {
                        File jarFile;
                        if (jar == null || jar.isEmpty() || !(jarFile = new File(jar)).exists()) {
                            getLog().warn(String.format("Could not load jar file '%s' - ignoring", jar));
                            continue;
                        }
                        try {
                            urls.add(jarFile.toURI().toURL());
                        } catch (Exception e) {
                            getLog().warn(String.format("Could not load jar file '%s' - ignoring", jar), e);
                        }
                    }
                }
            }
        }
        if (!urls.isEmpty()) {
            generateFieldAction(urls);
        }
    }

    private void generateFieldAction(List<URL> urls)
            throws MojoFailureException, MojoExecutionException {

        DefaultAtlasFieldActionService fieldActionService = DefaultAtlasFieldActionService.getInstance();
        ClassLoader origTccl = Thread.currentThread().getContextClassLoader();
        ActionDetails answer = new ActionDetails();
        try (URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[urls.size()]), origTccl)) {
            fieldActionService.init(loader);
        } catch (Exception e) {
            throw new MojoExecutionException("Could not load field actions:", e);
        }
        answer.getActionDetail().addAll(fieldActionService.listActionDetails());

        writeToJsonFile(DEFAULT_OUTPUT_FILE_PREFIX, answer);
    }

    /**
     * Gets the artifacts.
     * @return artifacts
     */
    public List<String> getArtifacts() {
        return artifacts;
    }

    /**
     * Sets the artifacts.
     * @param artifacts artifacts
     */
    public void setArtifacts(List<String> artifacts) {
        this.artifacts = artifacts;
    }

    /**
     * Gets the field action metadata.
     * @return field action metadata
     */
    public List<FieldAction> getFieldActions() {
        return fieldActions;
    }

    /**
     * Sets the field action metadata.
     * @param fieldActions field action metadata
     */
    public void setFieldActions(List<FieldAction> fieldActions) {
        this.fieldActions = fieldActions;
    }

    /**
     * Gets the field action output directory.
     * @return output directory
     */
    public File getFieldActionOutputDir() {
        return fieldActionOutputDir;
    }

    /**
     * Sets the field action output directory.
     * @param outputDir output directory
     */
    public void setFieldActionOutputDir(File outputDir) {
        this.fieldActionOutputDir = outputDir;
    }

}
