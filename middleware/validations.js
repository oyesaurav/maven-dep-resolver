const { check } = require('express-validator');


const validateMavenCoordinates = [
    check('groupId').matches(/^[a-zA-Z_][\w\.-]*$/).withMessage('Invalid Group ID'),
    check('artifactId').matches(/^[a-zA-Z_][\w\.-]*$/).withMessage('Invalid Artifact ID'),
    check('version').matches(/^[a-zA-Z0-9\.-]+$/).withMessage('Invalid Version'),
];
  

module.exports = validateMavenCoordinates;