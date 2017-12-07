const express = require('express');
const moment = require('moment');

const app = express();
const port = 3000;

let enabled = true;
let delay = 50;

// middleware: adding delay
app.use((req, res, next) => setTimeout(next, delay));

// manage api
app.post('/api/enable', (req, res) => {
	enabled = true;
	console.log(`Enable API`);
	res.json({
		enabled,
		delay
	});
});
app.post('/api/disable', (req, res) => {
	enabled = false;
	console.log(`Disable API`);
	res.json({
		enabled,
		delay
	});
});
app.post('/api/delay/:time', (req, res) => {
	delay = req.params.time;
	console.log(`Set delay : ${delay}`);
	res.json({
		enabled,
		delay
	});
});

// business

const randomIntBetween = (min, max) => Math.floor(Math.random() * (max - min) + min);
const randomInt = (max) => randomIntBetween(0, max);
const randomItemInArray = (array) => array[randomInt(array.length)];
const randomWeather = () => randomItemInArray(['Sunny', 'Windy', 'Snowy', 'Rainy', 'Cloudy', 'Foggy']);

app.get('/api/weather/:city', (req, res) => {
    const timestamp = moment();
	if (enabled) {
		res.json({
            type: randomWeather(),
            temperature: randomIntBetween(0, 40)
		});
	} else {
		res.status(404).json({
            code: 404,
            message: "Not Found",
            timestamp
        });
	}
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}!`);
});
