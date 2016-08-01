<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() { 
		loadUserPage(1, 10);
	});

	function loadUserPage(currentPage, limit) {
		//先销毁表格  
        $('#table').bootstrapTable('destroy'); 
		$.ajax({
           url: '<%=request.getContextPath()%>/user/listUser',
			data : {
				pageSize : currentPage,
				pageNumber : limit
			},
			type : "get",
			dataType : "json",
			success : function(data) {
				$('#table').bootstrapTable({
					data : data,
					striped : true, //表格显示条纹  
					pagination : true, //启动分页  
					pageSize : limit, //每页显示的记录数  
					pageNumber : currentPage, //当前第几页  
					search : false, //是否启用查询  
					showColumns : false, //显示下拉框勾选要显示的列  
					showRefresh : false, //显示刷新按钮  
					showToggle : false,
					sidePagination : "server" //表示服务端请求  
				});

				$('#currentPage').val(currentPage);
				$('#limit').val(limit);
			}
		});
	}

	function delUser() {
		var userList = [];
		$("input[name='subBox']").each(function() {
			if ($(this).is(":checked")) {

				var tr = $(this).parent().parent();
				var a = tr.children();

				userList.push(a.eq(1).text());
			}
		});

		if (userList.length > 0) {
			$.ajax({
				//提交数据的类型 POST GET
				type : "POST",
				//提交的网址
				url : '<%=request.getContextPath()%>/user/delUser',
				data : {
					'userList' : userList
				},
				traditional:true,
				dataType : 'JSON',

				success : function(data) {
					
					if (data) {
						bootbox.alert("删除成功");
					} else {
						bootbox.alert("删除失败");
					}
					loadUserPage($('#currentPage').val(), $('#limit').val());
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
				}
			});
		} else {
			bootbox.alert('请选择具体用户后删除');
		}

	}
</script>
<input type="hidden" id="currentPage" />
<input type="hidden" id="limit" />

<div class="row">
	<ol class="breadcrumb">
		<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
		<li class="active">用户列表</li>
	</ol>
</div>
<!--/.row-->

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">用户列表</h1>
	</div>
</div>
<!--/.row-->


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<!-- 	<div class="panel-heading">Advanced Table</div> -->
			<div class="panel-body">
				<div id="toolbar1" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					</button>
					<button id="btn_edit" type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					</button>
					<button id="btn_delete" type="button" class="btn btn-default"
						onclick='delUser()'>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>
				</div>
				<br />
				<br />
				<table data-show-refresh="true" data-show-toggle="true"
					data-show-columns="true" data-search="true"
					data-select-item-name="subBox" data-pagination="true"
					data-sort-order="desc" id="table">
					<thead>
						<tr>
							<th data-field="userId" data-checkbox="true">用户编号</th>
							<th data-field="userId" data-sortable="true">用户编号</th>
							<th data-field="username">用户名称</th>
							<th data-field="password">用户密码</th>
						</tr>
					</thead>
				</table>

			</div>
		</div>
	</div>
</div>
<!--/.row-->

