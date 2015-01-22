angular.module('controllers').controller('CategoriesListCtrl',['$scope', 'ngDialog', 'Category',
				function($scope, ngDialog, Category) {
					$scope.message = 'Hello categories';
				}]);
