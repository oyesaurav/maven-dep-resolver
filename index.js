const express = require('express')

const app = express()

app.get('/', (req, res) => {
    res.send('Hello World!')
})

// Implement a route that accepts GET requests with parameters for Group
// ID, Artifact ID, and Version.
app.get('/maven', (req, res) => {
    
    const groupId = req.query.groupId
    const artifactId = req.query.artifactId
    const version = req.query.version

    if (!groupId || !artifactId || !version) {
        return res.status(400).json({ error: 'Missing parameters' });
    }

    return res.status(200).json({
        groupId,
        artifactId,
        version
    })
})

app.listen(3000, () => {
    console.log('Listening on port 3000!')
})
