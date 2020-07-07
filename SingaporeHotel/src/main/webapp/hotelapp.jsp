<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<title>Search Hotel</title>
<script type="text/javascript">
	var app = angular.module("HotelManagement", []);
	 	
	app.controller("AppController", function($scope, $http) {
		$scope.hotellist = [];
		 $scope.hotelForm = {
			hotelName : ""
		};
		 $scope.orderByField = 'price';
		  $scope.reverseSort = false;
		//load the data from server
		_refreshHotelData();
		$scope.submitSearch = function() {
			var method = "";
			var url = "";
			if ($scope.hotelForm.hotelName == "") {
				//hotel name is absent in form data, it is get all hotel list operation
				method = "GET";
				url = '/getAllHotels';
			} else {
				//NAME is present in form data, it is SEARCH operation
				method = "GET";
				url = '/getHotelByName';
			}
		};
		
		$scope._searchHotel = function() {
			$http({
				method : 'GET',
				url : 'http://localhost:9596/hotelservice/getHotelByName/' + $scope.hotelName
			}).then(function successCallback(response) {
				if(response.data.length>0)
					$scope.hotellist = response.data;
				else alert("Hotel with entered name is not present,try with correct name.")
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
		/* Private Methods */
		//HTTP GET- get all hotels collection
		function _refreshHotelData() {
			$http({
				method : 'GET',
				url : 'http://localhost:9596/hotelservice/getAllHotels/'
			}).then(function successCallback(response) {
				$scope.hotellist = response.data;
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
		
		function _success(response) {
			_refreshHotelData();
			_clearFormData();
		}

		function _error(response) {
			console.log(response.statusText);
		}

		//Clear the form
		function _clearFormData() {
			$scope.hotelForm.hotelName = "";
		}
		;
	});
</script>
<script>
function filterByRegion() {
	  var input, filter, table, tr, td, i;
	  var inputField = document.getElementById("regionList");
	  var x = inputField.selectedIndex;
	  var y = inputField.options;
	  var value = y[x].text;
	  filter = value.toUpperCase();
	  table = document.getElementById("dataTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 1; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}
</script>
<style>
.blue-button {
	background: #25A6E1;
	filter: progid: DXImageTransform.Microsoft.gradient( startColorstr='#25A6E1',
		endColorstr='#188BC0', GradientType=0);
	padding: 3px 5px;
	color: #fff;
	font-family: 'Helvetica Neue', sans-serif;
	font-size: 12px;
	border-radius: 2px;
	-moz-border-radius: 2px;
	-webkit-border-radius: 4px;
	border: 1px solid #1A87B9
}

.red-button {
	background: #CD5C5C;
	padding: 3px 5px;
	color: #fff;
	font-family: 'Helvetica Neue', sans-serif;
	font-size: 12px;
	border-radius: 2px;
	-moz-border-radius: 2px;
	-webkit-border-radius: 4px;
	border: 1px solid #CD5C5C
}

table {
	font-family: "Helvetica Neue", Helvetica, sans-serif;
	width: 50%;
}

caption {
	text-align: left;
	color: silver;
	font-weight: bold;
	text-transform: uppercase;
	padding: 5px;
}

th {
	background: SteelBlue;
	color: white;
}

tbody tr:nth-child(even) {
	background: WhiteSmoke;
}

tbody tr td:nth-child(2) {
	text-align: center;
}

tbody tr td:nth-child(3), tbody tr td:nth-child(4) {
	text-align: center;
	font-family: monospace;
}

tfoot {
	background: SeaGreen;
	color: white;
	text-align: right;
}

tfoot tr th:last-child {
	font-family: monospace;
}

td, th {
	border: 1px solid gray;
	width: 25%;
	text-align: left;
	padding: 5px 10px;
}
</style>
<head>

<body ng-app="HotelManagement" ng-controller="AppController">
<div class="container">
	<div class="well" align="center">Hotel Search</div>
	<form ng-submit="submitSearch()">
		<div class="form-group">
  			<input type="text" class="form-control" ng-model='hotelName' placeholder="enter hotel name to search"/>
  		</div>
		<div class="form-group" align="center">
  			<input type="button" value="Search" ng-model='hotelsearch' class="btn btn-primary" ng-click="_searchHotel();"/>
  		</div>
	</form>
	<div class="form-group" align="center">
  		<label for="comment">Filter By Region::</label>
 		<select id="regionList" oninput="filterByRegion();" ng-model="selectedRegion" ng-options="hotel.neighbourhood_group for hotel in hotellist | filter: filterExpression">
             <option value="">Select</option>
  		</select>
	</div>
	<div class="table-responsive">
	<table id="dataTable" class="table">
		<tr>
			<th>Hotel Name</th>
			<th>Host Name</th>
			<th>Neighbourhood Group</th>
			<th>Neighbourhood</th>
			<th>Latitude</th>
			<th>Longitude</th>
			<th>Room Type</th>
			<th><a href="#" ng-click="orderByField='price'; reverseSort = !reverseSort" style="color: white;">
          	Price<span ng-show="orderByField == 'price'"><span ng-show="!reverseSort">^</span><span ng-show="reverseSort">v</span></span>
          </a></th>
		</tr>
		<tr ng-repeat="hotel in hotellist |orderBy:orderByField:reverseSort">
			<td>{{ hotel.name }}</td>
			<td>{{ hotel.host_name }}</td>
			<td>{{ hotel.neighbourhood_group }}</td>
			<td>{{ hotel.neighbourhood }}</td>
			<td>{{ hotel.latitude }}</td>
			<td>{{ hotel.longitude }}</td>
			<td>{{ hotel.room_type }}</td>
			<td>{{ hotel.price }}</td>
		</tr>

	</table>
	</div>
	</div>
</body>

</html>
