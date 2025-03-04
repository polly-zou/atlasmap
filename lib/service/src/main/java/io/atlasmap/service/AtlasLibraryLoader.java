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
package io.atlasmap.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.atlasmap.api.AtlasException;
import io.atlasmap.core.CompoundClassLoader;

/**
 * The class loader to achieve dynamic loading for which is required for design time
 * backend, such as custom field action, Java Document and custom mapping. The user uploaded
 * jar is dynamically added and classpath is updated to reflect them in UI on the fly.
 */
public class AtlasLibraryLoader extends CompoundClassLoader {
    private static final Logger LOG = LoggerFactory.getLogger(AtlasLibraryLoader.class);

    private File saveDir;
    private URLClassLoader urlClassLoader;
    private Set<ClassLoader> alternativeLoaders = new HashSet<>();
    private Set<AtlasLibraryLoaderListener> listeners = new HashSet<>();

    /**
     * A constructor.
     * @param saveDirName the path to the directory to save the uploaded jar
     * @throws AtlasException invalid save directory
     */
    public AtlasLibraryLoader(String saveDirName) throws AtlasException {
        LOG.debug("Using {} as a lib directory", saveDirName);
        this.saveDir = new File(saveDirName);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        if (!saveDir.isDirectory()) {
            throw new AtlasException(String.format("'%s' is not a directory", saveDir.getName()));
        }
        reload();
    }

    /**
     * Adds jar file from the {@link InputStream}.
     * @param is input stream
     * @throws Exception unexpected error
     */
    public void addJarFromStream(InputStream is) throws Exception {
        File dest = new File(saveDir + File.separator + UUID.randomUUID().toString() + ".jar");
        while (dest.exists()) {
            dest = new File(saveDir + File.separator + UUID.randomUUID().toString() + ".jar");
        }
        FileOutputStream buffer = new FileOutputStream(dest);
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        buffer.close();
        List<URL> urls = new LinkedList<>();
        urls.add(dest.toURI().toURL());
        if (this.urlClassLoader != null) {
            URL[] origUrls = this.urlClassLoader.getURLs();
            urls.addAll(Arrays.asList(origUrls));
        }
        reload();
    }

    /**
     * Clears all loaded jars.
     */
    public void clearLibraries() {
        if (this.urlClassLoader != null) {
            try {
                this.urlClassLoader.close();
            } catch (Exception e) {
                LOG.warn("Ignoring an error while closing an old URLClassLoader: {}", e.getMessage());
            }
            this.urlClassLoader = null;
        }

        File[] files = saveDir.listFiles();
        if (!saveDir.exists() || !saveDir.isDirectory() || files == null) {
            return;
        }
        for (File f : saveDir.listFiles()) {
            try {
                Files.delete(f.toPath());
             } catch (Exception e) {
                LOG.warn("Failed to remove jar file: '{}'", e.getMessage());
            };
        }
        reload();
    }

    /**
     * Gets the library class names available through this loader.
     * @return library class names
     * @throws AtlasException unexpected error
     */
    public ArrayList<String> getLibraryClassNames() throws AtlasException {
        final String classSuffix = ".class";
        ArrayList<String> classNames = new ArrayList<String>();

        if (this.urlClassLoader == null) {
            return classNames;
        }
        URL candidateURLs[] = this.urlClassLoader.getURLs();

        for (int i=0; i < candidateURLs.length; i++) {
            try (ZipInputStream zip = new ZipInputStream(new FileInputStream(candidateURLs[i].toURI().getPath()))) {
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (!entry.isDirectory() && entry.getName().endsWith(classSuffix)) {
                        String className = entry.getName().replace('/', '.');
                        classNames.add(className.substring(0, className.length() - classSuffix.length()));
                    }
                }
            } catch (IOException | URISyntaxException e) {
                throw new AtlasException(String.format("URL library '%s' access error: %s",
                    candidateURLs[i].getPath(), e.getMessage()));
            }
        }
        return classNames;
    }

    /**
     * Gets the library class names available through this loader, with filtered to the sub type of the class specified.
     * @param clazz class to filter
     * @param allowAbstract true to allow abstract classes
     * @return filtered library class names
     * @throws AtlasException unexpected error
     * @see #getLibraryClassNames()
     */
    public ArrayList<String> getSubTypesOf(Class<?> clazz, boolean allowAbstract) throws AtlasException {
        ArrayList<String> answer = new ArrayList<>();
        if (clazz == null) {
            return answer;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Searching sub types of {}", clazz.getName());
        }
        for (String className : getLibraryClassNames()) {
            try {
                Class<?> c = loadClass(className);
                if (clazz.isAssignableFrom(c)) {
                    if (!allowAbstract
                            && (c.isInterface() || Modifier.isAbstract(c.getModifiers()))) {
                        continue;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Found {}", className);
                    }
                    answer.add(className);
                }
            } catch (Exception e) {
                LOG.debug("", e);
                continue;
            }
        }
        return answer;
    }

    /**
     * Reloads all jars in the save directory.
     */
    public synchronized void reload() {
        List<URL> urls = new LinkedList<>();
        File[] files = saveDir.listFiles();
        if (!saveDir.exists() || !saveDir.isDirectory() || files == null) {
            return;
        }

        for (File f : files) {
            try {
                if (!f.isFile()) {
                    LOG.warn("Ignoring invalid file {}", f.getAbsolutePath());
                    continue;
                }
                urls.add(f.toURI().toURL());
            } catch (Exception e) {
                LOG.warn("Ignoring invalid file", e);
            }
        }
        // This won't work on hierarchical class loader like JavaEE or OSGi.
        // We don't have any plan to get design time services working on those though.
        if (LOG.isDebugEnabled()) {
            LOG.debug("Reloading library jars: {}", urls);
        }
        this.urlClassLoader = urls.size() == 0 ? null
         : new URLClassLoader(urls.toArray(new URL[0]), AtlasLibraryLoader.class.getClassLoader());
        listeners.forEach(l -> l.onUpdate(this));
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        LOG.debug("Loading Class:{}", name);
        for (ClassLoader cl : sortLoaders()) {
            try {
                return cl.loadClass(name);
            } catch (NoClassDefFoundError ncdfe) {
                throw ncdfe;
            } catch (Throwable t) {
                LOG.debug("Class not found: [ClassLoader:{}, Class name:{}, message:{}]",
                    cl, name, t.getMessage(), t);
                continue;
            }
        }
        return super.loadClass(name);
    }

    private Set<ClassLoader> sortLoaders() {
        Set<ClassLoader> loaders = new LinkedHashSet<>();
        if (this.urlClassLoader != null) {
            loaders.add(this.urlClassLoader);
        }
        loaders.addAll(this.alternativeLoaders);
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        if (this != tccl) {
            loaders.add(tccl);
        }
        return loaders;
    }

    @Override
    public URL getResource(String name) {
        URL answer;
        for (ClassLoader cl : sortLoaders()) {
            answer = cl.getResource(name);
            if (answer != null) {
                LOG.debug("Found resource:[ClassLoader:{}, name:{}]", cl, name);
                return answer;
            }
        }
        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Set<URL> answer = new LinkedHashSet<>();
        for (ClassLoader cl : sortLoaders()) {
            for (Enumeration<URL> e = cl.getResources(name); e.hasMoreElements();) {
                LOG.debug("Found resource:[ClassLoader:{}, name:{}]", cl, name);
                answer.add(e.nextElement());
            }
        }
        return new Enumeration<URL>() {
            Iterator<URL> iterator = answer.iterator();
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public URL nextElement() {
                return iterator.next();
            }
        };
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream answer;
        for (ClassLoader cl : sortLoaders()) {
            answer = cl.getResourceAsStream(name);
            if (answer != null) {
                LOG.debug("Found resource:[ClassLoader:{}, name:{}]", cl, name);
                return answer;
            }
        }
        return super.getResourceAsStream(name);
    }

    /**
     * Gets if it has no jar loaded.
     * @return true if empty, or false
     */
    public boolean isEmpty() {
        return this.urlClassLoader == null;
    }

    @Override
    public void addAlternativeLoader(ClassLoader cl) {
        if (this != cl) {
            this.alternativeLoaders.add(cl);
        }
    }

    /**
     * Adds the listener of the library loading/unloading event.
     * {@link AtlasLibraryLoaderListener#onUpdate(AtlasLibraryLoader)}
     * is invoked on the event.
     * @param listener listener
     */
    public void addListener(AtlasLibraryLoaderListener listener) {
        this.listeners.add(listener);
    }

    /**
     * The interface for the event listener of the library loading/unloading event.
     * {@link AtlasLibraryLoaderListener#onUpdate(AtlasLibraryLoader)}
     * is invoked on the event.
     */
    public interface AtlasLibraryLoaderListener {
        /**
         * Handles library loading/unloading event.
         * @param loader library loader
         */
        public void onUpdate(AtlasLibraryLoader loader);
    }
}
