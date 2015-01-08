var scalaShop = angular.module('scalaShop',
    ['ngRoute', 'ngDialog', 'route-segment', 'view-segment', 'controllers', 'filters', 'services']);

scalaShop.config(['$routeSegmentProvider', '$locationProvider', '$httpProvider',
    function ($routeSegmentProvider, $locationProvider, $httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        $routeSegmentProvider
            .when("/admin", 'admin')
            .when("/admin/brands", "admin.brands")
            .segment('admin', {
                templateUrl: "partials/admin/admin-nav.html"
            })
            .within()
            .segment('brands', {
                templateUrl:"partials/admin/brands-list.html",
                controller: 'BrandsCtrl'
            })
    }
])
