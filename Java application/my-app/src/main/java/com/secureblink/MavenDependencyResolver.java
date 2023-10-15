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

// import java.io.BufferedInputStream;
// // import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// // import java.io.InputStream;
// import java.net.URL;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;

public class MavenDependencyResolver {
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.err.println("Usage: java MavenDependencyResolver <groupId> <artifactId> <version>");
            System.exit(1);
        }

        String groupId = args[0];
        String artifactId = args[1];
        String version = args[2];

        // Create a repository system
        RepositorySystem system = newRepositorySystem();
        System.out.println("system created");
        // Create a local repository
        LocalRepository localRepo = new LocalRepository("target/local-repo");
        System.out.println("localRepo created");
        // Create a remote repository
        RemoteRepository remoteRepo = new RemoteRepository.Builder("central", "default",
                "https://repo1.maven.org/maven2/").build();
            System.out.println("remoteRepo created");
        // Create a repository system session
        DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();
        System.out.println("session created");
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
        // Create a dependency request
        CollectRequest collectRequest = new CollectRequest();
        System.out.println("collectRequest created");
        collectRequest
                .setRoot(new Dependency(new DefaultArtifact(groupId + ":" + artifactId + ":" + version), "compile"));
        collectRequest.addRepository(remoteRepo);
        DependencyRequest dependencyRequest = new DependencyRequest(collectRequest,
                DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE));
        System.out.println("dependencyRequest created");
        // Resolve the dependency
        system.resolveDependencies(session, dependencyRequest);
        System.out.println("dependency resolved");
        groupId = groupId.replace(".", "/"); // Replace '.' with '/' in Group ID

        // String urlStr = "https://repo1.maven.org/maven2/"
        //         + groupId + "/"
        //         + artifactId + "/"
        //         + version + "/"
        //         + artifactId + "-" + version + ".jar";

        // downloadJar(urlStr, artifactId + "-" + version + ".jar");
    

        // Download the JAR file
        // String url = "https://repo1.maven.org/maven2/" + groupId.replace(".", "/") + "/" + artifactId + "/" + version
        //         + "/" + artifactId + "-" + version + ".jar";
        // String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        // URL urlObj = new URL(url);
        // System.out.println("Downloading " + url);
        // if (urlObj != null) {
        //     // Path tempDir = Files.createTempDirectory("resolved_dependencies");
        //     // File jarFile = new File(tempDir.toFile(), "my-library.jar");
        //     try (InputStream in = urlObj.openStream()) {
        //         Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        //     }
        // } else {
        //     System.out.println("URL is null");
        // }

    }

    private static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        System.out.println(locator.getService(RepositorySystem.class));
        return locator.getService(RepositorySystem.class);
    }

    // private static void downloadJar(String urlStr, String outputFileName) throws IOException {
    //     URL url = new URL(urlStr);
    //     try (BufferedInputStream in = new BufferedInputStream(url.openStream());
    //          FileOutputStream fileOutputStream = new FileOutputStream(outputFileName)) {
    //         byte dataBuffer[] = new byte[1024];
    //         int bytesRead;
    //         while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
    //             fileOutputStream.write(dataBuffer, 0, bytesRead);
    //         }
    //     } catch (IOException e) {
    //         // Handle exception
    //         e.printStackTrace();
    //     }
    // }

}
