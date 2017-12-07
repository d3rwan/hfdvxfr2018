const express = require('express');
const moment = require('moment');
const ads = require('./ads.json')

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
const randomAd = () => randomItemInArray(ads);

app.get('/api/ad/:city', (req, res) => {
    const timestamp = moment();
	if (enabled) {
	    const city = req.params.city;
		res.json([randomAd(), randomAd(), randomAd()]);
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
