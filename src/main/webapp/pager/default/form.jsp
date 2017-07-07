<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.error {
/* 		margin-left: 10px; */
/* 		width: auto; */
/* 		display: inline; */
		color:red;
	}
</style>
</head>
<body>
	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="fa fa-home"></i>广告资源管理</li>
			<li><a href="${pageContext.request.contextPath}/default/list">默认地址配置</a></li>
			<li class="active"> ${defaultBean eq null ? "新增" : "编辑" }默认地址</li>
		</ul>
	</div>
	<!-- Page Header -->
	<div class="page-header position-relative">
		<!-- 		<div class="header-title"> -->
		<!-- 			<h1> -->
		<!-- 				用户管理 <small> <i class="fa fa-angle-right"></i> ${user eq null ? "新增" : "编辑" }用户 -->
		<!-- 				</small> -->
		<!-- 			</h1> -->
		<!-- 		</div> -->
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="javascript:void(0);"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="javascript:void(0);"> <i class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="javascript:void(0);"> <i class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->

	<!-- Page Body -->
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12 col-xs-12 col-md-12">
				<div class="widget">
					<div class="widget-header bordered-bottom bordered-palegreen">
						<span class="widget-caption"><i class="fa fa-table"> ${defaultBean eq null ? "新增" : "编辑" }默认地址</i></span>
						<div class="widget-buttons">
							<a href="javascript:void(0);" data-toggle="maximize"> <i class="fa fa-expand"></i></a> <a href="javascript:void(0);" data-toggle="collapse"> <i class="fa fa-minus"></i></a>
						</div>
					</div>
					<div class="widget-body">
						<form id="defaultBean_form" class="form-horizontal" action="${pageContext.request.contextPath}/default/${defaultBean eq null ? 'save' : 'update' }" method="post" role="form">
							<input type="hidden" name="id" id="id" value="${defaultBean.id}">
							<input type="hidden" name="status" id="status" value="${defaultBean eq null ? 0 : defaultBean.status }">
							<input type="hidden" name="currentPage" id="currentPage" value="${queryBean.currentPage}">
							<input type="hidden" name="pageSize" id="pageSize" value="${queryBean.pageSize}">
							<input type="hidden" name="keyword" id="keyword" value="${queryBean.keyword}">
							<input type="hidden" name="statusBean" id="statusBean" value="${queryBean.status}">
							<div class="form-group">
								<label for="name" class="col-sm-4 control-label no-padding-right">地址Url(<nobr style="font-size:14px;color:red;">http://打头</nobr>)</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="url" name="url" value="${defaultBean.url}" placeholder="地址Url">
									<label class="error" for="url" style="display:none;">请填写地址Url</label>
									<label class="error" for="url" style="display:none;">地址Url格式不对</label>
								</div>
							</div>
							<hr class="wide" />
							<div class="buttons-preview text-align-center">
								<a type="" class="btn btn-primary" href="javascript:history.back();"> <i class="fa fa-mail-reply"></i> 返回
								</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-primary" id="submitInput">
									<i class="fa fa-save"></i> 保存
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">	
		$("#submitInput").click(function(){
			var url = $.trim($("#url").val());
			var num = 0;
			
 			var strReg=/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
			if(url == ""){
				$("#url").next().show();
				$("#url").next().next().hide();
			}else{
				$("#url").next().hide();
				num = num + 1;
/* 				if(!strReg.test(url)) {  
					$("#url").next().next().show();
				}else{
					num = num + 1;
					$("#url").next().next().hide();
				} */
			}
			
			if(num == 1){
				$("#submitInput").attr("disabled","true");
				$("#defaultBean_form").submit();
			}
		});
	</script>
</body>
</html>