$(function(){
	$('[data-submenu]').submenupicker();

	function loadPage(href){
		$.get(href, function(data){
			$("#container").html(data)
			
			$(".list-collection").click(function(){
				var target = $(this).data("href")
				loadPage(target)
			})
		})
	}

	loadPage("collections/宪法.html")

	$(".collection").click(function(){
		var target = $(this).data("href")
		loadPage(target)
	})
	
})