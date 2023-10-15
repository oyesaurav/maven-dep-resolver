const express = require('express')

const resolveMavenDependency = require('./utils/dependencyResolver')

const app = express()

app.get('/', (req, res) => {
    res.send('Hello World!')
})


app.get('/maven', (req, res) => {
    
    const groupId = req.query.groupId
    const artifactId = req.query.artifactId
    const version = req.query.version

    if (!groupId || !artifactId || !version) {
        return res.status(400).json({ error: 'Missing parameters' });
    }

    resolveMavenDependency(groupId, artifactId, version, (error, jarPath) => {
        if (error) {
          res.status(500).send({ error: error.message });
        } else {
            console.log(jarPath)
            res.setHeader('Content-Disposition', 'attachment; filename=' + `${artifactId}-${version}.jar`)
            res.sendFile(jarPath)
        }
      });
})

app.listen(3000, () => {
    console.log('Listening on port 3000!')
})
