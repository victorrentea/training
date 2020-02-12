https://www.linkedin.com/search/results/people/?facetNetwork=%5B%22F%22%5D&keywords=recruiter&origin=FACETED_SEARCH




//function randomNode($q) {
//  return $q.eq(Math.floor(Math.random() * this.length));
//}     

function clickFirst() {		
	setTimeout(removeConn, 10000);	
	//randomNode($(".actor-name"))[0].click();
	$(".actor-name").click();
}


function removeConn() {
	setTimeout(back, 10000);
	//$("span.pv-s-profile-actions__label:contains(Remove Connection)")[0].click();	
	$("*[type='person-remove-icon'").click()
}

function back() {
	setTimeout(clickFirst, 10000);
	
	window.history.back();
	//window.location.url = 'https://www.linkedin.com/search/results/people/?facetNetwork=%5B%22F%22%5D&keywords=recruiter&origin=FACETED_SEARCH'
}
clickFirst();

