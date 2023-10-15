package com.secureblink;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.*;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;

import java.io.File;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java MavenDependencyResolver <groupId> <artifactId> <version>");
            System.exit(1);
        }

        // String groupId = "com.amazonaws";
        // String artifactId = "aws-java-sdk";
        // String version = "1.11.230";
        String groupId = args[0];
        String artifactId = args[1];
        String version = args[2];
    
        try {
            File jarFile = resolveArtifact(groupId, artifactId, version, new File("C:/trial projects/Secure Blink Task/target/local-repo"));
            System.out.println("Downloaded JAR file at: " + jarFile.getAbsolutePath());
            
        } catch (ArtifactResolutionException e) {
            e.printStackTrace();
        }
    }

    private static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

        return locator.getService(RepositorySystem.class);
    }

    private static RepositorySystemSession newSession(RepositorySystem system, File localRepository) {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
        LocalRepository localRepo = new LocalRepository(localRepository);
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

        return session;
    }

    public static File resolveArtifact(String groupId, String artifactId, String version, File localRepository) throws ArtifactResolutionException {
        RepositorySystem repositorySystem = newRepositorySystem();
        RepositorySystemSession session = newSession(repositorySystem, localRepository);

        Artifact artifact = new DefaultArtifact(groupId, artifactId, "", "jar", version);
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);
        artifactRequest.setRepositories(Arrays.asList(new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build()));

        ArtifactResult artifactResult = repositorySystem.resolveArtifact(session, artifactRequest);

        return artifactResult.getArtifact().getFile();
    }
}