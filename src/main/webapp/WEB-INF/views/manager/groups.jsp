<%@ include file="../libs/libs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<script src="<c:url value="/resources/script/jquery-2.1.0.js" />"></script>
<script src="<c:url value="/resources/script/jquery-ui.js" />"></script>
<script id="group-row" language="text">
<div class="row">
	<div class="col-md-6 group-name">{{name}}</div>
	<div class="col-md-2 group-opened">{{start}}</div>
	<div class="col-md-2 group-closed">{{end}}</div>
	<div class="col-md-2 text-center">
		<div class="btn-group btn-group-lg">
			<button type="button" class="modifiable btn btn-warning">
				<span class="glyphicon glyphicon-pencil"></span>
			</button>

			<button type="button" class="deleteable btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span>
			</button>
		</div>
		<input type="hidden" name="delete" value="{{id}}" />
		<input type="hidden" name="modify" value="{{id}}" />
		<input type="hidden" name="competence-id" value="{{comp_id}}" />
	</div>
</div>
<hr />


</script>

<script id="group-panel" language="text">
<div id="{{competence}}" class="panel panel-default">
<div class="panel-heading">{{competence}}</div>
<div class="panel-body">
	<div class="row">
		<div class="col-md-6">
			<strong>Group name</strong>
		</div>
		<div class="col-md-2">
			<strong>Opening date</strong>
		</div>
		<div class="col-md-2">
			<strong>Closing date</strong>
		</div>
		<div class="col-md-2">
			<strong>Actions</strong>
		</div>
	</div>
	<hr />

	{{row}}
</div>
</div>


</script>
<script>
	$(function() {
		cancelModify = function() {
			$(".modify-existing-group").css("display", "none");
            $(".createAchievementType-new-group").css("display", false);
			$("input[name=type]").val("create");
			$("input[name=id]").val("");
			$("input[name=group_name]").val("");
			$("input[name=dateStart]").val("");
			$("input[name=dateEnd]").val("");
		};
		
		$("#date-from").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			numberOfMonths : 3,
			dateFormat : 'yy-mm-dd',
			onClose : function(selectedDate) {
				$("#date-to").datepicker("option", "minDate", selectedDate);
			}
		});

		$("#date-to").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			numberOfMonths : 3,
			dateFormat : 'yy-mm-dd',
			onClose : function(selectedDate) {
				$("#date-from").datepicker("option", "maxDate", selectedDate);
			}
		});

		$("#message-box").on("click", function() {
			$(this).fadeOut();
        });

		$("body").on("click", ".modifiable", function() {
				row = $(this).parent().parent().parent();
            $(".createAchievementType-new-group").css("display", "none");
				$(".modify-existing-group").css("display", false);
				$("input[name=type]").val("modify");
				$("input[name=id]").val($(row).find("input[name=modify]").val());
				$("input[name=group_name]").val($(row).find(".group-name").html());
				$("select[name=competence]").val($(row).find("input[name=competence-id]").val());
				$("input[name=dateStart]").val($(row).find(".group-opened").html());
				$("input[name=dateEnd]").val($(row).find(".group-closed").html());
			});

		$("body").on("click", "#cancel-modify", function() {
			cancelModify();
		});

		$("body").on("click", ".deleteable", function() {
			var row = $(this).parent().parent().parent();
			$("input[name=type]").val("delete");
            $("input[name=id]").val($(row).find("input[name=deleteAchievementType]").val());
			var data = $("form").serialize();

			$("input[name=type]").val("create");

			$.ajax({
				url : "<c:url value="/manager/groups/manage/"/>",
				type : "post",
				data : data,
				statusCode : {
					200 : function() {
						if ($(row).parent().find(".row").length > 2) {
							$(row).next().fadeOut();
							$(row).fadeOut({
								complete : function() {
									$(row).next().remove();
									$(row).remove();
								}
							});
						} else {
							$(row).parent().parent().fadeOut({
								complete: function() {
									$(this).remove();
								}
							});
						}
					}
				}
			});
		});

		$("body").on("click", "#form-submit", function() {
				if ($.inArray($("input[name=type]").val(), ["create", "modify"]) < 0) {
					return;
				}

				button = this;
				$(button).attr("disabled", true);
				var ok = function () {};
				var someError = function() {

                };
				var rowRenamer = function(id) {
					return $("#group-row").html()
							.replace(/{{name}}/g, $("input[name=group_name]").val())
							.replace(/{{start}}/g, $("input[name=dateStart]").val())
							.replace(/{{end}}/g, $("input[name=dateEnd]").val())
							.replace(/{{id}}/g, id)
							.replace(/{{comp_id}}/g, $("select[name=competence]").val());
				};
				
				switch ($("input[name=type]").val()) {
				case "create":
					ok = function(id) {
						var competence = $("select[name=competence] option:selected").text();
						if ($("#" + competence).length > 0) {
							$("#" + competence + " .panel-body").append(
								rowRenamer(id)
							);
						} else {
							$("#message-box").after(
								$("#group-panel").html()
									.replace(/{{competence}}/g, $("select[name=competence] option:selected").text())
									.replace(/{{row}}/g, rowRenamer(id))
							);
						}
					};
					break;
					
				case "modify":
					ok = function() {
						var id = $("input[name=id]").val();
						var divs = $("input[name=modify][value=" + id + "]").parent().parent().find("div");
						$(divs[0]).html($("input[name=group_name]").val());
						$(divs[1]).html($("input[name=dateStart]").val());
						$(divs[2]).html($("input[name=dateEnd]").val());
					};
					break;
				}

				$.ajax({
					url : "<c:url value="/manager/groups/manage/"/>",
					type : "post",
					data : $("form").serialize(),
					statusCode : {
						406 : function(resp) {
							$("#message-box").html(resp.responseText);
							$("#message-box").fadeIn("fast");
							console.log(resp);
						},
						200 : function(resp) {
							ok(resp);
						}
					},
					complete: function() {
						$(button).attr("disabled", false);
						cancelModify();
					}
				});
			});
	});


</script>
<div class="container">
    <div id="message-box" class="alert alert-danger text-center"
         style="width: 400px; position: fixed; left: 50%; top: 50%; margin-left: -200px; z-index: 10; display: none"></div>

    <c:forEach var="mapItem" items="${groups}">
        <c:if test="${not empty mapItem.value}">
            <div id="${mapItem.key}" class="panel panel-default">
                <div class="panel-heading">${mapItem.key}</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-6">
                            <strong>Group name</strong>
                        </div>
                        <div class="col-md-2">
                            <strong>Opening date</strong>
                        </div>
                        <div class="col-md-2">
                            <strong>Closing date</strong>
                        </div>
                        <div class="col-md-2">
                            <strong>Actions</strong>
                        </div>
                    </div>
                    <hr/>
                    <c:forEach var="item" items="${mapItem.value}">
                        <div class="row">
                            <div class="col-md-6 group-name">${item.name }</div>
                            <div class="col-md-2 group-opened">${item.dateOpened }</div>
                            <div class="col-md-2 group-closed">${item.dateClosed }</div>
                            <div class="col-md-2 text-center">
                                <div class="btn-group btn-group-lg">
                                    <button type="button" class="modifiable btn btn-warning">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </button>
                                    <button type="button" class="deleteable btn btn-danger">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                                <input type="hidden" name="delete" value="${item.id}"/>
                                <input type="hidden" name="modify" value="${item.id}"/>
                                <input type="hidden" name="competence-id" value="${item.competence.id}"/>
                            </div>
                        </div>
                        <hr/>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </c:forEach>

    <div class="panel panel-default">
        <div class="panel-heading">
            <a href="#grouplink"></a> <span class="create-new-group">Створити
				нову групу</span> <span class="modify-existing-group" style="display: none">Редагувати
				групу</span> <span id="cancel-modify" class="modify-existing-group"
                                   style="display: none; float: right"><span
                class="glyphicon glyphicon-remove-circle"></span></span>
        </div>
        <div class="panel-body">
            <div class="row">
                <form method="POST">
                    <input type="hidden" name="type" value="create"/> <input
                        type="hidden" name="id" value=""/>
                    <div class="col-md-4">
                        <div class="form-group">
                            <input class="form-control" type="text" name="group_name"
                                   placeholder="Назва групи"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <select class="form-control" name="competence">
                                <c:forEach var="item" items="${competences}">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <input id="date-from" class="form-control" name="dateStart"
                                   placeholder="Дата старту"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <input id="date-to" class="form-control" name="dateEnd"
                                   placeholder="Дата закінчення"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <button id="form-submit" class="btn btn-default form-control"
                                type="button">
                            <span class="create-new-group">Створити</span> <span
                                class="modify-existing-group" style="display: none">Редагувати</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>