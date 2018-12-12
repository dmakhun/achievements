<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="<c:url value="/resources/script/jquery-2.1.0.js" />"></script>
<script src="<c:url value="/resources/script/jquery-ui.js" />"></script>
<script type="text/javascript">
  function loadAchievementType(competenceId) {
    $.ajax({
      url: '<c:url value="/admin/achievementtype/list/"/>'
      + competenceId,
      success: function (text) {
        $(".achievement-type-list").remove();
        $("#competence-" + competenceId).after(
            "<tr class='achievement-type-list'><td colspan='2'>"
            + text + "</td></tr>");
        $(".btn").css("display", "inherit");
        $("#button-" + competenceId).css("display", "none");
      }
    });
  }

  function displayDialog(competenceId) {
    $("#dialog").dialog();
    var button = $("#achievement-type-submitter")[0];
    button.value = competenceId;
  }

  function pass() {
    $.ajax({
      url: '<c:url value="/admin/achievementtype/add/"/>' + $("#achievement-type-submitter").val(),
      type: "POST",
      data: $("form").serialize(),
      success: function (text) {
        $("#dialog").dialog("close");

        switch (text) {
          case "success":
            addLineToTable();
            break;
          case "fail":
            alert("Something wrong)!");
            break;
        }
      }
    });
  }

  function addLineToTable() {
    var line = '<tr><td>' + $("form input[name=name]").val() +
        '</td><td>'
        + $("form input[name=points]").val() + '</td></tr>';
    $($("table table tr")[0]).after(line);
  }


</script>

<div class="panel panel-default">
    <div class="panel-heading">Achievements</div>
    <table class="table">
        <tr>
            <th>Competence</th>
            <th></th>
        </tr>
        <c:forEach var="item" items="${competenceList}">
            <tr id="competence-${item.id}">
                <td>${item.name}</td>
                <td>
                    <button id="button-${item.id}" class="btn btn-default"
                            onclick="loadAchievementType('${item.id}');">
                        Show achievement types
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="dialog" style="display: none" title="Add new achievement type">
    <div class="panel-body">
        <form id="add-achievement-type">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-aGroup">
                        <input class="form-control" id="achName" type="text" name="name"
                               placeholder=
                                <spring:message code="achievement.name"/>/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="form-aGroup">
                        <input class="form-control" id="achPoints" type="text" name="points"
                               placeholder=
                                <spring:message code="achievement.points"/>/>
                    </div>
                </div>
            </div>

            <div class="row text-center">
                <div class="col-md-12">
                    <div class="form-aGroup">
                        <button id="achievement-type-submitter" type="button"
                                class="btn btn-success form-control"
                                onclick="pass();">
                            <spring:message code="add"/>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>