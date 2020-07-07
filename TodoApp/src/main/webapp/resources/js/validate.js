function explandCollapseTab(Obj){
		var data = $(Obj).attr("data");
		if(data!=""){
			// div specified for toggling
			var hideShowDiv= document.getElementById(data);
			var label= document.getElementById(Obj.id);
			
			
			// show label if display is none
			if(hideShowDiv.style.display== "none"){
				label.innerHTML= label.innerHTML.replace("[+]", "[-]");
				
				hideShowDiv.style.display= "block";
			}
			// hide label if display is block 
			else{
				label.innerHTML= label.innerHTML.replace("[-]", "[+]");
				hideShowDiv.style.display= "none";
			}
		}	
	}
	
	function showToDoDetails(){
		var hideShowDiv= document.getElementById("myTaskDetails");
			hideShowDiv.style.display= "block";	
	}
function validateField(element){
	var fieldId = document.getElementById(element.id);
	if(fieldId!=undefined){
		var textLength = $(fieldId).val().length;
		if(textLength==0){
			return true;
		}
		if(textLength<1){
			showAlert(1,"Kindly enter task name.");
			setTimeout(function() { fieldId.focus(); }, 10);
			fieldId.focus();
			return false;
		}
	}
	return true;	
}


function showAlert(type,message,isAutoHide) {
	PNotify.prototype.options.styling = "bootstrap3";
	var titleStr="";
	var messageType="";
	if(type==0 || type=="" || type=="undefined"){
		titleStr="Success!";
		messageType="success";		
	}
	else if(type==1){
		titleStr="Error!";
		messageType="error";
	}
	else if(type == 2 ){
		titleStr="Warning!";
		messageType="notice";
	}
	else if(type == 3)
	{
		titleStr="Info!";
		messageType="info";
	}
	else{
		titleStr="Success!";
		messageType="success";
	}
	$(function(){
			new PNotify({
				title: titleStr
				,text: message
				,type:messageType // "notice", "info", "success", or "error".
				,insert_brs : true
				,delay : 2000
				,hide: true
				,history:false
				,after_open : function(){
						$("#pnotify_alertdialog").trigger("mouseleave");
				}
		});
	});
}

function showConfirmDialog(titleStr,message) {
	PNotify.prototype.options.styling = "bootstrap3";
	$(function(){
		new PNotify({
					title: titleStr
					,text: message
					,icon: 'glyphicon glyphicon-question-sign'
					,hide: false
					,confirm: {
						 		confirm: true
					 		  }
					,buttons: {
						 		closer: false,
						 		sticker: false
					 		  }
					,history: {
						 		history: false
					 		  }
				}).get().on('pnotify.confirm', function() {
						return true;
				}).on('pnotify.cancel', function() {
						return false;
				});
		});
}
function validateEmail(emailField){
	var pattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	var reg = new RegExp(pattern);
	var labelValue = $('label[for="'+emailField.id+'"]').text();
	if(emailField.value=="" || emailField.value==undefined){
		return true;
	}
	if(labelValue=="" && labelValue==undefined){
		labelValue = "Field";
	}
	var emails = emailField.value.split(",");        
    for(var i = 0;i < emails.length;i++) {
        if (reg.test(emails[i]) == false) 
	   {
        	//code review bug no : 1018346 Proper termination of alert messages
        	showAlert(1,"Invalid Email Address for "+labelValue+".");
		emailField.focus();
		return false;
	   }
    }    
	return true;
}