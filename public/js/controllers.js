var controllers = angular.module('controllers', []);

controllers.controller('BrandsListCtrl', ['$scope','ngDialog', 'Brand',
    function ($scope, ngDialog, Brand) {
        $scope.brands = Brand.query();
        
        $scope.editPopup  = function (brand) {
            ngDialog.open({
                template : 'partials/admin/popup/edit-brand.html',
                data: brand,
                controller: 'EditBrandsCtrl',
                scope: $scope,
                cache: false
            })
        }
        
        $scope.getById = function(brandId){
        	Brand.get({id: brandId}, function(data){alert(JSON.stringify(data))})
        }
        
        $scope.deleteBrand = function(brandId){
        	Brand.delete({id : brandId}, function(data){ 
        		$scope.brands = $scope.brands.filter(
        					function(brand){ return !(brand.id == brandId) }
        				) 
        	})
        }
          
    }
    
]);

controllers.controller('EditBrandsCtrl',['$scope','Brand', function($scope,Brand) {
    $scope.brand = $scope.ngDialogData;
	
	$scope.saveBrand = function(){
    	Brand.save($scope.brand,function(data){
    		$scope.brands.push($scope.brand) // TODO : Add Id to entity
    	})
    }
	
	$scope.editBrand = function(brandId){
		alert(brandId);
		Brand.update({id : brandId} , $scope.brand, function(data){
			alert(JSON.stringify(data))
		})
	}
}])

controllers.controller('ItemsCtrl', ['$scope',
    function ($scope, Phone) {

    }]);


controllers.controller('CategoriesCtrl', function ($scope) {

})



