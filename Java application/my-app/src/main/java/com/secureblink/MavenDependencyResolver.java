package com.secureblink;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.eclipse.aether.graph.Dependency;

// import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MavenDependencyResolver {
    public static void main(String[] args) throws Exception {
        // Create a repository system
        RepositorySystem system = newRepositorySystem();
        System.out.println(system);
        // Create a local repository
        LocalRepository localRepo = new LocalRepository("target/local-repo");

        // Create a remote repository
        RemoteRepository remoteRepo = new RemoteRepository.Builder("central", "default",
                "https://repo1.maven.org/maven2/").build();

        // Create a repository system session
        DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();

        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
        // Create a dependency request
        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(new Dependency(new DefaultArtifact("junit:junit:4.12"), "compile"));
        collectRequest.addRepository(remoteRepo);
        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest,
                DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE));

        // Resolve the dependency
        system.resolveDependencies(session, dependencyRequest);

        // Download the JAR file
        String url = "https://repo1.maven.org/maven2/junit/junit/4.12/junit-4.12.jar";
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        URL urlObj = new URL(url);
        System.out.println("Downloading " + url);
        if (urlObj != null) {
            try (InputStream in = urlObj.openStream()) {
                Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            System.out.println("URL is null");
        }
    }

    private static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        System.out.println(locator.getService(RepositorySystem.class));
        return locator.getService(RepositorySystem.class);
    }

}
