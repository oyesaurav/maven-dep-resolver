const { exec } = require('child_process');
const path = require('path');

const regex = /Downloaded JAR file at: (.+)/;

function resolveMavenDependency(groupId, artifactId, version, callback) {

  const command = `java -cp target/local-repo/cp_d5msybub486dzu07jq5dj4hio.jar com.secureblink.App ${groupId} ${artifactId} ${version}`;

    exec(command, (error, stdout, stderr) => {
      if (error) {
        console.error(`exec error: ${error}`);
        callback(error, null);
        return;
      }
      if (stderr) {
        console.error(`stderr: ${stderr}`);
        callback(new Error(stderr), null);
        return;
      }
      const matches = stdout.match(regex);
      console.log(matches[1])
      const jarPath = matches[1]
      callback(null, jarPath);
    });
}
  

// function resolveMavenDependency(groupId, artifactId, version, callback) {
//   // const command = `mvn dependency:get -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dtransitive=false`;
  
//   const command = `mvn dependency:copy -Dartifact=${groupId}:${artifactId}:${version} -DoutputDirectory="${path.resolve("", 'assets')}"`;
  
//     exec(command, (error, stdout, stderr) => {
//       if (error) {
//         console.error(`exec error: ${error}`);
//         callback(error, null);
//         return;
//       }
//       if (stderr) {
//         console.error(`stderr: ${stderr}`);
//         callback(new Error(stderr), null);
//         return;
//       }
//       console.log(`stdout: ${stdout}`);
//       const jarPath = path.resolve("", 'assets', `${artifactId}-${version}.jar`);
//       callback(null, jarPath);
//     });
//   }
  
module.exports = resolveMavenDependency;
