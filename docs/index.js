$(function(){
	$('[data-submenu]').submenupicker();

	function loadPage(href){
		$.get(href, function(data){
			$("#container").html(data)
			init()
		})
	}

	var includeDeprecated = false
	function init(){
		$(".list-collection").click(function(){
			var target = $(this).data("href")
			loadPage(target)
		})
		$(".check-deprecated input").attr("checked", includeDeprecated).change(function(){
			var checked = $(this).is(":checked")
			includeDeprecated = checked
			processDeprecated()
		})
		processDeprecated()
	}
	
	function processDeprecated(){
		if(includeDeprecated){
			$(".deprecated").show()
		}
		else{
			$(".deprecated").hide()
		}
	}
	
	loadPage("collections/宪法-宪法.html")
})