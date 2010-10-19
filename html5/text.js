const WIDTH = 600;
const HEIGHT = 600;
const INTERVAL = 20;
const SQUARE_WIDTH = 200;
const INCREMENT = 10;
const WHITE = "#fff";
const BLACK = "#000";
const FONT = "120pt Helvetica";
const WORDS = ["Hello", "World !"];

var ctx;
var posX = 200;
var tabPos = 0;

function init() {
	ctx = document.getElementById("text").getContext("2d");
    ctx.font = FONT;
	return setInterval(draw, INTERVAL);
}

function draw() {
	clear();
	if (posX >= WIDTH) {
		posX = -SQUARE_WIDTH;
        if (tabPos == (WORDS.length - 1))
		    tabPos = 0;
        else
		    tabPos++;
	}
	posX += INCREMENT;
	ctx.fillStyle = BLACK;
	ctx.fillRect(posX, (HEIGHT / 2) - (SQUARE_WIDTH / 2), SQUARE_WIDTH, SQUARE_WIDTH);
	ctx.fillStyle = WHITE;
  	ctx.fillText(WORDS[tabPos], 50, 340);
}

function clear() {
    ctx.fillStyle = WHITE;
	ctx.fillRect(0, 0, WIDTH, HEIGHT);
}
