const apm = require('elastic-apm-node').start({
    serviceName: 'nodejs-backend',
    serverUrl: 'http://localhost:8200'
})

const express = require('express')
const app = express()

var sleep = require('thread-sleep');

app.get('/long-running', (req, res) => {
    console.log("Long running task called")
    sleep(4000);

    res.json({
        result: "It took long!"
    });
});

app.get('/short-running', (req, res) => {
    res.json({
        result: "It took short!"
    });
});

// Listen and inform
app.listen(3031, () => {
    console.log('Express server listening on port 3031');
});
