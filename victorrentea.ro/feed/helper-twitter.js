

var permaLinks = [];

function addStr(a, b) {
    return parseInt(a) + parseInt(b);
}

function extractPermalinks() {
	permaLinks = $(".tweet[data-permalink-path]").map(function(){ 
		var $tweet = $(this);
		var socialRawText = $tweet.find(".ProfileTweet-actionCountForPresentation").map(function(){
			return $(this).text();
		  }).get().join(',');
		var socialTexts = getAllMatches(socialRawText, /(\d+)/g)
		//alert(socialTexts);
		var item = {
			permaLink: $tweet.attr("data-permalink-path"),
			count: socialTexts.reduce(addStr, '0')
		};
		// console.log("Extracted ", item);
		return item;
	}).get();

	alert("Will copy to clipboard.")
	copyToClipboard(JSON.stringify(permaLinks));
	alert("Copied to clipboard");
}





 function getAllMatches(s, re) {
     var rez = [];
     var m;
     do {
         m = re.exec(s);
         if (m) {
             rez.push(m[1]);
             
         }
     } while (m);
     return rez;
 }

var POSTS_TO_RETRIEVE = 400;
 
function preloadOneMorePage() {
	menuButtons = $('.tweet');
	if (menuButtons.length >= POSTS_TO_RETRIEVE) {
		extractPermalinks();	
	} else {
		window.scrollTo(0,document.body.scrollHeight);
		setTimeout(preloadOneMorePage, 3000);
	}
}

 
preloadOneMorePage();


function copyToClipboard(text){
    var dummy = document.createElement("input");
    document.body.appendChild(dummy);
    dummy.setAttribute('value', text);
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);
}

