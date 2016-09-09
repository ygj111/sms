$(function(){
	$(".sidebar ul li").eq(1).addClass("open");
	$("#search_startTime,#search_endTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-MM-dd hh:ii:ss",
		autoclose: true,
		todayBtn: true
	});
	var confirmStatus;
	var applyTable = $('#apply_table').DataTable({
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
				type:'post',
				data:{'userId':loginUserId}
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
				confirmStatus=data;
				if(data==0){
					return '未审核';
				}else{
					return '已审核';
				}
			}},
			{'data': 'id',
			     'render': function ( data, type, full, meta ) {
			    	 if(confirmStatus==0){
			    		 return '<a href=\'javascript:void(0)\' onclick=\'approve(\"'+data+'\")\'>审批&nbsp;&nbsp;</a><a href=\'javascript:void(0)\' onclick=\'deleteApprove(\"'+data+'\")\'>删除</a>';
			    	 }else{
			    		 return '';
			    	 }
			      }
			}
		]
	});
	if(loginUserType==1){//管理员
		applyTable.column(9).visible(true);
	}else{//企业用户
		applyTable.column(9).visible(false);
	}
	
	$('#applySearchBtn').on('click',function(){
		var search_applyCode = $('#search_applyCode').val();
		var search_customerId = $('#search_customerId').val();
		var search_customerName = $('#search_customerName').val();
		var search_startTime = $('#search_startTime').val();
		var search_endTime = $('#search_endTime').val();
		if(search_startTime != null && search_startTime != ""){
			if(search_endTime == null || search_endTime == ""){
				alert("请输入结束时间！");
				return;
			}
		}
		if(search_endTime != null && search_endTime != ""){
			if(search_startTime == null || search_startTime == ""){
				alert("请输入开始时间!");
				return;
			}
		}
		var param={
				userId:loginUserId,
				customerId:search_customerId,
				customerName:search_customerName,
				applyCode:search_applyCode,
				startTime:search_startTime,
				endTime:search_endTime,
		}
		applyTable.settings()[0].ajax.data=param;
		applyTable.ajax.reload();
	});
	
});
	

	//function
function approve(applyRecordId){
	var r=confirm("确定审批通过");
	  if (r==true)
	    {
		  $.ajax({
				url:ctx + '/smsApplyRecord/smsApplyConfirm',
				type: 'Post',
				data: {applyRecordId:applyRecordId},
				dataType:'json',
				success:function(data){
					if(data=='1')alert("审批成功");
					window.location.reload();
				}
			});
	    }
}

function deleteApprove(applyRecordId){
	var r=confirm("确定删除申购");
	  if (r==true)
	    {
		  $.ajax({
				url:ctx + '/smsApplyRecord/deleteApprove',
				type: 'Post',
				data: {applyRecordId:applyRecordId},
				dataType:'json',
				success:function(data){
					if(data=='1')alert("删除成功");
					window.location.reload();
				}
			});
	    }
}