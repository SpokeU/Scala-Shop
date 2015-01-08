var controllers = angular.module('controllers', []);

controllers.controller('BrandsCtrl', ['$scope','ngDialog', 'Brand',
    function ($scope, ngDialog, Brand) {
        $scope.brands = Brand.query();
        
        $scope.editPopup  = function (brand) {
            ngDialog.open({
                template : 'partials/admin/popup/edit-brand.html',
                data: brand,
                cache: false
            })
        }
    }
    
]);

controllers.controller('ItemsCtrl', ['$scope',
    function ($scope, Phone) {

    }]);


controllers.controller('CategoriesCtrl', function ($scope) {

})



