angular.module('services', [ 'ngResource' ]).
		factory('Brand',
			function($resource) {
				return $resource('http://localhost:9000/brands/:id', {
					id : '@_id'
				}, {
					update : {
						method : 'PUT'
					}
				});
			}).factory('Item', function($resource) {
					return $resource('http://localhost:9000/items/:id', {
						id : '@_id'
					}, {
						update : {
							method : 'PUT'
						}
					})
			}).factory('Category', function($resource){
				return $resource('http://localhost:9000/items/:id',{
					id : '@_id'
				}, {
					update : 'PUT'
				})
			})
