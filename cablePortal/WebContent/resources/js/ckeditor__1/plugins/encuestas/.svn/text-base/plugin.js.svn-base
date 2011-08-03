
CKEDITOR.plugins.add('encuestas',{
    requires: ['iframedialog'],
    init:function(a){
        CKEDITOR.dialog.addIframe('encuesta_dialog', 'Crear una encuesta',contextPath+'/com/cablevision/controller/encuesta/addEncuesta.do',400,300,function(){/*oniframeload*/})
        var cmd = a.addCommand('encuestas', {exec:encuestas_onclick})
        cmd.modes={wysiwyg:1,source:1}
        cmd.canUndo=true
        a.ui.addButton('encuestas',{ label:'Crear una encuesta...', command:'encuestas', icon:this.path+'images/tick.png' })
    }
})

function encuestas_onclick(e){
    // run when custom button is clicked
    CKEDITOR.instances[editorId].openDialog('encuesta_dialog')
}