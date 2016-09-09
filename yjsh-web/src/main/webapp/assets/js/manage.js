var historyTable;
$(function(){
	$(".sidebar ul li").eq(0).addClass("open");
	var columns;

	var manageTable = $('#manage_table').DataTable({
		processing: true,
	    serverSide: true,
	    lengthChange:false,
		paging: true,
	    searching:false,
	     ordering: false,
		info:false,
//		columnDefs: [
//		    {
//		       targets: [7],
//		       visible: false
//		    }
//		 ],
		ajax: {
		   	url:ctx+"/smsUser/list",
		   	type:"post"
//		    		dataSrc: ''
		},
		columns: [
       		{'data': 'id',
           		render: function(data) {
           			return "<input type='checkbox' id='" + data +"' name='singleCheck' value='" + data +"'>";
           		}},
           	{'data': 'customerId'},
           	{'data': 'customerName'},
           	{'data': "username"},
           	{'data': "msgAmount"},
           	{'data': 'contactor'},
           	{'data': 'contactorInfo'},
        	{'data': 'type',
        		'render': function ( data, type, full, meta ) {
        			if(data==1){
        				return '管理员';
        			}else if(data == 2){
        				return '部门管理员';
        			}else{
        				return '企业用户';
        			}
  			    }},
           	{
			    'data': 'id',
			    'render': function ( data, type, full, meta ) {
			      return '<a href=\'javascript:void(0)\' onclick=\'applyPackage(\"'+data+'\")\'>申购</a>';
			    }},
			{'data': 'dept'},
           	{
			    'data': 'id',
			    'render': function (data, type, full, meta ) {
			      return '<a href=\'javascript:void(0)\' onclick=\'history(\"'+data+'\")\'>明细</a>';
			    }
			}
//			    ,
//			{'data': 'password'}
		],
	});
	
	if(loginUserType==1){//管理员
		$(".userType").attr("type","text");
		manageTable.column(8).visible(false);
	}else if(loginUserType == 2){
		$(".userType").attr("type","hidden");
		//$(".ssbm").find("select").remove();
		//$(".ssbm").find("input").attr("type","text");
		manageTable.column(8).visible(false);
		manageTable.column(10).visible(false);
	}else{//企业用户
		$(".userType").attr("type","hidden");
		manageTable.column(8).visible(true);
	}
	
	$('#manageSearchBtn').on('click',function(){
		var search_customerId = $('#search_customerId').val();
		var search_customerName = $('#search_customerName').val();
		var search_dept = $('#search_dept').val();
		var param={
				customerId:search_customerId,
				customerName:search_customerName,
				dept:search_dept
		}
		manageTable.settings()[0].ajax.data=param;
		manageTable.ajax.reload();
	});
	//增删改操作
			//进行全选selectAll
			$("#selectAll").on('click',function(){
				$("input[name='singleCheck']").prop('checked',$(this).prop('checked'));
			});
			
			//新增用户数据操作
			$("#btn_add").on('click',function(){
				$('#username').prop('readonly',false);
				$("#manage_form input").val("");		//清空input
				$('#myModalLabel').text('新增用户数据');		// 修改label
				$("#tablegl_updata").modal('toggle');
				if(loginUserType == 2){
					var length = getDeptLength();
					for(var i=0;i<length;i++)  { 
				  		if($("#select ").get(0).options[i].text == dept)  {    
				            $("#select").get(0).options[i].selected = true;    
				         var stat = $("#select ").get(0).options[i].value;
				         $("#dept").val(stat);
				            break;    
				        }    
				    } 
					$(".ssbm").find("#select").attr("style","display:none;");
					$(".ssbm").find("input").attr("type","text");
					$("#dept").prop("readonly",true);
				}
			});
			//修改用户数据操作
			$("#btn_modify").on('click',function(){
				if($('input[name="singleCheck"]:checked').length>1){
					alert('只可以选择一条要修改的数据！');
					return;
				}
				var id;
				//判断表格有没有选中
				$('input[name="singleCheck"]').each(function(i){
					if ($(this).prop('checked')) {
   						id = $(this).attr('value');
						
						//选中行的数据填充到模态窗口
					var td =$(this).parent().nextAll();
					$("#id").val(id);
					$("#customerId").val(td.eq(0).text());
					$("#customerName").val(td.eq(1).text());
					$("#username").val(td.eq(2).text());
					$("#msgAmount").val(td.eq(3).text());
					$("#contactor").val(td.eq(4).text());
					$("#contactorInfo").val(td.eq(5).text());
					var length = getDeptLength();
					if(loginUserType == 2){
						for(var i=0;i<length;i++)  { 
					  		if($("#select ").get(0).options[i].text == dept)  {    
					            $("#select").get(0).options[i].selected = true;    
					         var stat = $("#select ").get(0).options[i].value;
					         $("#dept").val(stat);
					            break;    
					        }    
					    } 
						$(".ssbm").find("#select").attr("style","display:none;");
						$(".ssbm").find("input").attr("type","text");
						$("#dept").prop("readonly",true);
					}else{
						for(var i=0;i<length;i++)  { 
   				  			if($("#select ").get(0).options[i].text == td.eq(7).text())  {    
   				  				$("#select").get(0).options[i].selected = true;    
   				  				var stat = $("#select ").get(0).options[i].value;
   				  				$("#dept").val(stat);
   				  				break;    
   				  			}    
						}  
					}
					if(td.eq(6).text()=='管理员'){
						$("#select2").attr("style","display:none;");
						$("#type").attr("type","text");
						$("#type").val("管理员");
						$("#type").prop("readonly",true);
						$(".dxl").attr("style","");
					}else{
						//$("#type").val(0);
						$(".dxl").attr("style","display:none;");
						var roleLength = getRoleLength();
						for(var i=0;i<length;i++)  { 
   				  			if($("#select2 ").get(0).options[i].text == td.eq(6).text())  {    
   				  				$("#select2").get(0).options[i].selected = true;    
   				  				var stat = $("#select2 ").get(0).options[i].text;
   				  				$("#type").val(stat);
   				  				break;    
   				  			}    
						} 
					}
					}
				});
				if(id==null){
					alert("请选择一条要修改的数据");
					return;
				}
				$('#myModalLabel').text('修改用户数据');	
				$('#username').prop('readonly',true);
				$("#tablegl_updata").modal('toggle');
			});
			//保存数据
			$("#btn_save").on('click',function(){
				//判断必填数据是否为空
				var customerId = $('#customerId').val();
				var username = $('#username').val();
				var password = $('#password').val();
				var id = $('#id').val();
				if (customerId == null || customerId == "") {
	   				$("#customerId").css("border-color","#FF0000");
	   				$("#customerId").focus();
	   				return;
	   			}
				if (username == null || username == "") {
	   				$("#username").css("border-color","#FF0000");
	   				$("#username").focus();
	   				return;
	   			}
				if (password == null || password == "") {
	   				$("#password").css("border-color","#FF0000");
	   				$("#password").focus();
	   				return;
	   			}
				if(id==''){
					$.ajax({
						type:"Post",
						url:ctx+"/smsUser/checkUserName",
						data: {username:username},
						dataType:"json",
						success: function(data){
							if(data==1){
								alert('用户名已存在');
								$("#username").css("border-color","#FF0000");
				   				$("#username").focus();
							}else{
								var length = getDeptLength();
								for(var i=0;i<length;i++)  { 
								  	if($("#select").get(0).options[i].selected == true)  {    
								         var stat = $("#select ").get(0).options[i].text;
								         $("#dept").val(stat);
								            break;    
								     }    
								 }
								
								var roleLength = getRoleLength();
								for(var i=0;i<roleLength;i++)  { 
								  	if($("#select2").get(0).options[i].selected == true)  {    
								         var stat = $("#select2 ").get(0).options[i].text;
								         if(stat == "部门管理员"){
								        	 $("#type").val(2);
								         }else{
								        	 $("#type").val(0);
								         }
								            break;    
								     }    
								 }
								saveManageForm();
							}
						}
					});
				}else{
					for(var i=0;i<length;i++)  { 
					  	if($("#select").get(0).options[i].selected == true)  {    
					         var stat = $("#select ").get(0).options[i].text;
					         $("#dept").val(stat);
					            break;    
					     }    
					 }
					if($("#type").val()=="管理员"){
						$("#type").val(1);
					}else{
					var roleLength = getRoleLength();
					for(var i=0;i<roleLength;i++)  { 
					  	if($("#select2").get(0).options[i].selected == true)  {    
					  		 var stat = $("#select2 ").get(0).options[i].text;
					         if(stat == "部门管理员"){
					        	 $("#type").val(2);
					         }else{
					        	 $("#type").val(0);
					         }
					            break;    
					     }    
					 }
					}
					saveManageForm();
				}
					
			});
			
			//删除数据
			$('#btn_del').on('click',function(){
			var ids = new Array();
			$('input[name="singleCheck"]').each(function(i) {
   				if ($(this).prop('checked')) {
   					var id = $(this).attr('value');
   					ids.push(id);
   					return;
   				}
   			});
			if (ids.length==0) {
   				alert("请选择需要删除的数据！");
   				return;
   			}else{
   				var r=confirm("确定删除用户？删除后用户申购历史和消费记录同时被删除!");
   				if(!r)return;
   			}
			$.ajax({
				url:ctx+"/smsUser/delUsers",
				type: 'Post',
				data: {ids:ids},
				dataType:'json',
				success:function(data){
					alert("删除成功");
					window.location.reload();
				}
			});
		})
			
		
		
		//////////
//		$('#packageApply_table').css("width","100%");
		$('#packageApply_table').DataTable({
			bAutoWidth: false, 
			processing: true,
	        serverSide: true,
			ajax: {
				url:ctx+'/smsPackage/list'
			},
			columns:[
			    {'data':'id',render : function(data){
			    	return "<input type='checkbox' name='packageCheck' value="+data+">";
			    }},
				{'data':'packageName'},
				{'data':'price'},
				{'data':'msgAmount'},
				{'data':'packageDes'}
			],
			lengthChange:false,
			paging: true,
		    searching:false,
		    ordering: false,
		    info:false
		});
		
			$("#apply_save").on("click",function(){
				var packageIds = new Array();
				$('input[name="packageCheck"]').each(function(i) {
	   				if ($(this).prop('checked')) {
	   					var id = $(this).attr('value');
	   					packageIds.push(id);
	   					return;
	   				}
	   			});
				
				if (packageIds.length==0) {
	   				alert("请选择至少一个套餐！");
	   				return;
	   			}
				
				$.ajax({
					url:ctx + '/smsApplyRecord/smsApply',
					type: 'Post',
					data: {packageIds:packageIds,
						userId:choseUserId
					},
					dataType:'json',
					success:function(data){
						if(data==0){
							alert('管理员可申购短信量不足，请联系管理员！');
						}else{
							alert("申购成功");
							$("#apply_model").modal('hide');
						}
					}
				});
			});
			
//			$('#manageSearchBtn').on('click',function(){
//				$('#manageSearchForm').attr('action',ctx + '/smsUser/list');
//				$('#manageSearchForm').submit();
//				historyTable.draw();
//			});
			
//历史记录明细
//		$('#history_table').css("width","100%");
		historyTable = $('#history_table').DataTable({
			bAutoWidth: false, 
			processing: true,
		    serverSide: true,
		    lengthChange:false,
			paging: true,
		    searching:false,
		     ordering: false,
			info:false,
			columnDefs: [
		    {
		       targets: [ 0 ],
		       visible: false,
		       searchable: false
		    }
		 ],
	   	 	ajax: {
					url:ctx+"/smsApplyRecord/list",
					data:{'userId':loginUserId},
				},
			columns:[
				{'data': 'id',
         			render: function(data) {
         			return "<input type='checkbox' id='" + data +"' name='singleCheck' value='" + data +"'>";
         			}
         		},
         		{'data':'applyCode'},
				{'data':'customerId'},
				{'data':'customerName'},
				{'data':'price'},
				{'data':'msgAmount'},
				{'data':'applyDate'},
				{'data':'confirmTime'},
				{'data':'confirmStatus',render:function(data){
					if(data==0){
						return '未审核';
					}else{
						return '已审核';
					}
				}}
			],
		});
//		$('#btn_resetAccount').on('click',function(){
//			$.ajax({
//				type:"post",
//				url:ctx+"/smsUser/getRemainNumber",
//				dataType:"json",
//				success: function(data){
//					if(data=='fail'){
//						alert('获取短信剩余条数失败!');
//					}else{
//						alert('管理员短信量更新成功!');
//						manageTable.draw();
//					}
//				}
//			});
//		});
		
});  //function			
	function saveManageForm(){
		//将form中的表单元素序列化成json对象
		var data=$("#manage_form").serializeArray();
		var o = {};
		$.each(data, function() {
			if (o[this.name]) {    
		            if (!o[this.name].push) {    
		                o[this.name] = [o[this.name]];    
		            }    
		            o[this.name].push(this.value || '');    
		        } else {    
		            o[this.name] = this.value || '';    
		        }   
		});
		var jsonData = JSON.stringify(o);
		//保存进url
		$.ajax({
			type:"post",
			url:ctx+"/smsUser/save",
			data: jsonData,
			contentType: "application/json; charset=utf-8", 
			dataType:"json",
			success: function(){
				$("#tablegl_updata").modal("hide");
				alert("保存成功!");
				window.location.reload();
			}
		});
	}

	var choseUserId;
	function applyPackage(data){
		choseUserId=data;
		$("#apply_model").modal('toggle');
	}

	function history(data){
		var param={'userId':loginUserId,
			'userIdOfDetail':data};
			historyTable.settings()[0].ajax.data = param;
			historyTable.ajax.reload();
		$("#consumption_model").modal('toggle');
	}



