


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
	var iframeHtml = $('textarea.embed-destination').val();
	//var socialText = $(menuButtons[index]).closest('div.feed-shared-update-v2').children('.feed-shared-social-counts').text();
	//var counts = getAllMatches(socialText, /(\d+)/g);
	var count = 0;// counts.reduce(addStr, '0') / 2;	
	iframeHtmlList.push({count:count ,html:iframeHtml});
	$('button.modal-btn.modal-close.js-close').click();
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

var POSTS_TO_RETRIEVE = 20;
 
function preloadOneMorePage() {
	menuButtons = $('.embed-link');
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

