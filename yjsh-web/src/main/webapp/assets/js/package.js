$(function(){
	$(".sidebar ul li").eq(4).addClass("open");
		 $('#package_table').DataTable({
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
				{'data':'packageDes'},
				{'data':'status',
					render : function(data){
				    	if(data==1){
				    		return '有效';
				    	}else{
				    		return '无效';
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
		
		// 全选方法
   		$('#checkAll').on('click', function() {
   			console.info('checkall status:' + $(this).prop("checked"));
   	   		$("input[name='packageCheck']").prop('checked', $(this).prop('checked'));
   	   	});
   		
		//添加
		$('#btn_add').on('click',function(){
			$('#package_form input').val("");
			$('#myModalLabel').text("新增");
			$("#modeltc_add").modal('toggle');
		})
		
		//保存
		$('#btn_save').on('click',function(){
			var packageName = $('#packageName').val();
			var msgAmount = $('#msgAmount').val();
			var price = $('#price').val();
			var packageDes = $('#packageDes').val();
			if (packageName == null || packageName == "") {
   				$("#packageName").css("border-color","#FF0000");
   				$("#packageName").focus();
   				return;
   			}
			if (msgAmount == null || msgAmount == "") {
   				$("#msgAmount").css("border-color","#FF0000");
   				$("#msgAmount").focus();
   				return;
   			}
			if (price == null || price == "") {
   				$("#price").css("border-color","#FF0000");
   				$("#price").focus();
   				return;
   			}
//			if (packageDes == null || packageDes == "") {
//   				$("#packageDes").css("border-color","#FF0000");
//   				$("#packageDes").focus();
//   				return;
//   			}
			
//			var result;
//			//验证套餐名是否已存在
//			$.ajax({
//				type: 'POST',
//   				url: ctx + '/smsPackage/checkPackageName',
//   				data: {packageName:packageName},
//   				dataType:"json",
//   				async:false,
//   				success: function(data) {
//   						if(data==1){
//   							alert('套餐名已存在!');
//   							result=1;
//   						}else{
//   							result=0;
//   						}
//   					}
//			});
//			if(result==1){
//				return;
//			}
			var data = $('#package_form').serializeArray();	// 将form中的表单元素序列化成json对象；
			
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
   			console.info(jsonData);
   			
   			$.ajax({
   				type: 'POST',
   				url: ctx + '/smsPackage/save',
   				data: jsonData,
   				contentType: "application/json; charset=utf-8", 
   				dataType:"json",
   				success: function() { 
   						$("#modeltc_add").modal("hide");
   						alert("保存成功");
   						window.location.reload();
   					}
   			}); 
		})
		
		//修改
		$('#btn_update').on('click',function(){
			// 判断表格中是否有行被选中
   			var id;
   			
   			$('input[name="packageCheck"]').each(function(i) {
   				if ($(this).prop('checked')) {
   					id = $(this).attr('value');
   					
   					// 将选中行的数据填充到模态窗口
   					var td = $(this).parent().nextAll();
   					$('#id').val(id);
   					$('#packageName').val(td.eq(0).text());
   					$('#price').val(td.eq(1).text());
   					$('#msgAmount').val(td.eq(2).text());
   					$('#packageDes').val(td.eq(3).text());
   					if(td.eq(4).text()=='有效'){
   						$('#status').val(1);
   					}else{
   						$('#status').val(0);
   					}
   					return;
   				}
   			});
   			
   			if (id == null) {
   				alert("请选择一条需要修改的套餐！");
   				return;
   			}
   			
   			$('#myModalLabel').text('修改');		// 修改label
   			$("#modeltc_add").modal('toggle');
		})
		
		//删除
		$('#btn_del').on('click',function(){
			var ids = new Array();
			$('input[name="packageCheck"]').each(function(i) {
   				if ($(this).prop('checked')) {
   					var id = $(this).attr('value');
   					ids.push(id);
   					return;
   				}
   			});
			
			if (ids.length==0) {
   				alert("请选择需要的套餐！");
   				return;
   			}
			
			$.ajax({
				url:ctx + '/smsPackage/delPackages',
				type: 'Post',
				data: {ids:ids},
				dataType:'json',
				success:function(data){
					alert("套餐状态改变成功！");
					window.location.reload();
				}
			});
		})
		
	});