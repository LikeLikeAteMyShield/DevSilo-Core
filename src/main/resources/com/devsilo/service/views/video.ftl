<#-- @ftlvariable name="" type="com.devsilo.service.views.VideoView" -->
<#import "layout.ftl" as layout>
<@layout.layout>
<div style="height:200px;width:1000px;padding:10px">
    <img src="${video.getThumbNailAsBase64()}" style="width:200px;height:100%;display:inline;"/>
    <p style="display:inline;">${video.title}</p>
</div>
</@layout.layout>