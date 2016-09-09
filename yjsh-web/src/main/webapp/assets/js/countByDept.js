$(function(){
	$(".sidebar ul li").eq(5).addClass("open");
	var countTable = $('#count_tableByDept').DataTable({
		processing: true,
	    serverSide: true,
	    lengthChange:false,
		paging: true,
	    searching:false,
	    ordering: false,
		info:false,
	   	 	ajax: {
					url:ctx+"/smsConsumption/listSmsCountByDept",
					type:'post',
				},
			columns:[
				{'data':'customerName'},
				{'data':'msgAmount'},
				{'data':'sendedAmount'},
				{'data':'msgRemain'},
				{
			    'data': 'customerName',
			    'render': function ( data, type, full, meta ) {
			      return '<a href=\'javascript:void(0)\' onclick=\'countHistory(\"'+data+'\")\'>明细</a>';
			    }}
			],
	});
	
//	countHistory_table
//	$('#countHistory_table').css("width","100%");
	countHistoryTable = $('#countHistory_table').DataTable({
			bAutoWidth: false, 
			processing: true,
	        serverSide: true,
	   	 	ajax: {
					url:ctx + "/smsConsumption/listByDept",
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
	   	 	
	});
	
	$('#countSearchBtn').on('click',function(){
		var search_customerName = $('#search_customerName').val();
		var param={
				customerName:search_customerName
		}
		countTable.settings()[0].ajax.data=param;
		countTable.ajax.reload();
	});
});
	

function countHistory(data){
	var param={'dept':data};
		countHistoryTable.settings()[0].ajax.data = param;
		countHistoryTable.ajax.reload();
	$("#countHistory_model").modal('toggle');
	
	
	
}
