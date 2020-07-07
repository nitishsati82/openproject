<html>
  <head>
    <title>Todo App</title>
     <link rel="icon" href="resources/css//todo.png">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/jquery-ui.min.css">
    <link rel="stylesheet" href="resources/css/custom.css">
    <link rel="stylesheet" href="resources/css/pnotify.custom.min.css">
  <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="resources/js/apps.js"></script>
    <script type="text/javascript" src="resources/js/validate.js"></script>
    <script src="resources/js/angular.js"></script>
    <script src="resources/js/pnotify.custom.js"></script>
  <script>
  $(document).ready(function(){
	  startOnLoadToDo();
	 });
  
  </script>
  <style>
  
  </style>
  <script type="text/javascript">
	var data = [];
	var totalHotel = 0;
	var app = angular.module("ToDoList", []);
	app.controller("AppController", function($scope, $http) {
		function _refreshDefLabel() {
			var created_by = document.getElementById("created_by");
			var sessionKey = document.getElementById("sessionKey");
			$http({
				method : 'GET',
				url : "http://localhost:9596/labelservice/getDefaultLabels/"+created_by.value+"/"+sessionKey.value  
			}).then(function successCallback(response) {
				LABEL_ARRAY = response.data;
				loadLabels();
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		$scope._createLabel = function() {
			var created_by = document.getElementById("created_by").value;
			var sessionKey = document.getElementById("sessionKey").value;
			var dataObj = {"is_deleted":"N","id":"","label_name":document.getElementById("labelText").value,"created_by":created_by,"user_session":sessionKey};
			$http({
				method : 'POST',
				data : dataObj,
				dataType : "json",
		       contentType : "application/json",
				url : 'http://localhost:9596/labelservice/createNewLabel/'
			}).then(function successCallback(response) {
				if(response.data.id!=""){
					alert("Label Created..");
					$('#addNewLabelModal').modal('hide');
					_refreshDefLabel();
				}else{
					alert("Error in label creation.");
				}
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		$scope._addTaskTable = function() {
			var created_by = document.getElementById("created_by").value;
			if($scope.taskName=='' || $scope.taskName==undefined){
				alert("Kindly enter task");
				return false;
			}
			var sessionKey = document.getElementById("sessionKey").value;
			var taskDefLabell= $scope.task_Label;
			if(taskDefLabell==undefined || taskDefLabell==''){
				taskDefLabell= "Inbox";
			}
			taskPriority_EDIT
			var dataObj = {"user_index":created_by,"task_name":$scope.taskName,"task_label":taskDefLabell,"task_due_date":$scope.taskDueDate,"task_notes":$scope.task_notes,"user_session":sessionKey,"task_priority":$scope.taskPriority_drop};
			$http({
				method : 'POST',
				data : dataObj,
				dataType : "json",
		       contentType : "application/json",
				url : 'http://localhost:9596/todoservice/createToDo/'
			}).then(function successCallback(response) {
				if(response.data.id>1){
					document.getElementById("addToDo_Form").reset();
					$('#myModal').modal('hide');
					$('#myTaskDetails').hide();
					alert("Task created in label "+response.data.task_label);
				}else if(response.data.user_session=='Invalid'){
					alert("Invalid session");
					location.reload();
				}else{
					alert(response.data.user_session);
				}
				
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		/*update todo */
		$scope._updateToDo = function() {
			var created_by = document.getElementById("created_by").value;
			var sessionKey = document.getElementById("sessionKey").value;
			var dueDate = document.getElementById("taskDueDate_edit").value;
			var taskName = document.getElementById("taskText_edit").value;
			var taskLabel = document.getElementById("taskLabel_edit").value;
			var task_id = document.getElementById("task_id").value;
			if(dueDate==undefined || dueDate=='')dueDate="";
			if(taskName==undefined || taskName==''){
				alert("Task name can not be empty.");
				return false;
			}
			
			if($scope.taskName==''){
				alert("Kindly enter task");
				return false;
			}
			var dataObj = {"id":task_id,"user_index":created_by,"task_name":taskName,"task_label":taskLabel,"task_due_date":dueDate,"task_notes":$scope.task_notes_edit,"user_session":sessionKey,"task_priority":$scope.taskPriority_drop_EDIT};
			$http({
				method : 'POST',
				data : dataObj,
				dataType : "json",
		       contentType : "application/json",
				url : 'http://localhost:9596/todoservice/updateToDo/'
			}).then(function successCallback(response) {
				if(response.data.id>1){
					_refreshDefLabel();
					alert("Task updated in label "+response.data.task_label);
					$('#myTaskDetails').hide();
					$('#myEditModal').modal('hide');
					
				}else if(response.data.user_session=='Invalid'){
					alert("Invalid session");
					location.reload();
				}else{
					alert(response.data.user_session);
				}
				
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		$scope._userSignUp = function() {
			if($scope.user_name_signup=='' || $scope.user_passowrd_signup=='' || $scope.email_signup==''){
				alert("Kindly fill all required fileds.");
				return false;
			}
			var dataObj = {"user_name":$scope.user_name_signup,"user_password":$scope.user_passowrd_signup,"email":$scope.email_signup};
			$http({
				method : 'POST',
				data : dataObj,
				dataType : "json",
		       contentType : "application/json",
				url : 'http://localhost:9596/userservice/signup/'
			}).then(function successCallback(response) {
				var res = response.data.split(",");
				if(res[0]=='N'){
					alert(res[1]);
					return false;
				}else if(res[0]=='Y'){
					alert("User signup successfully, Kindly use "+res[1]+" for login.");
					$("#signUp").modal('hide');
					document.getElementById("signUpForm").reset();
				}else{
					alert(res[1]);
				}
				
				
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		
		$scope._userLogin = function() {
			var dataObj = {"email":$scope.login_id,"user_password":$scope.user_passowrd};
			if($scope.login_id==undefined || $scope.undefined==''){
				alert("Kindly enter email/password.");
				return false;
			}else if($scope.login_id=='' || $scope.user_passowrd==''){
				alert("Kindly enter email/password.");
				return false;
			}
			$http({
				method : 'POST',
				data : dataObj,
				dataType : "json",
		       contentType : "application/json",
				url : 'http://localhost:9596/userservice/login/'
			}).then(function successCallback(response) {
				if(response.data!=null){
					if(response.data.login_success=='Y'){
						$scope.user_index = response.data.id;
						loginUser(response.data);
						_refreshDefLabel();
						$('#fileList tbody').empty();
						$('#inProgress tbody').empty();
						$('#completed tbody').empty();
					}else{
						alert(response.data.login_success);
						return false;
					}
				}
			}, function errorCallback(response) {
				_error(response);
			});
		}
		
		function _error(response) {
			console.log(response.statusText);
		}
	});
</script>
  </head>
  <body ng-app="ToDoList" ng-controller="AppController">
  <form ng-model="main_form">
  	<input type="hidden"id="created_by" ng-model="user_index">
  	<input type="hidden" id="sessionKey" ng-model="user_session">
  </form>
  
  <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
     <a class="navbar-brand" href="#"><img src="resources/css//todo.png" style="height: 20px;">TODOApp</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
	  <li id="addNewLabel" style="display:none"><a data-toggle="modal" data-target="#addNewLabelModal">Add new label</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right" id="userListRight">
      <li id="signUpLink"><a href="#" data-toggle="modal" data-target="#signUp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      <li id="loginlink"><a href="#" data-toggle="modal" data-target="#loginModal"><span class="glyphicon glyphicon-log-in" ></span> Sign in</a></li>
      <li id="logOutLink" style="display:none"><a href="#" onclick="logout();"><span class="glyphicon glyphicon-log-out"></span> Sign out</a></li>
    </ul>
  </div>
</nav>
<div class="container" id="startOrganize">
  <div class="jumbotron">
    <h2>Organise your daily schedule and Tasks</h2>
    <button type="button" class="btn btn-primary btn-lg" style="text-align: center;" data-toggle="modal" data-target="#loginModal">Organise Tasks</button>
  </div>
  
  
</div>
<div id="mainDiv" style="display:none;">
		<div class="col-md-4">
			<div class="leftMenu">
				<form ng-model="leftt_form">
					<ul class="list-group" id="labelList">

					</ul>
				</form>

			</div>
		</div>
		<div class="col-md-8" id="LabelListLeft">
			<div class="myContainer">
				<p>
					<label for="new-task">Add a Task</label>
					<button type="button" class="btn btn-info" data-toggle="modal"
						data-target="#myModal">Add a task</button>
				</p>
				<div id="myTaskDetails" style="display: none">
					<a class="pointer"><h3>
							<span id="newExpand" data="newdDiv"
								onclick="explandCollapseTab(this);">new [+]</span>
						</h3></a>
					<div id="newdDiv" style="display: none;">
						<table id="fileList"></table>
					</div>

					<a class="pointer"><h3>
							<span id="progrExpand" data="inProgressdDiv"
								onclick="explandCollapseTab(this);">In Progress [+]</span>
						</h3></a>
					<div id="inProgressdDiv" style="display: none;">
						<table id="inProgress"></table>
					</div>

					<a class="pointer"><h3>
							<span id="compExpand" data="completedDiv"
								onclick="explandCollapseTab(this);">Completed [+]</span>
						</h3></a>
					<div id="completedDiv" style="display: none;">
						<table id="completed"></table>
					</div>
				</div>


			</div>

		</div>
	</div>


<!-- modal pop up html -->
    <!-- add pop up html -->
	<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">TODO</h4>
        </div>
        <form ng-model="addToDoForm" id="addToDo_Form">
         	<div class="modal-body" id= "modal-body-edit">
           <div class="form-group">
		   <input id="taskText" ng-model="taskName" class="form-control inputBox" type="text" placeholder="e.g.birthday, shopping etc." onchange="validateField(this);" onblur="validateField(this);">
		  <input id="taskDueDate" ng-model="taskDueDate" class="form-control inputBox dateTime" type="text" placeholder="due date" maxlength="10">
		  <select class="form-control" id="taskLabel" ng-model="task_Label" style=" order-radius: 0px !important;">
				<option value="" selected>--Select--</option>
		</select>
		<select class="form-control" id="taskPriority" ng-model="taskPriority_drop" style=" order-radius: 0px !important;">
				<option value="" selected>--Select--</option>
				<option value="1" selected>LOW</option>
				<option value="2" selected>MEDIUM</option>
				<option value="3" selected>HIGH</option>
		</select>
		<textarea id="" ng-model="task_notes" class="form-control inputBox dateTime" maxlength="500"></textarea>
		  </div>
		   <button type="button" class="btn btn-success" ng-click="_addTaskTable();">Add Task</button>
		   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div>
         </form>
      </div>
      
    </div>
  </div>
  <!-- edit pop up html -->
  <div class="modal fade" id="myEditModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">TODO</h4>
        </div>
        <form ng-model="updateModelForm" id="updateModel_form">
	        <div class="modal-body">
	           <div class="form-group">
			  <input id="taskIddEdit"  type="hidden">
			  <input id="task_id" ng-model="task_id" type="hidden">
			  <input id="tableId" type="hidden">
			   <input id="taskText_edit" ng-model="taskName_edit" class="form-control inputBox" type="text" placeholder="e.g.birthday, shopping etc." onchange="validateField(this);" onblur="validateField(this);">
		  <input id="taskDueDate_edit" ng-model="taskDueDate_edit" class="form-control inputBox dateTime" type="text" placeholder="due date" maxlength="10">
		  <input class="form-control inputBox" id="taskLabel_edit" ng-model="task_Label_edit" disabled="true">
		  <select class="form-control" id="taskPriority_EDIT" ng-model="taskPriority_drop_EDIT" style=" order-radius: 0px !important;">
				<option value="" selected>--Select--</option>
				<option value="1" selected>LOW</option>
				<option value="2" selected>MEDIUM</option>
				<option value="3" selected>HIGH</option>
		</select>
		 <textarea id="taskNotes_edit" ng-model="task_notes_edit" class="form-control inputBox dateTime" maxlength="500"></textarea>
			  </div>
			   <button type="button" class="btn btn-success" ng-click="_updateToDo();">Update Task</button>
			   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
	        </div>
        </form>
      </div>
      
    </div>
  </div>
    <!-- add label pop up html -->
  <div class="modal fade" id="addNewLabelModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Create new label</h4>
        </div>
        <div class="modal-body">
           <div class="form-group">
		   <input id="labelText" ng-model='labelName' class="form-control inputBox" type="text" placeholder="e.g.birthday, shopping etc." onchange="validateField(this);" onblur="validateField(this);">
		  </div>
		   <button type="button" class="btn btn-success" ng-click="_createLabel();">Create new label</button>
		   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div>
      </div>
      
    </div>
  </div>
  
    <!-- login pop up html -->
  <div class="modal fade" id="loginModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <form ng-model="loginForm" id="login_form">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Login</h4>
        </div>
        <div class="modal-body">
           <div class="form-group">
		   <input ng-model="login_id" class="form-control inputBox" type="text" placeholder="email id" onchange="validateField(this);" onblur="validateField(this);">
		   <input ng-model="user_passowrd" class="form-control inputBox" type="password" placeholder="password" onchange="validateField(this);" onblur="validateField(this);">
		  </div>
		   <button type="button" class="btn btn-success" ng-click="_userLogin();">Login</button>
		   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div>
        <div class="modal-footer">
        	<span>Don't have an account?<button type="button" class="btn btn-link" id='noAccount'>Sign up</button></span> 
		  <button type="button" class="btn btn-link" id='forgotPassword'>Forgot Password</button>
        </div>
      </div>
      </form>
    </div>
  </div>
  
  <!-- forget password -->
  <div class="modal fade" id="forgetPasswordModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <form ng-model="recov_Form" id="recov_form">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Password Recovery</h4>
        </div>
        <div class="modal-body">
           <div class="form-group">
		   <input class="form-control inputBox" type="text" placeholder="recovery email" id="recovery_email" onchange="validateField(this);" onblur="validateField(this);">
		  </div>
		   <button type="button" class="btn btn-success" onclick="_getPassword();">Get Password</button>
		   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div>
      </div>
      </form>
    </div>
  </div>
  
  
  <!-- sign up pop up html -->
  <div class="modal fade" id="signUp" role="dialog" style="display:none">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Sign up</h4>
        </div>
        <form id="signUpForm">
        <div class="modal-body">
           <div class="form-group">
		   <input ng-model="user_name_signup" class="form-control inputBox" type="text" placeholder="full name field is required" onchange="validateField(this);" onblur="validateField(this);">
		   <input ng-model="email_signup" class="form-control inputBox" type="email" placeholder="email field is required" onchange="validateField(this);validateEmail(this);" onblur="validateField(this);">
		   <input ng-model="user_passowrd_signup" class="form-control inputBox" type="password" placeholder="password field is required" onchange="validateField(this);" onblur="validateField(this);">
		  </div>
		   <button type="button" class="btn btn-success" ng-click="_userSignUp();">Sign up</button>
		   <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
        </div></form>
      </div>
      
    </div>
  </div>
  </body>
</html>