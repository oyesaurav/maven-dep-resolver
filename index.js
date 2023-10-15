const express = require("express")
const { exec } = require("child_process")
const { check, validationResult } = require("express-validator")

const resolveMavenDependency = require("./utils/dependencyResolver")
const validateMavenCoordinates = require("./middleware/validations")

const app = express()

app.get("/", (req, res) => {
  res.send("Hello World!")
})

app.get("/maven", validateMavenCoordinates, (req, res) => {
  const errors = validationResult(req)
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() })
  }

  const groupId = req.query.groupId
  const artifactId = req.query.artifactId
  const version = req.query.version

  resolveMavenDependency(groupId, artifactId, version, (error, jarPath) => {
    if (error) {
      res.status(500).send({ error: error.message })
    } else {
      res.setHeader("Content-Disposition", "attachment; filename=" + `${artifactId}-${version}.jar`)
      // res.send("done")
      res.sendFile(jarPath)
    }
  })
})

app.listen(3000, () => {
  console.log("Listening on port 3000!")
})
