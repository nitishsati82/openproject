/*This function is used for delete file from server*/
function cancelFileUpload(obj){
	try{
		if(showConirmPopup("Are you sure you want to delete the document?")){
			var resultObj = {};
			var docName = obj.id;
			docName = docName.split("^");
			resultObj["docName"]=docName[0];
			resultObj["operation"]="delete";
			var uploadType = docName[2];
			
			var dataClass = document.getElementById("dataClass");
			var fieldValue = document.getElementById("fieldValue");
			if(dataClass!=undefined && dataClass.value!=""){
				resultObj["dataClassField"]=dataClass.value;
			}
			if(fieldValue!=undefined && fieldValue.value!=""){
				resultObj["fieldValueField"]=fieldValue.value;
			}
			var sUrl="ServiceReponser.jsp";
			if(uploadType!=undefined && uploadType=='drag_drop'){
				$.ajax({
					type: "POST",
					url: sUrl,
					cache:false,
					data : resultObj,
					dataType:'json'
				}).done(function(jsonDataObj){												
					if(jsonDataObj){
						if(jsonDataObj.status && jsonDataObj.status=="true"){
							deleteDocRow(obj); 
						}
					}
				});
			}else{
				var docUpload = document.getElementById("docUpload");
				if(docUpload!=undefined) docUpload.value = "";
				deleteDocRow(obj);
			}

		}else{
			return false;
		}
	}
	catch(e){
		alert("Error in removing uploaded documents."+e);  
	}

}
/*This function remove row from table*/
function deleteDocRow(obj){
	var row = obj.parentNode.parentNode;
	row.parentNode.removeChild(row);
	var rowCount = document.getElementById('fileList').rows.length;
	if(rowCount==0){
		$("#drag_drop_div").show();
		$("#docUploadLabel").show();
		$("#fileBrowseDiv").show();
	}
	
}
/*show confirm yes no pop up*/
function showConirmPopup(message) {
	var result = confirm(message);
	if (!result) {
		return false;
	} else {
		return true;
	}
}
function downloadToLocal(){
	var base64 = document.getElementById("base64");
	var fileName = document.getElementById("docnameToDownload");
	var pdf = 'data:application/octet-stream;base64,'+base64.value;
	var dlnk = document.getElementById('downloadLink');
	dlnk.download = fileName.value;
	dlnk.href = pdf;
	dlnk.click();
	alert('File Downloaded successfully!');
	location.reload(true);
}

function validateFileUpload(){
	var returnResult = false;
	var rowCount = document.getElementById('fileList').rows.length;
	if(rowCount==0){
		returnResult = true;
		alert("Kindly select file to upload.")
	}
	return returnResult;
}