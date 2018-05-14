
//查询信息定时任务
var timer;
function userLogin_doLogin()
{
	if (""==$("#userLogin_userName").val().trim())
	{
		alert("userName cannot be empty");
		return;
	}
	$("#userLogin_login").attr("disabled", "disabled");
	//使输入框处于不可编辑状态
	document.getElementById("userLogin_userName").readOnly = true;

	//查询信息定时任务
	timer = setInterval(queryMessage, 1000);
	alert("login success!");

}

function userLogin_doLogout()
{
	document.getElementById("userLogin_userName").readOnly = false;
	clearInterval(timer);
	alert("logout success!");
}

function createMessage(){
	$.ajax({
	 url:"/SMSWebDemo/resource/sms/send",
	 type:"post",
	 dataType:"json",
	 data:{"message":$("#send_message").val(),
		 "senderAddress":$("#userLogin_userName").val(),
		 "smsServerActiveNum":$("#receive_sms").val()},
	 success:function(result){
			if (result != null)
			{
				createLog($("#receive_sms").val(),$("#userLogin_userName").val(), $("#send_message").val());
			}
			else{
				alert("send message failed!");	
			}
		},
	 error:function(){
		 alert("send failed.");
	 }
	});
}

function queryMessage(){
	 $.ajax({
		 url:"/SMSWebDemo/resource/sms/query",
		 type:"post",
		 dataType:"json",
		 data:{"destinationAddresses":$("#userLogin_userName").val()},
		 success:function(result){
				if (null != result)
				{
					//对后台数据取值
				    var message = result;
				    var receiverAddress = message.receiverAddress;
				    var senderAddress = message.senderAddress;
				    var message = message.message;
				    createLog(receiverAddress,senderAddress,message);
				}
			},
		 error:function(){
		 }
	 });
}

//根据发送和返回消息，拼接成表格形式在界面上
function createLog(receiver,sender,message)
{
	var data="";
	data += '<tr>';
	data += '	<td>';
	data += 		htmlEncode(receiver);
	data += '	</td>';
	data += '	<td>';
	data += 		htmlEncode(sender);
	data += '	</td>';
	data += '	<td>';
	data += 		htmlEncode(message);
	data += '	</td>';
	data += '<tr>';
	var $data = $(data);
	$('#tablebody tbody').append(data);
}

function htmlEncode(objVal)
{
	var str = objVal+"";
	if(str == '')
	{
		return str;
	}
	str = str.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(new RegExp("\"","g"),"&quot;").replace(new RegExp("\'","g"),"&#39;").replace(new RegExp("  ","g")," &nbsp;");
	return str;
}

