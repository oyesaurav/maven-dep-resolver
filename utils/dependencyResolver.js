const { exec } = require('child_process');
const path = require('path');

function resolveMavenDependency(groupId, artifactId, version, callback) {
  // const command = `mvn dependency:get -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dtransitive=false`;
  
  const command = `mvn dependency:copy -Dartifact=${groupId}:${artifactId}:${version} -DoutputDirectory="${path.resolve("", 'assets')}"`;
  
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
      console.log(`stdout: ${stdout}`);
      const jarPath = path.resolve("", 'assets', `${artifactId}-${version}.jar`);
      callback(null, jarPath);
    });
  }
  
module.exports = resolveMavenDependency;

// const command = `mvn dependency:get -DgroupId=junit -DartifactId=junit -Dversion=4.12 -Dtransitive=false`;
  
//     exec(command, (error, stdout, stderr) => {
//       if (error) {
//         console.error(`exec error: ${error}`);
//         return;
//       }
//       console.log(`stdout: ${stdout}`);
//       console.error(`stderr: ${stderr}`);
//     });