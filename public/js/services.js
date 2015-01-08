var services = angular.module('services', ['ngResource']);

services.factory('Brand', function ($resource) {
	return $resource('http://localhost:9000/brands');
})
