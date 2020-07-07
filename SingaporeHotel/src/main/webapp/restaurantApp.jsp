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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Search Hotel</title>
<script type="text/javascript">
$(document).ready(function(){
	  $('[data-toggle="tooltip"]').tooltip();
	});
	var data = [];
	var totalHotel = 0;
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
					//data = response.data;
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
				url : 'http://localhost:9596/restaurantservice/getAllRestaurants/'
			}).then(function successCallback(response) {
				data = response.data;
				$scope.hotellist = data;
				loadData();
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
function loadData(){
	var myHotelContainer  = document.getElementById("myHotelContainer");
	var rowDiv = "<div class='row'>";
	var lineNo = 0;
	var hotelBoxLineNo = 1;
	var rows = 0;
	var column = 3;
	if(data!=undefined){
		var jsonLength = data.length;
		rows = jsonLength/column;
		var hotelRow = ""; 
			for(var k=0;k<rows;k++){
					hotelRow ="<div class='row'>";
					var colCounter = 0;
					for(var i=lineNo;i<jsonLength;i++){
						var branName = data[i]["restaurant_Name"];
						var variety = data[i]["cuisines"];
						var has_Table_book = data[i]["has_Table_book"];
						var hasOnlineDel = data[i]["has_Online_del"];
						var average_Cost = data[i]["average_Cost"];
						var country = data[i]["currency"];
						var stars = data[i]["aggregate_rating"];
						var votes = data[i]["votes"];
						if(stars=='NaN')stars='0';
						var topTen = data[i]["rating_text"];
						var hasOnlineText = "";
						if(hasOnlineDel!="" && hasOnlineDel!=undefined){
							if(hasOnlineDel=='Yes')hasOnlineText = "This restaurant has online delivery service ";
							else if(hasOnlineDel=='No')hasOnlineText = "online delivery service is not available "
						}
						if(has_Table_book!="" && has_Table_book!=undefined){
							if(has_Table_book=='Yes')hasOnlineText += " and restaurant has table booking service.";
							else if(has_Table_book=='No')hasOnlineText += " and table booking service is not available."
						}
						if(topTen=='NaN')topTen='0';
						var starHtml = "";
						for(starC=0;starC<5;starC++){
							var checked = "fa-star-o checked";
							if(stars!=0){
								if(stars>=1){
									checked = "checked";
								}else if(stars>0){
									checked = "fa-star-half-o";
								}
								stars--;
							}
							starHtml +='<span class="fa fa-star '+checked+'"></span>';
						}
						
						hotelRow += '<div class="col-sm-4 restBook"><div class="panel panel-primary"><div class="panel-heading">'+ branName +' <i class="fa fa-cutlery" aria-hidden="true"></i></div><div class="panel-body"><span>'+ variety +'</span><br/><a href="#">'+ country +'</a></div><div class="panel-footer">'+starHtml+''+topTen+'<br/><span>'+average_Cost+' FOR TWO</span></div><div class="quickViewDiv"><span role="button" aria-label="Open" class="_2ECk4 _24tlh"><a href="#" data-toggle="tooltip" title="'+hasOnlineText+'">Quick View</a></span></div></div></div>';
						lineNo++;
						totalHotel++;
						colCounter++;
						if(colCounter==3){
							hotelRow += '</div>';
							break;
						}
					}
					myHotelContainer.innerHTML += hotelRow;
			}
			hotelRow = "";
			document.getElementById("totalRestDivC").innerHTML+= totalHotel+" restaurants"
	}
}
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
.restBook:hover {
    border-color: #d3d5df;
    box-shadow: 0 4px 7px 0 rgba(218,220,230,.6);
}
.restBook {
    padding: 20px 20px 57px;
    border: 1px solid #fff;
    contain: content;
}
.col-sm-4:hover+.quickViewDiv {
    visibility: visible;
     border-color: #d3d5df;
    box-shadow: 0 4px 7px 0 rgba(218,220,230,.6);
}

.details-div {
    display: none;
}

.details-div:hover {
    display: inline-block;
}
 a {
      text-decoration:none;
   }
</style>
<style>
.checked {
  color: orange;
}
.fa-star-half-o {
  color: orange;
}

</style>
<style>
.topRestMainDiv {
    height: 72px;
    position: relative;
}
.topRestInnerDiv {
    height: 72px;
    transform: translateZ(0);
    z-index: 999;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
}
._1H8qJ, .totFilterContainer {
    position: relative;
}
.totFilterContainer {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
}
.topResInnerDiv2, .totFilterContainer {
    height: 100%;
}
.totRestaurant {
    font-weight: 600;
    font-size: 28px;
    -ms-flex: 1;
    flex: 1;
}
.restFilterDiv, .restFilterInner {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
}
.restFilterInner {
    margin-top: 9px;
}
.restFilterDiv:hover, ._3hfyI {
    color: #3d4152;
}
._3bkpC {
    font-weight: 500;
    color: #282c3f;
    margin-right: 15px;
    will-change: opacity;
    opacity: 0;
}
.restFilterDiv {
    font-size: 16px;
    font-weight: 300;
    text-transform: capitalize;
    color: #686b78;
    margin-left: 35px;
    cursor: pointer;
    position: relative;
}
.restFilterDiv, .restFilterInner {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-align: center;
    align-items: center;
}
.quickViewDiv {
    color: #686b78;
    font-size: 13px;
    visibility: visible;
    border-top: 1px solid #e9e9eb;
    padding-top: 14px;
    margin-top: 14px;
    position: absolute;
    left: 20px;
    right: 20px;
    bottom: 14px;
}
._24tlh {
    text-align: center;
    float: none;
    display: block;
    margin: 0 auto;
    width: 100px;
}
._2ECk4 {
    color: #5d8ed5;
    float: right;
    text-transform: uppercase;
    font-weight: 600;
}
</style>
<script>

</script>
</head>

<body ng-app="HotelManagement" ng-controller="AppController">
	<div class="container">
		<div class="well" align="center">Hotel Search</div>
		<form ng-submit="submitSearch()">
			<div class="form-group">
				<input type="text" class="form-control" ng-model='hotelName'
					placeholder="enter hotel name to search" />
			</div>
			<div class="form-group" align="center">
				<input type="button" value="Search" ng-model='hotelsearch'
					class="btn btn-primary" ng-click="_searchHotel();" />
			</div>
		</form>
		<div class="form-group" align="center">
			<label for="comment">Filter By Region::</label> <select
				id="regionList" oninput="filterByRegion();"
				ng-model="selectedRegion"
				ng-options="hotel.neighbourhood_group for hotel in hotellist | filter: filterExpression">
				<option value="">Select</option>
			</select>
		</div>
		<div class="topRestMainDiv">
			<div class="topRestInnerDiv">
				<div class="topResInnerDiv2">
					<div class="totFilterContainer">
						<div class="totRestaurant" id="totalRestDivC"></div>
						<div class="restFilterInner">
							<div class="restFilterDiv _3hfyI">Relevance</div>
							<div class="restFilterDiv">Cost For Two</div>
							<div class="restFilterDiv">Delivery Time</div>
							<div class="restFilterDiv">Rating</div>
							<div class="restFilterDiv">
								<span class="filterButton">Filters</span>
								<div class="filterIconDiv">
									<span class="icon-filter _1PoQ7"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container" id="myHotelContainer"></div>
		<br>
		<br>

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
					<th><a href="#"
						ng-click="orderByField='price'; reverseSort = !reverseSort"
						style="color: white;"> Price<span
							ng-show="orderByField == 'price'"><span
								ng-show="!reverseSort">^</span><span ng-show="reverseSort">v</span></span>
					</a></th>
				</tr>


			</table>
		</div>
	</div>


</body>

</html>
