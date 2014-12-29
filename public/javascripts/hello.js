function CategoryController($scope, $http) {

	$scope.response = "Hello";

	$scope.addCategoryForm = function(addCategoryUrl) {
		console.log("Adding shit")
	}

}

$(document).ready(function() {

	console.log("Ready to party!")
	
	$("#addCategory").click(function() {
		console.log("click")
		$("#addCategoryForm").slideToggle()
	})

	$("#tree1").treeview({
		onNodeClick : function(node) {
			console.log(node);
		}
	});

})
