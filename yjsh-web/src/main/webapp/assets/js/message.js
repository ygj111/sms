$(function(){
	$('#sendBtn').on('click',function(){
		var userNumbers = $("#userNumbers").val();
		var content = $("#content").val();
		if(userNumbers==''){
			alert('请输入手机号码');
			return;
		}
		if(content==''){
			alert('请输入发送内容');
			return;
		}
		var obj = {userNumbers:userNumbers,
				  content:content,
				  userId:loginUserId
		  };
		var jsonData = JSON.stringify(obj);
		$.ajax({
			url:ctx+'/smsConsumption/sendAndSaveSms',
			type:'Post',
			data:jsonData,
			dataType:'json',
			contentType: "application/json; charset=utf-8",
			success:function(data){
				console.info(data);
				if(data.msgRemain!=null){
					alert('发送不成功，您的短信余量仅剩'+data.msgRemain+'条');
				}else{
					if(data.failNums.length==0){
						alert('发送成功');
					}else{
						var tips='';
						for(i=0;i<data.failNums.length;i++){
							if(i<data.failNums.length-1){
								tips=tips+data.failNums[i]+","
							}else{
								tips=tips+data.failNums[i]
							}
						}
						alert('发送不成功的号码包括:'+tips);
					}
				}
//				}
			}
		});
	});
	
	$('#resetBtn').on('click',function(){
		$('.form-horizontal')[0].reset();
	});
});
