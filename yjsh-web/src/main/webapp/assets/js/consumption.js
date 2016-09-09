$(function(){
	$(".sidebar ul li").eq(3).addClass("open");
	var recordTable = $('#record_table').DataTable({
			processing: true,
	        serverSide: true,
	   	 	ajax: {
					url:ctx + "/smsConsumption/list",
					type:'post',
					data:{'userId':loginUserId}
				},
			columns:[
				{'data':'customerId'},
				{'data':'customerName'},
				{'data':'telephone'},
				{'data':'sendTime'},
				{'data':'content'},
				{'data':'status',
					render:function(data){
						if(data==0){
							return '未发送';
						}else{
							return '已发送';
						}
					}
				}
			],
			lengthChange:false,
			paging: true,
	        searching:false,
	        ordering: false,
	        info:false
		// 	 	"columnDefs": [
		//  		{
		//  			"orderable":false,"targets":[1,2,4]
		//  		}
		//  	]
	   	 	
	});
	
	$('#consuSearchBtn').on('click',function(){
		var search_customerId = $('#search_customerId').val();
		var search_customerName = $('#search_customerName').val();
		var search_startTime = $('#search_startTime').val();
		var search_endTime = $('#search_endTime').val();
		var search_sendStatus = $('#search_sendStatus').val();
		var status=0;
		if(search_sendStatus == "未发送"){
			status = 0;
		}else if(search_sendStatus == "已发送"){
			status = 1;
		}
//		var search_startTime = '2016-04-22 12:12:12';
//		var search_endTime = '2017-01-01 12:12:12';
		var param={
				userId:loginUserId,
				customerId:search_customerId,
				customerName:search_customerName,
				startTime:search_startTime,
				endTime:search_endTime,
				status:status
		}
		recordTable.settings()[0].ajax.data=param;
		recordTable.ajax.reload();
	});
	
	$("#search_startTime,#search_endTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-MM-dd hh:ii:ss",
		autoclose: true,
		todayBtn: true
	});
});
		
	
		
