/*
 * 
 * 
 * 
 * This js file is used for todo app client operation
 * 
 * 
 * 
 * 
 * 
 * */

var COMPLETE_COUNTER = 0;
var LABEL_ARRAY = [];

var  counter = 0;
function startOnLoadToDo(){
	setDatePicker();
	$("#forgotPassword").click(function(){
		  $('#loginModal').modal('hide');
		  $("#forgetPasswordModal").modal();
	  });
	  $("#noAccount").click(function(){
		  $('#loginModal').modal('hide');
		  $("#signUp").modal();
	  });
}
/*jquery date picker setting*/
function setDatePicker(){
	var sDateFormat="dd-mm-yy";	
	$( ".dateTime" ).datepicker({
		 dateFormat : sDateFormat // SET THE FORMAT.
	    ,yearRange : '1920:2050' //starting year
	    ,changeMonth : true // month list
		,changeYear : true //year list
		,orientation: "top"
		,onClose: function(dateText, inst) {
            $(this).datepicker('option', 'dateFormat', sDateFormat); //clear texfield if date is not in correct format
            $(this).change();
        }
	});
}
/*add tasks to table*/
function addTaskTable(obj) {
	var output = document.getElementById('fileList');
	var length = output.rows.length;
	var rowCount = length;
	if(rowCount<1)counter=0;
	var tableName = 'fileList';
	var data = obj.task_name+"^"+obj.task_due_date+"^"+obj.task_label+"^"+obj.id+"^"+obj.user_index+"^"+obj.task_notes+"^"+obj.task_priority;
	if(obj.task_label==undefined || obj.task_label==null || obj.task_label=='')obj.task_label = 'NA';
	if(obj.task_due_date==undefined || obj.task_due_date==null || obj.task_due_date=='')obj.task_due_date = 'NA';
	output.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_fileList_ang" onclick="_updateStatus(this);"  data="'+data+'" type="checkbox" id="checkBox_'+counter+'_'+tableName+'"></td><td width="70%"><span id="labelTask_'+counter+'">'+obj.task_name+'</span></td><td width="10%"><a href="#"><span class="glyphicon glyphicon-edit" 	title="click here to edit." onclick="editTaskTable(this);" data="'+data+'" id="edit_'+counter+'_fileList"></span></a></td><td width="10%"><a href="#"><span data="'+data+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_fileList_'+rowCount+'" onclick="_updateStatus(this);" row></span></a></td></tr>';
	counter++;
	document.getElementById("addToDo_Form").reset();
	$('#myModal').modal('hide');
}
/*edit tasks in modal*/
function editTaskTable(obj) {
	var data = $(obj).attr("data");
	var id = $(obj).attr("id");
	var task = document.getElementById('taskText_edit');
	var taskId = document.getElementById('taskIddEdit');
	var taskDueDate = document.getElementById('taskDueDate_edit');
	var taskLabel = document.getElementById('taskLabel_edit');
	var task_id = document.getElementById('task_id');
	var taskNotes_edit = document.getElementById('taskNotes_edit');
	var tableId = document.getElementById('tableId');
	var taskPriority = document.getElementById('taskPriority_EDIT');
	var parentIndex = obj.parentElement.parentElement.parentElement.rowIndex;
	if(data!=undefined){
		data = data.split("^");
		task.value = data[0];
		taskDueDate.value = data[1];
		taskLabel.value = data[2];
		task_id.value = data[3];
		if(data[5]==null || data[5]=='')taskNotes_edit.value = "";
		else taskNotes_edit.value = data[5];
		taskPriority.value = data[6];
		var tableName = id.split("_")[2];
		tableId.value = parentIndex+"_"+tableName;
	}
	$('#myEditModal').modal('show');
}

function updateTaskTable(obj) {
	var tableName = "";
	var tableId = document.getElementById('tableId');
	tableName = tableId.value.split("_")[1];
	var style = "";
	if(tableName=='fileList')style="";
	else style = "completed";
	var output = document.getElementById(tableName);
	output.deleteRow(tableId.value);
	var length = output.rows.length;
	var rowCount = length;
	if(rowCount<1)counter=0;
	var data = obj.task_name+"^"+obj.task_due_date+"^"+obj.task_label+"^"+obj.id+"^"+obj.user_index+"^"+obj.task_notes+"^"+obj.task_priority;
	output.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_'+tableName+'_ang" onclick="_updateStatus(this);"  data="'+data+'" type="checkbox" id="checkBox_'+counter+'_'+tableName+'"></td><td width="70%"><span class="'+style+'" id="labelTask_'+counter+'">'+obj.task_name+'</span></td><td width="10%"><a href="#"><span class="glyphicon glyphicon-edit" title="click here to edit." onclick="editTaskTable(this);" data="'+data+'" id="edit_'+counter+'_'+tableName+'"></span></a></td><td width="10%"><a href="#"><span data="'+data+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_'+tableName+'" onclick="_updateStatus(this);"></span></a></td></tr>';
	
	document.getElementById("updateModel_form").reset();

	$('#myEditModal').modal('hide');
}
/*show confirm alert*/
function showConirmPopup(message) {
	var result = confirm(message);
	if (!result) {
		return false;
	} else {
		return true;
	}
}

/*Move to completed from progress or new to progress task*/
function completeToDo(obj,control){
	var idObj = $(control).attr("id");
	var operation = idObj.split("_")[0];
	var id= idObj.split("_")[1];	
	var tableNameOrg = idObj.split("_")[2];
	var tableName = "";
	var style="";
	if(tableNameOrg=='inProgress'){
		tableName = "completed";
		style="completed";
	}else if(tableNameOrg=='fileList'){
		tableName = "inProgress";
	}else if(tableNameOrg=='completed'){
		tableName = "inProgress";
	}
	if(operation=='delete'){
		deleteTodo(control);
	}else{
		var x = document.getElementById(tableName);
		var length = x.rows.length;
		var counter = length;
		var rowCount = 0;
		var orgData = obj.task_name+"^"+obj.task_due_date+"^"+obj.task_label+"^"+obj.id+"^"+obj.user_index+"^"+obj.task_notes+"^"+obj.task_priority;
		if (control.checked == true){
			if(obj.taskStatus=='COMPLETED'){
				x.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_'+tableName+'_ang" onclick="_updateStatus(this);"  type="checkbox" data="'+orgData+'" id="checkBox_'+counter+'_'+tableName+'"></td><td width="70%"><span id="labelTask_'+counter+'" class="'+style+'">'+obj.task_name+'</span></td><td width="10%"><a href="#"><span data="'+orgData+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_'+tableName+'" ng-model="delete_'+counter+'_'+tableName+'_'+rowCount+'_ang" onclick="_updateStatus(this);" onclick="_updateStatus(this);"></span></a></td></tr>';
			}else if(obj.taskStatus=='PROGRESS'){
				var styleForGlyPhicons = getStyle(obj.task_priority);
				x.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_'+tableName+'_ang" onclick="_updateStatus(this);"  type="checkbox" data="'+orgData+'" id="checkBox_'+counter+'_'+tableName+'"></td><td width="70%"><span id="labelTask_'+counter+'" class="'+style+'"><span class="glyphicon glyphicon-flag '+styleForGlyPhicons+'"></span>&nbsp;'+obj.task_name+'</span></td><td width="10%"><a href="#"><span class="glyphicon glyphicon-edit" title="click here to edit." onclick="editTaskTable(this);" data="'+orgData+'" id="edit_'+counter+'_completed"></span></a></td><td width="10%"><a href="#"><span data="'+orgData+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_'+tableName+'" ng-model="delete_'+counter+'_'+tableName+'_'+rowCount+'_ang" onclick="_updateStatus(this);" onclick="_updateStatus(this);"></span></a></td></tr>';
			}
			counter++;
			deleteTodo(control);
		}
	}
}
/*get css class name*/
function getStyle(task_priority){
	var styleForGlyPhicons="";
	if(task_priority==3){
		styleForGlyPhicons = "high";
	}else if(task_priority==2){
		styleForGlyPhicons = "medium";
	}else if(task_priority==1){
		styleForGlyPhicons = "low";
	}
	else{
		styleForGlyPhicons = "none";
	}
	return styleForGlyPhicons;
}
/*This function remove row from table*/
function deleteTodo(obj){
	var id = $(obj).attr("id");
	var operation = id.split("_")[0];
	var message = "";
	 if(operation=='delete'){
		message = "Are you sure to delete todo?";
		if(showConirmPopup(message)){
			removeData(obj);
		}else{
			return false;
		}
	}else{
		removeData(obj);
	}
	
	
}
/*remove row*/
function removeData(obj){
	var row ;
	if(obj.nodeName!='SPAN'){
		row = obj.parentNode.parentNode;
		row.parentNode.removeChild(row);
	}else{
		row = obj.parentNode.parentElement.parentElement;
		row.parentNode.removeChild(row);
	}
}

/*load and paint lists*/
function loadLabels(){
	var ul = document.getElementById("labelList");
	$(ul).empty();
	document.getElementById("taskLabel").value = "";
	$('#taskLabel').empty();
	$('#taskLabel').append($('<option>').val('').text('--Select--'));
	if(LABEL_ARRAY!=undefined && LABEL_ARRAY.length>0){
		for(var i=0;i<LABEL_ARRAY.length;i++){
			var li = document.createElement("li");
			var labelText = document.getElementById("labelText");
			li.appendChild(document.createTextNode(LABEL_ARRAY[i].label_name));
			li.setAttribute("id", LABEL_ARRAY[i].id);
			li.setAttribute("label_type", LABEL_ARRAY[i].created_by);
			li.setAttribute("class", "list-group-item d-flex justify-content-between align-items-center pointer");
			li.setAttribute('onclick', "_loadLabelTask(this);");
		
			var optionTextAndValue = LABEL_ARRAY[i].label_name; 
	        $('#taskLabel').append($('<option>').val(optionTextAndValue).text(optionTextAndValue));
	        ul.appendChild(li);
		}
	}
}
/*paint task label*/
function addLabel(data) {
	var res = false;
	if(data!=undefined){
		var ul = document.getElementById("labelList");
		var li = document.createElement("li");
		var labelText = document.getElementById("labelText");
		li.appendChild(document.createTextNode(data.label_name));
		li.setAttribute("id", data.id);
		li.setAttribute("label_type", data.created_by);
		li.setAttribute("class", "list-group-item d-flex justify-content-between align-items-center"); 
		ul.appendChild(li);
		res = true;
	}
	$('#addNewLabel').modal('hide');
	return res;
}
/*logout*/
function logout(){
	try{
		var created_by = document.getElementById("created_by");
		var sessionKey = document.getElementById("sessionKey");
		var url="http://localhost:9596/userservice/logout/"+created_by.value+"/"+sessionKey.value;     
		$.ajax({
			type: "GET",
			url: url
		}).done(function(obj){
			if(obj=='Y'){
				$('#loginlink').show();
				$('#signUpLink').show();
				$('#logOutLink').hide();
				$('#mainDiv').hide();
				$('#addNewLabel').hide();
				$('#startOrganize').show();
				created_by.value = "";
				sessionKey.value = "";
			}

		}).fail(function(){
			console.log(" Fail api ");
		})
	}catch (e){
		alert(e);
	}
}
/*login user*/
function loginUser(data){
	try{
		$('#loginModal').modal('hide');
		document.getElementById("login_form").reset();
		$('#loginlink').hide();
		$('#signUpLink').hide();
		$('#logOutLink').show();
		$('#mainDiv').show();
		$('#addNewLabel').show();
		$('#myTaskDetails').hide();
		$('#startOrganize').hide();
		var created_by = document.getElementById("created_by");
		var sessionKey = document.getElementById("sessionKey");
		if(created_by!=undefined && created_by.value==''){
			created_by.value= data.id;
		}
		if(sessionKey!=undefined && sessionKey.value==''){
			sessionKey.value= data.session_index;
		}
	}catch (e){
		alert(e);
	}
}
/*load tasks */
function _loadLabelTask(obj){
	try{
		var id = $(obj).attr("id");
		var data = obj.innerText;
		var created_by = document.getElementById("created_by").value;
		var sessionKey = document.getElementById("sessionKey").value;
		var url="http://localhost:9596/todoservice/getToDoTaks/"+created_by+"/"+data+"/"+sessionKey;     
		$.ajax({
			type: "GET",
			url: url
		}).done(function(obj){
			$('#myTaskDetails').show();
			setDataInTable(obj);
		}).fail(function(){
			console.log(" Fail api ");
		})
	}catch (e){
		alert(e);
	}
}
/*get password*/
function _getPassword(){
	try{
		var email = document.getElementById("recovery_email").value;
		if(email==null || email==''){
			alert("Kindly enter email.")
			return false;
		}
		$.ajax({
			type: "GET",
			url: "http://localhost:9596/userservice/getPassowrd/"+email
		}).done(function(obj){
			if(obj=="N"){
				alert("Email id not registered.")
				$("#forgetPasswordModal").modal();
				return false;
			}else{
				alert("Your password is "+obj);
				$("#loginModal").modal();
				$('#forgetPasswordModal').modal('hide');
			}
			document.getElementById("recov_form").reset();
			document.getElementById("login_form").reset();
		}).fail(function(){
			console.log(" Fail api ");
		})
	}catch (e){
		alert(e);
	}
}
/*update task status*/
function _updateStatus(obj){
	try{
		var id = $(obj).attr("id");
		var data = $(obj).attr("data");
		var control = id.split("_")[0];
		var table = id.split("_")[2];

		var url ="";
		var todoId = data.split("^")[3];
		var userIndex = data.split("^")[4];
		var status = "NA";
		var sessionKey = document.getElementById("sessionKey");
		if(control=='checkBox'){
			if(table=='fileList'){
				status = "PROGRESS";
			}else if(table=='inProgress'){
				status = "COMPLETED";
			}else if(table=='completed'){
				status = "PROGRESS";
			}
			url = 'http://localhost:9596/todoservice/updateStatus/status/'+todoId+"/"+status+"/"+userIndex+"/"+sessionKey.value;
		}else 
			if(control=='delete'){
				url = 'http://localhost:9596/todoservice/updateStatus/delete/'+todoId+"/"+status+"/"+userIndex+"/"+sessionKey.value;
			}
		$.ajax({
			type: "POST",
			url: url
		}).done(function(response){
			if(response!=null){
				if(response.id>0) completeToDo(response,obj);
				else alert("Error in changing status of task.")
			}else alert("Error in changing status of task.")
			
		}).fail(function(){
			console.log(" Fail api ");
		})
	}catch (e){
		alert(e);
	}
}
/*set data*/
function setDataInTable(obj){
	var newTask = document.getElementById('fileList');
	var inProgress = document.getElementById('inProgress');
	var completed = document.getElementById('completed');
	var rowCount = 0;
	newTask.innerHTML = "";
	inProgress.innerHTML = "";
	completed.innerHTML = "";
	var priorCount = 0;
	if(obj!=undefined && obj.length>0){
		for(var i=0;i<obj.length;i++){
			var status = obj[i].taskStatus;
			var styleForGlyPhicons= getStyle(obj[i].task_priority);
			if(obj[i].task_priority==3){
				priorCount++;
			}
			if(status=='NEW'){
				var data = obj[i].task_name+"^"+obj[i].task_due_date+"^"+obj[i].task_label+"^"+obj[i].id+"^"+obj[i].user_index+"^"+obj[i].task_notes+"^"+obj[i].task_priority;
				newTask.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_fileList_ang" onclick="_updateStatus(this);"  data="'+data+'" type="checkbox" id="checkBox_'+counter+'_fileList"></td><td width="70%"><span id="labelTask_'+counter+'"><span class="glyphicon glyphicon-flag '+styleForGlyPhicons+'"></span>&nbsp;'+obj[i].task_name+'</span></td><td width="10%"><a href="#"><span class="glyphicon glyphicon-edit" 	onclick="editTaskTable(this);" data="'+data+'" title="click here to edit." id="edit_'+counter+'_fileList"></span></a></td><td width="10%"><a href="#"><span data="'+data+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_fileList_'+rowCount+'" ng-model="delete_'+counter+'_fileList_'+rowCount+'_ang" onclick="_updateStatus(this);" onclick="_updateStatus(this);" row></span></a></td></tr>';
				counter++;
			}else if(status=='PROGRESS'){
				var data = obj[i].task_name+"^"+obj[i].task_due_date+"^"+obj[i].task_label+"^"+obj[i].id+"^"+obj[i].user_index+"^"+obj[i].task_notes+"^"+obj[i].task_priority;
				inProgress.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_inProgress_ang" onclick="_updateStatus(this);"  data="'+data+'" type="checkbox" id="checkBox_'+counter+'_inProgress"></td><td width="70%"><span id="labelTask_'+counter+'"><span class="glyphicon glyphicon-flag '+styleForGlyPhicons+'"></span>&nbsp;'+obj[i].task_name+'</span></td><td width="10%"><a href="#"><span class="glyphicon glyphicon-edit" 	onclick="editTaskTable(this);" data="'+data+'" title="click here to edit." id="edit_'+counter+'_inProgress"></span></a></td><td width="10%"><a href="#"><span data="'+data+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_inProgress_'+rowCount+'" ng-model="delete_'+counter+'_fileList_'+rowCount+'_ang" onclick="_updateStatus(this);" onclick="_updateStatus(this);" row></span></a></td></tr>';
				counter++;
			}else if(status=='COMPLETED'){
				var data = obj[i].task_name+"^"+obj[i].task_due_date+"^"+obj[i].task_label+"^"+obj[i].id+"^"+obj[i].user_index+"^"+obj[i].task_notes+"^"+obj[i].task_priority;
				completed.innerHTML += '<tr id="upload_'+counter+'"><td width="10%"><input ng-model="checkBox_'+counter+'_completed_ang" onclick="_updateStatus(this);"  data="'+data+'" type="checkbox" id="checkBox_'+counter+'_completed"></td><td width="70%"><span class="completed" id="labelTask_'+counter+'">'+obj[i].task_name+'</span></td><td width="10%"><a href="#"><span data="'+data+'" class="glyphicon glyphicon-trash" id="delete_'+counter+'_completed_'+rowCount+'" ng-model="delete_'+counter+'_fileList_'+rowCount+'_ang" onclick="_updateStatus(this);" onclick="_updateStatus(this);" row></span></a></td></tr>';
				counter++;
			}
		}
		if(priorCount==1){
			alert("You have "+priorCount+" task as high priority.")
		}else if(priorCount>1){
			alert("You have "+priorCount+" tasks as high priority.")
		}
		
	}
}