$(document).ready(function(){
    $("#coffeeCall").on("click", function(){
        $.ajax({
            url: "/call",
            method: "POST",
            data: {
                callForCoffee: true,
                urgency: 100
            },
            success: function(response)
            {
                resetText();
                showStatus(response);
            }
        });
    });
});

var showStatus = function(statusText) {
    document.getElementById("status").innerHTML = statusText;
    $("#status").fadeIn(1000);
    setTimeout(function() {
        $("#status").fadeOut(1000);
    }, 10000);
}

var texts = [
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "I need Coffee!",
    "Coffeein!",
    "I need T&uuml;rkentrunk!",
    "Gimme Coffee!"
];

var setText = function(text) {
    $("#coffeetext").html(text);
};

var getRandomNumber = function(min, max) {
    var randomInt = Math.floor(Math.random()*10000);
    return min+randomInt%Math.abs(max-min)+min
}

var getRandomText = function() {
    return texts[getRandomNumber(0, texts.length)];
};

var resetText = function() {
    setText(getRandomText());
};
