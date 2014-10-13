<#include "molgenis-header.ftl">
<#include "molgenis-footer.ftl">
<@header/>

<#if content?has_content>
	<div class="row">
		<div class="col-md-12">
			${content}
		</div>
	</div>
</#if>

<#if isCurrentUserCanEdit?has_content && isCurrentUserCanEdit>
	<div class="row">
		<div class="col-md-12">
			<hr></hr>
			<a href="${context_url}/edit" class="btn btn-default pull-left">Edit page</a>
		</div>
	</div>
</#if>

<@footer/>
