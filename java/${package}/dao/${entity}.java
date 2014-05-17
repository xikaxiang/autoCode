package ${package};


<#list imports as s >
import ${s};
</#list>


public class ${entity}{
	
	<#list javaFullFields as p>
	  ${p};
	  
	</#list>
	
	<#list columns as s>
	public ${s.javaType} get${s.javaFieldName?cap_first}(){
		return ${s.javaFieldName};
	}
	
	public void set${s.javaFieldName?cap_first}(${s.javaType} ${s.javaFieldName}) {
		this.${s.javaFieldName} = ${s.javaFieldName};
	}
	
	</#list>
	
}