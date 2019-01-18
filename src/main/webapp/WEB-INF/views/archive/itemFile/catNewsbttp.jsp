<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    .jcrop-holder #preview-pane {
        display: block;
        position: absolute;
        z-index: 2000;
        top: -20px;
        right: -380px;
        padding: 6px;
        background-color: white;

        -webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
        -moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
        box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
    }

    #preview-pane .preview-container {
        width: 290px;
        height: 270px;
        overflow: hidden;
    }

</style>
</head>
<script>
    jQuery(document).ready(function () {
        itemFile.initCatBttp();
    })
</script>
<body>
<form id="myform">
    <div id="BodyPanel">
        <div id="content" style="margin: 50px;">
            <img src="itemicon/${imagename}" id="target" style="max-width:300px;max-height:300px;"/>
            <div id="preview-pane">
                <div class="preview-container">
                    <img src="itemicon/${imagename}" class="jcrop-preview" alt="Preview"/>
                </div>
            </div>
        </div>
        <input type="hidden" name="imagename" value="${imagename }" id="imagename"/>
        <input type="hidden" name="width" value="" id="width"/>
        <input type="hidden" name="height" value="" id="height"/>
        <input type="hidden" name="x" value="" id="x"/>
        <input type="hidden" name="y" value="" id="y"/>
        <input type="hidden" name="boundx" value="" id="boundx"/>
        <input type="hidden" name="boundy" value="" id="boundy"/>
    </div>
</form>
</body>
</html>