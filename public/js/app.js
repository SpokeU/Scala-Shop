var scalaShop = angular.module('scalaShop',
    ['ngRoute', 'ngDialog', 'route-segment', 'view-segment', 'controllers', 'filters', 'services']);

scalaShop.config(['$routeSegmentProvider', '$locationProvider', '$httpProvider',
    function ($routeSegmentProvider, $locationProvider, $httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        $routeSegmentProvider
            .when("/admin", 'admin')
            .when("/admin/brands", "admin.brands")
            .when("/admin/items","admin.items")
            .when("/admin/categories","admin.categories")
            .segment('admin', {
                templateUrl: "partials/admin/admin-nav.html"
            })
            .within()
            .segment('brands', {
                templateUrl:"partials/admin/brands-list.html",
                controller: 'BrandsListCtrl'
            }).segment('items', {
                templateUrl:"partials/admin/items-list.html",
                controller: 'ItemsListCtrl'
            }).segment('categories',{
            	templateUrl:"partials/admin/categories-list.html",
            	controller: 'CategoriesListCtrl'
            })
    }
])
