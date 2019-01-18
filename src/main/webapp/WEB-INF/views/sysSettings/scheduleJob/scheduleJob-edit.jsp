<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <body>
 <form id="myfrom" class="editForm">
 	 <input type="hidden" id="hideId" name="jobid" value="${job.jobid}">
     <ul class="edit-contents animated fadeInUp">
     	
     	 <li><span class="title"><span class="required">*</span>任务名称：</span>
             <div class="form-group biger-wid">
                 <input <c:if test="${not empty job}">disabled</c:if> maxlength="18" type="text" class="form-control" id="jobname" name="jobname" value="${job.jobname}">
             </div>
         </li>
         <li><span class="title"><span class="required">*</span>任务组：</span>
             <div class="form-group biger-wid">
                 <input <c:if test="${not empty job}">disabled</c:if> maxlength="18" type="text" class="form-control" id="jobgroup" name="jobgroup" value="${job.jobgroup}">
             </div>
         </li>
         <li><span class="title"><span class="required">*</span>任务方法：</span>
             <div class="form-group biger-wid">
                 <input <c:if test="${not empty job}">disabled</c:if> maxlength="25" type="text" class="form-control" id="methodname" name="methodname" value="${job.methodname}">
             </div>
         </li>
         <li><span class="title"><span class="required">*</span>任务描述：</span>
             <div class="form-group bigest-wid">
                 <input <c:if test="${not empty job}">disabled</c:if> maxlength="50" type="text" class="form-control" id="description" name="description" value="${job.description}">
             </div>
         </li>
         <li><span class="title"><span class="required">*</span>任务方法类：</span>
             <div class="form-group bigest-wid">
                 <input <c:if test="${not empty job}">disabled</c:if> maxlength="50" type="text" class="form-control" id="beanclass" name="beanclass" value="${job.beanclass}">
             </div>
         </li>
         <li><span class="title"><span class="required">*</span>表达式：</span>
             <div class="form-group bigest-wid">
                 <input maxlength="50" type="text" class="form-control" id="cronexpression" name="cronexpression" value="${job.cronexpression}">
             </div>
         </li>
     </ul>
 </form>
 </body>