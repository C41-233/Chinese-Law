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
		
		$("#search").on("input", function(e){
			search()
		})
	}
	
	function processDeprecated(){
		if(includeDeprecated){
			$(".deprecated").show()
		}
		else{
			$(".deprecated").hide()
		}
	}
	
	function search(){
		var val = $("#search").val()
		function match(dom){
			var d_name = dom.html()
			if(val && d_name.indexOf(val) < 0){
				return false
			}
			
			return true
		}
		
		$("#div-search-laws>div").each(function(){
			if(match($(this))){
				$(this).show()
			}
			else{
				$(this).hide()
			}
		})
	}
	
	loadPage("search.html")
})
