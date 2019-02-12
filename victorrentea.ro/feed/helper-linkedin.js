

var POSTS_TO_RETRIEVE = 300;

var iframeHtmlList = [];

var menuButtons;
var index = -1;


function openEmbed() {
	index ++;
	if (index == menuButtons.length) {
		copyToClipboard(JSON.stringify(iframeHtmlList));
		return;
	}
	var menuButton = menuButtons[index];
	$(menuButton).click();
	
	setTimeout(copyEmbedCode, 5000);
}

function copyEmbedCode() {
	var iframeHtml = $('textarea[title="Embed code"]').val();
	var $postRoot = $(menuButtons[index]).closest('div.feed-shared-update-v2');
	var socialText = $postRoot.children('.feed-shared-social-counts').text();
	var counts = getAllMatches(socialText, /(\d+)/g);
	var count = counts.reduce(addStr, '0') / 2;	
	var hasImage = $($postRoot).children(".feed-shared-article__image").size() > 0;
	
	var item = {
		count:count ,
		html:iframeHtml,
		hasImage: hasImage
	};
	console.log("Found ", item);
	iframeHtmlList.push(item);
	$('li-icon[type="cancel-icon"]').click();
	openEmbed();
}

function addStr(a, b) {
    return parseInt(a) + parseInt(b);
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


 
function preloadOneMorePage() {
	menuButtons = $('span:contains("Embed this post")');
	if (menuButtons.length >= POSTS_TO_RETRIEVE) {
		openEmbed();	
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






// ------------------------ collect last posts stats

"Total Views: " + $(".content-analytics-entry-point").find("strong").text().replace(/ views/g,";").replace(/,/g,"").split(";").map(function(s) {return parseInt(s);}).reduce(function(a,b) {return a + (b|0);}, 0);


"Total LIkes: " + $("*[data-control-name='likes_count']").find("span[aria-hidden='true']").text().replace(/ Likes/g,";").replace(/,/g,"").split(";").map(function(s) {return parseInt(s);}).reduce(function(a,b) {return a + (b|0)/2;}, 0);
